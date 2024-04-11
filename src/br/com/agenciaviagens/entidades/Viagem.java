package br.com.agenciaviagens.entidades;

import java.util.Date;

import br.com.agenciaviagens.utils.DataUtils;

public class Viagem {

	private Date dataInicio;
	private Date dataTermino;
	protected int codViagem;
	private String destino;
	protected static int COD_VIAGEM = 1;
	
	public Viagem(String destino, Date dataInicio, Date dataTermino) {
		
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.codViagem = COD_VIAGEM;
		this.destino = destino;
		
		COD_VIAGEM++;
		
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public int getCodViagem() {
		return codViagem;
	}

	public void setCodViagem(int codViagem) {
		this.codViagem = codViagem;
	}
	

	@Override
	public String toString() {
		return "Viagem [dataInicio=" + DataUtils.converteData(dataInicio)+ ", dataTermino=" + DataUtils.converteData(dataTermino) + ", codViagem=" + codViagem
				+ ", destino=" + destino + "]";
	}

	
	
	
}
