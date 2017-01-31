package br.com.geladaonline.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.geladaonline.model.Cerveja;

@XmlRootElement
public class Cervejas {

	private List<Cerveja> cervejas = new ArrayList<>();
	
	public Cervejas(List<Cerveja> cervejas) {
		super();
		this.cervejas = cervejas;
	}

	public Cervejas() {
		super();
	}

	@XmlElement(name="cerveja")
	public List<Cerveja> getCervejas() {
		return cervejas;
	}
	
	@XmlElement(name="link")
	public List<Link> getLinks(){
		List<Link> links = new ArrayList<>();
		for (Cerveja cerveja : getCervejas()) {
			Link link = Link.fromPath("cervejas/{nome}").rel("cerveja").title(cerveja.getNome())
					.build(cerveja.getNome());
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}

//	@XmlTransient
//	public List<Cerveja> getCervejas() {
//		return cervejas;
//	}

	public void setCervejas(List<Cerveja> cervejas) {
		this.cervejas = cervejas;
	}
	
}
