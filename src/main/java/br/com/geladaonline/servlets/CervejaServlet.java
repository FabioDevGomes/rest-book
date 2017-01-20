package br.com.geladaonline.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Estoque;
import br.com.geladaonline.model.rest.Cervejas;

@SuppressWarnings("serial")
@WebServlet(value = "/cervejas/*")
public class CervejaServlet extends HttpServlet {

	private static JAXBContext context;
	private Estoque estoque = new Estoque();

	static {
		try {
			context = JAXBContext.newInstance(Cervejas.class);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String identificador = null;
		try {
			identificador = obtemIdentificador(req);
		} catch (RecursoSemIdentificadoException e) {
			resp.sendError(400, e.getMessage());
		}
		
		if(identificador != null && estoque.recuperaCervejaPeloNome(identificador) != null){
			resp.sendError(500, "Cerveja já cadastrada");
			return;
		}
		
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Cerveja cerveja = (Cerveja) unmarshaller.unmarshal(req.getInputStream());
			cerveja.setNome(identificador);
			estoque.adicionarCerveja(cerveja);
		} catch (JAXBException e) {
			resp.sendError(500, e.getMessage());
		}
		
		String requestURI = req.getRequestURI();
		resp.setHeader("Location", requestURI);
		resp.setStatus(201);
		
		escreveXML(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acceptHeader = req.getHeader("Accept");

		if (acceptHeader == null || acceptHeader.contains("application/xml")) {
			escreveXML(req, resp);
		} else if (acceptHeader.contains("application/json")) {
			escreveJSON(req, resp);
		} else {
			// Formato não suportado
			resp.sendError(415);
		}
	}

	private void escreveXML(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object objetoAEscrever = localizaObjetoASerEnviado(req);

		if (objetoAEscrever == null) {
			// Objeto não encontrado
			resp.sendError(404);
			return;
		}

		try {
			resp.setContentType("application/xml;charset=UTF-8");
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(objetoAEscrever, resp.getWriter());

		} catch (JAXBException e) {
			resp.sendError(500, e.getMessage());
		}
	}

	private void escreveJSON(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object objetoAEscrever = localizaObjetoASerEnviado(req);

		if (objetoAEscrever == null) {
			// Objeto não encontrado
			resp.sendError(404);
			return;
		}

		try {
			resp.setContentType("application/json;charset=UTF-8");
			MappedNamespaceConvention con = new MappedNamespaceConvention();

			XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, resp.getWriter());

			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(objetoAEscrever, xmlStreamWriter);

		} catch (JAXBException e) {
			resp.sendError(500);
		}
	}

	private String obtemIdentificador(HttpServletRequest req) throws RecursoSemIdentificadoException {
		String requestURI = req.getRequestURI();
		String[] pedacosDaURI = requestURI.split("/");

		boolean contextoEncontrado = false;
		for (String contexto : pedacosDaURI) {
			if (contexto.equals("cervejas")) {
				contextoEncontrado = true;
				continue;
			}

			if (contextoEncontrado) {
				try {
					return URLDecoder.decode(contexto, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					return URLDecoder.decode(contexto);
				}
			}
		}

		throw new RecursoSemIdentificadoException("Recurso sem identificador, óbvio!!");

	}

	private Object localizaObjetoASerEnviado(HttpServletRequest req) {
		Object objeto = null;
		Cervejas cervejas = new Cervejas();

		try {
			String identificado = obtemIdentificador(req);
			objeto = estoque.recuperaCervejaPeloNome(identificado);
//			if (objeto != null) {
//				List<Cerveja> cervejaEscolhida = new ArrayList<>();
//				cervejaEscolhida.add((Cerveja) objeto);
//				cervejas.setCervejas(cervejaEscolhida);
//				objeto = cervejas;
//			}
		} catch (RecursoSemIdentificadoException e) {
			cervejas.setCervejas(new ArrayList<>(estoque.listarCervejas()));
			objeto = cervejas;
		}

		return objeto;
	}
}
