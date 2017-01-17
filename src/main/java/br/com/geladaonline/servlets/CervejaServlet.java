package br.com.geladaonline.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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
		
		try {
			Marshaller marshaller = context.createMarshaller();
			resp.setContentType("application/xml;charset=UTF-8");
			PrintWriter printWriter = resp.getWriter();
			
			Cervejas cervejas = new Cervejas();
			cervejas.setCervejas(new ArrayList<>(estoque.listarCervejas()));
			marshaller.marshal(cervejas, printWriter);
		} catch (Exception e) {
			resp.sendError(500, e.getMessage());
		}
	}
}
