package br.com.agenciaviagens.enums;

public enum ListaErros {

	ERRO_CADASTRO_VIAJANTE(1l), ERRO_LISTA_VIAJANTES(2l), ERRO_PESQUISA_VIAJANTES(3l), ERRO_APAGAR_VIAJANTE(4l);

	private Long codigo;

	private ListaErros(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

}
