package br.com.geladaonline.services;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.jackson.JacksonFeature;

import br.com.geladaonline.model.twitter.Status;
import br.com.geladaonline.model.twitter.Statuses;

@Path("twitter/messages")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class TwitterMessages {

	@GET
	public Statuses list(@Context HttpServletRequest request){
		AccessToken tokenn = (AccessToken) request.getAttribute(TwitterLoginFilter.ACCESS_TOKEN_KEY);
		Client client = TwitterOAuthFlowService.getClient(tokenn);
		Response response = client
				.target("https://api.twitter.com/1.1/statuses/home_timeline.json").request().get();
		
		Status[] statusArray = response.readEntity(Status[].class);
		Statuses statuses = new Statuses();
		
		statuses.setStatusCollection(Arrays.asList(statusArray));
		
		return statuses;
	}
	
}
