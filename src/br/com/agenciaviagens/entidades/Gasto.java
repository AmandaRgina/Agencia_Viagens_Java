package br.com.agenciaviagens.entidades;

import br.com.agenciaviagens.enums.TipoGasto;

public class Gasto {

	
	private TipoGasto tipoGasto;
	private Double valor;
	private Viagem viagem;
	private Viajante viajante;
	
	public Gasto(TipoGasto tipoGasto, Double valor, Viagem viagem, Viajante viajante) {
		
		this.tipoGasto = tipoGasto;
		this.valor = valor;
		this.viagem = viagem;
		this.viajante = viajante;
	}

	public TipoGasto getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(TipoGasto tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Viajante getViajante() {
		return viajante;
	}

	public void setViajante(Viajante viajante) {
		this.viajante = viajante;
	}

	@Override
	public String toString() {
		return "Gasto [tipoGasto=" + tipoGasto + ", valor=" + valor + ", viagem=" + viagem + ", viajante=" + viajante
				+ "]";
	}
	
	
	
	
}
