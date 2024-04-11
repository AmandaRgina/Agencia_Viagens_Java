package br.com.agenciaviagens.exceptions;

@SuppressWarnings("serial")
public class ViagemNEncontradaException extends Exception {

	public ViagemNEncontradaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViagemNEncontradaException(String message) {
		super(message);
	}

}
