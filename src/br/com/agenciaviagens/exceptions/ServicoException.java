package br.com.agenciaviagens.exceptions;

public class ServicoException extends Exception {
    
    private Long codigoErro;

    private static final long serialVersionUID = -9050772623294882273L;

    public ServicoException(String mensagem, Throwable causa, Long codigoErro) {
	super(mensagem, causa);
	this.codigoErro = codigoErro;
    }

    public ServicoException(String mensagem, Long codigoErro) {
	super(mensagem);
	this.codigoErro = codigoErro;
    }

    public Long getCodigoErro() {
	return codigoErro;
    }

}
