package br.com.geladaonline.services;

import java.net.URI;
import java.util.List;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Estoque;
import br.com.geladaonline.model.rest.Cervejas;

@Path("/cervejas")
@Consumes({MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_XML})
public class CervejaService {
	
	private static Estoque estoque = new Estoque();
	private static final int TAMANHO_PAGINA = 2;
	
	@GET
	public Cervejas listarTodasAsCervejas(@QueryParam("pagina") int pagina){
		List<Cerveja> cervejas =  estoque.listarCervejas(pagina, TAMANHO_PAGINA);

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

}
