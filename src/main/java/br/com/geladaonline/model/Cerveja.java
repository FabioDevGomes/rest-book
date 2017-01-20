package br.com.geladaonline.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cerveja {
	
	public Cerveja(){}

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCevejaria() {
		return cevejaria;
	}

	public void setCevejaria(String cevejaria) {
		this.cevejaria = cevejaria;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
