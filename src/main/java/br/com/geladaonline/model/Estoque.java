package br.com.geladaonline.model;

import java.util.ArrayList;
import java.util.Collection;

import br.com.geladaonline.model.Cerveja.Tipo;

public class Estoque {
	
	public Estoque(){
		Cerveja cerveja = new Cerveja("Stella Artois", "A cerveja belga mais franca do mundo", "Artois", Tipo.LAGER);
		Cerveja cerveja2 = new Cerveja("Erdinger Wissbier", "Cerveja de trigo alemá", "Erdinger", Tipo.WEIZEN);
		cervejas.add(cerveja);
		cervejas.add(cerveja2);
	}

	private Collection<Cerveja> cervejas = new ArrayList<Cerveja>();
	
	public Collection<Cerveja> listarCervejas(){
		return new ArrayList<Cerveja>(this.cervejas);
	}
	
	public void adicionarCerveja(Cerveja cerveja){
		cervejas.add(cerveja);
	}
}
