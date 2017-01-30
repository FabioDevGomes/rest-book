package br.com.geladaonline.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.geladaonline.model.Cerveja.Tipo;
import br.com.geladaonline.services.CervejaJaExisteException;
import br.com.geladaonline.services.CervejaNaoEcontradaException;

public class Estoque {
	
	public Estoque(){
		Cerveja cerveja = new Cerveja("Stella Artois", "A cerveja belga mais franca do mundo", "Artois", Tipo.LAGER);
		Cerveja cerveja2 = new Cerveja("Erdinger Wissbier", "Cerveja de trigo alemá", "Erdinger", Tipo.WEIZEN);
		cervejas.put(cerveja.getNome(), cerveja);
		cervejas.put(cerveja2.getNome(), cerveja2);
	}

	private Map<String, Cerveja> cervejas = new HashMap<>();
	
	public Collection<Cerveja> listarCervejas(){
		return new ArrayList<>(this.cervejas.values());
	}
	
	public void adicionarCerveja(Cerveja cerveja) throws CervejaJaExisteException{
		if(cervejas.containsKey(cerveja.getNome())){
			throw new CervejaJaExisteException();
		}
		cervejas.put(cerveja.getNome(), cerveja);
	}

	public Cerveja recuperaCervejaPeloNome(String nome){
		return cervejas.get(nome);
	}
	
	public void atualizarCerveja(Cerveja cerveja) throws CervejaNaoEcontradaException{
		if(!cervejas.containsKey(cerveja.getNome())){
			throw new CervejaNaoEcontradaException();
		}
		cervejas.remove(cerveja.getNome());
		cervejas.put(cerveja.getNome(), cerveja);
	}
	
	public void apagarCerveja(String nome) throws CervejaNaoEcontradaException{
		if(!cervejas.containsKey(nome)){
			throw new CervejaNaoEcontradaException();
		}
		cervejas.remove(nome);
	}
	
}
