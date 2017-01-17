package br.com.geladaonline.model;

public class Cerveja {

	public Cerveja(String nome, String descricao, String cevejaria, Tipo tipo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.cevejaria = cevejaria;
		this.tipo = tipo;
	}

	private String nome;
	private String descricao;
	private String cevejaria;
	private Tipo tipo;
	
	public enum Tipo{
		LAGER, PILSEN, PALE_ALE, INDIAN_PALE_ALE, WEIZEN;
	}

	@Override
	public String toString() {
		return "Cerveja [nome=" + nome + ", descricao=" + descricao + ", cevejaria=" + cevejaria + ", tipo=" + tipo
				+ "]";
	}
}
