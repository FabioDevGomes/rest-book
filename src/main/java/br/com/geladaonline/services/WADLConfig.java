package br.com.geladaonline.services;

import java.util.List;

import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorApplicationDoc;

public class WADLConfig extends WadlGeneratorConfig{

	@Override
	public List<WadlGeneratorDescription> configure() {
		return generator(WadlGeneratorApplicationDoc.class).prop("applicationDocsStream", "doc.xml")
				.descriptions();
	}

}
