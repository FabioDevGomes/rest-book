package br.com.geladaonline.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Estoque;
import br.com.geladaonline.model.rest.Cervejas;

@SuppressWarnings("serial")
@WebServlet(value = "/cervejas/*")
public class CervejaServlet extends HttpServlet{

	private static JAXBContext context;
	private Estoque estoque = new Estoque();
	
	static{
		try {
			context = JAXBContext.newInstance(Cervejas.class); 
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acceptHeader = req.getHeader("Accept");
		
		if (acceptHeader == null || acceptHeader.contains("application/xml")){
			escreveXML(req, resp);
		} else if (acceptHeader.contains("application/json")){
			escreveJSON(req, resp);
		} else {
			//Formato não suportado
			resp.sendError(415);
		}
	}
	
	private void escreveXML(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cervejas cervejas = new Cervejas();
		cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));
		
		try {
			resp.setContentType("application/xml;charset=UTF-8");
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cervejas, resp.getWriter());
			
		} catch (JAXBException e) {
			resp.sendError(500, e.getMessage());
		}
	}

	private void escreveJSON(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cervejas cervejas = new Cervejas();
		cervejas.setCervejas(new ArrayList<Cerveja>(estoque.listarCervejas()));
		
		try {
			resp.setContentType("application/json;charset=UTF-8");
			MappedNamespaceConvention con = new MappedNamespaceConvention();

			XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, resp.getWriter());

			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cervejas, xmlStreamWriter);
			
		} catch (JAXBException e) {
			resp.sendError(500);
		}

	}
}
