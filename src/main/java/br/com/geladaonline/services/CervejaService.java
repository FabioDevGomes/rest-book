package br.com.geladaonline.services;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	
	@GET
	public Cervejas listarTodasAsCervejas(){
		List<Cerveja> cervejas = (List<Cerveja>) estoque.listarCervejas();
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

}
