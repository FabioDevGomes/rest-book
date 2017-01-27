package br.com.geladaonline.services;

public class CervejaJaExisteException extends Exception {

	public CervejaJaExisteException() {
		super();
	}

	public CervejaJaExisteException(String message) {
		super(message);
	}

	public CervejaJaExisteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CervejaJaExisteException(Throwable cause) {
		super(cause);
	}

}
