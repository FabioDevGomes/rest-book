package br.com.geladaonline.services;

public class CervejaNaoEcontradaException extends Exception {

	public CervejaNaoEcontradaException() {
		super();
	}

	public CervejaNaoEcontradaException(String message) {
		super(message);
	}

	public CervejaNaoEcontradaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CervejaNaoEcontradaException(Throwable cause) {
		super(cause);
	}

}
