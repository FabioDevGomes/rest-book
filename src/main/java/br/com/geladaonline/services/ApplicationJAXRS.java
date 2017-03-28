package br.com.geladaonline.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.jettison.JettisonFeature;

import br.com.geladaonline.cache.CacheInterceptor;

public class ApplicationJAXRS extends Application{

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> propriedades = new HashMap<>();
		propriedades.put("jersey.config.server.provider.packages", "br.com.geladaonline.services");
		propriedades.put("jersey.config.server.wadl.generatorConfig", WADLConfig.class.getCanonicalName());
		return propriedades;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		singletons.add(new JettisonFeature());
		singletons.add(new CacheInterceptor());
		
		return singletons;
	}
	
	

}
