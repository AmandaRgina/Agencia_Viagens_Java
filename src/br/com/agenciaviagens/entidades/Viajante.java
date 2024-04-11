package br.com.agenciaviagens.entidades;

import java.util.ArrayList;

import br.com.agenciaviagens.enums.TipoDocumento;

public class Viajante {

    private Long id;
    private String nome;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String email;
    private ArrayList<Viagem> viagensViajante;
    private ArrayList<Gasto> gastosViajante;

    public Viajante(Long id, String nome, TipoDocumento tipoDocumento, String numeroDocumento, String email) {
	this.id = id;
	this.nome = nome;
	this.tipoDocumento = tipoDocumento;
	this.numeroDocumento = numeroDocumento;
	this.email = email;
	this.viagensViajante = new ArrayList<Viagem>();
	this.gastosViajante = new ArrayList<Gasto>();
    }

    public Viajante(Viajante viajante) {
	this.id = viajante.getId();
	this.nome = viajante.getNome();
	this.tipoDocumento = viajante.getTipoDocumento();
	this.numeroDocumento = viajante.getNumeroDocumento();
	this.email = viajante.getEmail();
	this.viagensViajante = new ArrayList<Viagem>();
	this.gastosViajante = new ArrayList<Gasto>();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public TipoDocumento getTipoDocumento() {
	return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
	return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
	this.numeroDocumento = numeroDocumento;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public ArrayList<Viagem> getViagensViajante() {
	return viagensViajante;
    }

    public void setViagensViajante(ArrayList<Viagem> viagensViajante) {
	this.viagensViajante = viagensViajante;
    }

    public ArrayList<Gasto> getGastosViajante() {
	return gastosViajante;
    }

    public void setGastosViajante(ArrayList<Gasto> gastosViajante) {
	this.gastosViajante = gastosViajante;
    }

    @Override
    public String toString() {
	return "Viajante [id=" + id + ", nome=" + nome + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento="
		+ numeroDocumento + ", email=" + email + "]";
    }

	public boolean atende(Viajante filtro) {
		
		if(filtro == null) {
			
			return false;
		}
		if(filtro.nome != null && !filtro.nome.isBlank()) {
			if(this.nome == null) {
				return false;
			}
			if(!filtro.nome.equalsIgnoreCase(this.nome)) {
				return false;
			}
		} 
		
		if(filtro.tipoDocumento != null) {
			if(this.tipoDocumento == null) {
				return false;
			}
			if(!filtro.tipoDocumento.equals(this.tipoDocumento)) {
				return false;
			}
		}
		
		if(filtro.numeroDocumento != null && !filtro.numeroDocumento.isBlank()) {
			if(this.numeroDocumento == null) {
				return false;
			}
			if(!filtro.numeroDocumento.equals(this.numeroDocumento)) {
				return false;
			}
		}
		
		if(filtro.email != null && !filtro.email.isBlank()) {
			if(this.email == null) {
				return false;
			}
			if(!filtro.email.equals(this.email)) {
				return false;
			}
		} 
		
		
		
		return true;
	}

}
