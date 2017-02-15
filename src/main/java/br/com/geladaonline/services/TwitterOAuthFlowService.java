package br.com.geladaonline.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;

public class TwitterOAuthFlowService {
	private static final String OAUTH_TOKEN_FIELD = "oauth_token";
	private static final String CONSUMER_KEY = "xiDLtw6Cn12G3XiGRAus2RBgf";
	private static final String CONSUMER_SECRET = "Skwzal0PfsJbNnFZ6KpwHMmdam2f7CWQa8JCSmbtYlXc9kA2gz";
	
	public static String init(HttpServletRequest request){
		ConsumerCredentials credentials = new ConsumerCredentials(CONSUMER_KEY, CONSUMER_SECRET);
		StringBuffer callback = request.getRequestURL();
		callback.append(";jsessionid=").append(request.getSession().getId());
		
		if(request.getQueryString() != null){
			callback.append("?").append(request.getQueryString());
		}
		
		String callbackHost = callback.toString().replace("localhost", "127.0.0.1");
		
		OAuth1AuthorizationFlow flow = OAuth1ClientSupport.builder(credentials)
				.authorizationFlow("https://api.twitter.com/oauth/request_token", 
						"https://api.twitter.com/oauth/access_token",
						"https://api.twitter.com/oauth/authorize").callbackUri(callbackHost).build();
		
		String authorizationURI = flow.start();
		
		String token = authorizationURI.substring(authorizationURI.indexOf(OAUTH_TOKEN_FIELD) + OAUTH_TOKEN_FIELD.length() + 1);
		
		request.getSession().setAttribute(token, flow);
		
		return authorizationURI;
	}
	
	public static AccessToken verify(String token, String verifier, HttpServletRequest req){
		OAuth1AuthorizationFlow flow = (OAuth1AuthorizationFlow) req.getSession().getAttribute(token);
		AccessToken accessToken = flow.finish(verifier);
		req.getSession().removeAttribute(token);
		
		return accessToken;
	}

	public static Client getClient(AccessToken accessToken){
		ConsumerCredentials credentials = new ConsumerCredentials(CONSUMER_KEY, CONSUMER_SECRET);
		Feature feature = OAuth1ClientSupport.builder(credentials).feature().accessToken(accessToken).build();
		Client client = ClientBuilder.newClient().register(feature).register(JacksonFeature.class);
		
		return client;
	}
	

}
