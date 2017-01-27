package br.com.geladaonline.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

public class ApplicationJAXRS extends Application{

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> propriedades = new HashMap<>();
		propriedades.put("jersey.config.server.provider.packages", "br.com.geladaonline.services");
		return propriedades;
	}

}
