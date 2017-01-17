package br.com.geladaonline.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Estoque;

@SuppressWarnings("serial")
@WebServlet(value = "/cervejas/*")
public class CervejaServlet extends HttpServlet{

	private Estoque estoque = new Estoque();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter printWriter = resp.getWriter();
		Collection<Cerveja> cervejas = estoque.listarCervejas();
		for (Cerveja cerveja : cervejas) {
			printWriter.print(cerveja);
		}
	
	}
}
