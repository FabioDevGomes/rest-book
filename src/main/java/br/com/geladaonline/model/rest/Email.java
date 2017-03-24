package br.com.geladaonline.model.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Email {

	private String mensagem = "";
	private String formatoMensagem = FORMATO_PADRAO;
	private String assunto = "";
	private List<String> destinatarios = new ArrayList<String>();
	private List<String> comCopia = new ArrayList<String>();
	private List<String> comCopiaOculta = new ArrayList<String>();

	private static final String SEPARADOR_ENDERECOAS = ",";
	private static final String FORMATO_PADRAO = "text/palin";

	public Email withMensagem(String mensagem, String formatoMensagem) {
		if (mensagem != null && formatoMensagem != null) {
			this.mensagem = mensagem;
			this.formatoMensagem = formatoMensagem;
		}
		return this;
	}

	public Email withAssunto(String assunto) {
		if (assunto != null) {
			this.assunto = assunto;
		}
		return this;
	}

	public Email withDestinatario(String destinatario) {
		if (destinatario != null) {
			withDestinatarios(destinatario.split(","));
		}
		return this;
	}

	public Email withDestinatarios(String... destinatario) {
		if (destinatario != null) {
			this.destinatarios.addAll(Arrays.asList(destinatario));
		}
		return this;
	}

	public Email withComCopia(String comCopia) {
		if (comCopia != null) {
			withComCopias(comCopia.split(","));
		}
		return this;
	}

	public Email withComCopias(String... comCopias) {
		if (comCopias != null) {
			this.comCopia.addAll(Arrays.asList(comCopias));
		}
		return this;
	}

	public Email withComCopiaOculta(String comCopiaOculta) {
		if (comCopiaOculta != null) {
			withComCopias(comCopiaOculta.split(","));
		}
		return this;
	}

	public Email withComCopiasOcultas(String... comCopiasOcultas) {
		if (comCopiasOcultas != null) {
			this.comCopiaOculta.addAll(Arrays.asList(comCopiasOcultas));
		}
		return this;
	}

	@Override
	public String toString() {
		return "Email: [mensagem= " + mensagem + ", formatoMensagem= " + formatoMensagem + ", assunto= " + assunto
				+ ", comCopia= " + comCopia + ", comCopiaOculta= " + comCopiaOculta + ", destinatarios= "
				+ destinatarios + " ]";
	}
	
	public void enviar(){
		System.out.println("Enviando email... "+ toString());
	}

}
