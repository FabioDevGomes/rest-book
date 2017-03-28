package br.com.geladaonline.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.geladaonline.cache.Cached;
import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Estoque;
import br.com.geladaonline.model.rest.Cervejas;

@Path("/cervejas")
@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Cached
public class CervejaService {
	
	private static Estoque estoque = new Estoque();
	private static final int TAMANHO_PAGINA = 10;
	private static Map<String, String> EXTENSOES;
	@Context
	private UriInfo uriInfo;
	
	static{
		EXTENSOES = new HashMap<>();
		EXTENSOES.put("image/jpg", ".jpg");
	}
	
	@GET
	public Cervejas listarTodasAsCervejas(@QueryParam("pagina") int pagina){
		
		MultivaluedMap<String, String> queryMap = uriInfo.getQueryParameters();
		
		List<Cerveja> cervejas =  estoque.listarCervejasPorCampo(pagina, TAMANHO_PAGINA, queryMap);
		
//		List<Cerveja> cervejas =  estoque.listarCervejas(pagina, TAMANHO_PAGINA);

		return new Cervejas(cervejas);
	}
	
	@GET
	@Path("{nome}")
	public Cerveja encontrarCerveja(@PathParam("nome") String nomeCerveja){
		Cerveja cerveja = estoque.recuperaCervejaPeloNome(nomeCerveja);
		if(cerveja != null){
			return cerveja;
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}
	
	@POST
	public Response criarCerveja(Cerveja cerveja){
		try {
			estoque.adicionarCerveja(cerveja);
		} catch (CervejaJaExisteException e) {
			throw new WebApplicationException(Status.CONFLICT);
		}
		URI uri = UriBuilder.fromPath("cervejas/{nome}").build(cerveja.getNome());
		
		return Response.created(uri).entity(cerveja).build();
	}
	
	@PUT
	@Path("{nome}")
	public void atualizarCerveja(@PathParam("nome")String nome, Cerveja cerveja){
		try {
			cerveja.setNome(nome); //nome da cerveja a ser alterada
			estoque.atualizarCerveja(cerveja); //objeto recebido com os dados alterados
		} catch (CervejaNaoEcontradaException e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	@DELETE
	@Path("{nome}")
	public void apagarCerveja(@PathParam("nome")String nome){
		try {
			estoque.apagarCerveja(nome);
		} catch (CervejaNaoEcontradaException e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.NOT_FOUND);
		} 
	}
	
	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperarImagem(@PathParam("nome") String nomeCerveja) throws IOException{
		InputStream is = CervejaService.class.getResourceAsStream("/"+ nomeCerveja +".jpg");
		
		if(is == null){
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		
		byte[] dados = new byte[is.available()];
		is.read(dados);
		is.close();
		
		return Response.ok(dados).type("image/jpg").build();
	}
	
	@POST
	@Path("{nome}")
	@Consumes("image/*")
	public Response criarImagem(@PathParam("nome") String nomeImagem, @Context HttpServletRequest req, byte[] dados) 
			throws IOException{
		String userHome = System.getProperty("user.home");
		String mimeType = req.getContentType();
		
		FileOutputStream fos = new FileOutputStream(userHome + File.separator + nomeImagem + EXTENSOES.get(mimeType));
		fos.write(dados);
		fos.flush();
		fos.close();
		
		return Response.ok().build();
	}

}
