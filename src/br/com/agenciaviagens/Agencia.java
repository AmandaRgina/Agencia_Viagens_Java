package br.com.agenciaviagens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.com.agenciaviagens.entidades.Gasto;
import br.com.agenciaviagens.entidades.Viagem;
import br.com.agenciaviagens.entidades.Viajante;
import br.com.agenciaviagens.enums.TipoDocumento;
import br.com.agenciaviagens.exceptions.DocumentoNEncontradoException;
import br.com.agenciaviagens.exceptions.ServicoException;
import br.com.agenciaviagens.exceptions.ViagemNEncontradaException;
import br.com.agenciaviagens.servico.ViajanteServico;


public class Agencia {

    private ViajanteServico viajanteServico;

    private ArrayList<Viagem> viagens;
    private ArrayList<Viajante> viajantes;

    public Agencia() {
	this.viajanteServico = new ViajanteServico();
	this.viagens = new ArrayList<Viagem>();
	this.viajantes = new ArrayList<Viajante>();
    }

    public boolean addViagem(Viagem viagem) {

	return viagens.add(viagem);
    }

    public ArrayList<Viagem> getViagens() {
	return viagens;
    }

    public ArrayList<Viajante> getViajantes() {
	return viajantes;
    }

    public void cadastrarViagem(String destino, Date dataInicio, Date dataTermino) {

	Boolean valida = validaViagem(destino, dataInicio, dataTermino);
	if (valida) {
	    Viagem viagem = new Viagem(destino, dataInicio, dataTermino);
	    addViagem(viagem);
	}
    }

    public Boolean validaViagem(String destino, Date dataInicio, Date dataTermino) {

	if (destino == null || destino.isBlank() || dataInicio == null || dataTermino == null) {

	    return false;
	}

	if (dataInicio.after(dataTermino)) {

	    return false;
	}

	return true;
    }

    public Viajante cadastrarViajante(String nome, TipoDocumento documento, String numeroDocumento, String email)
	    throws ServicoException {

	Viajante viajante = new Viajante(null, nome, documento, numeroDocumento, email);
	return viajanteServico.criarViajante(viajante);
    }

    public void cadastrarViagemViajante(String numeroDocumento, int codViagem)
	    throws DocumentoNEncontradoException, ViagemNEncontradaException {

	Boolean achouViajante = false;
	Boolean achouViagem = false;

	for (Viajante viajant : viajantes) {

	    if (viajant.getNumeroDocumento().equals(numeroDocumento)) {
		achouViajante = true;
		for (Viagem viag : viagens) {

		    if (viag.getCodViagem() == codViagem) {
			achouViagem = true;
			viajant.getViagensViajante().add(viag);
		    }

		}
	    }
	}

	if (!achouViajante) {

	    throw new DocumentoNEncontradoException("Documento não encontrado");
	}

	if (!achouViagem) {

	    throw new ViagemNEncontradaException("Destino não encontrado!");
	}
    }

    public void cadastrarGastoViajante(String numeroDocumento, Gasto gasto) throws DocumentoNEncontradoException {

	for (Viajante viajant : viajantes) {

	    if (viajant.getNumeroDocumento().equals(numeroDocumento)) {

		viajant.getGastosViajante().add(gasto);
		return;
	    }
	}

	throw new DocumentoNEncontradoException("Documento não encontrado!");
    }

    public void listarViagens() {

	for (int i = 0; i < viagens.size(); i++) {

	    System.out.println(viagens.get(i));
	}
    }

   
    
    public Collection<Viajante> pesquisarViajantes(Viajante filtro) throws ServicoException {
    	
    	Collection<Viajante> viajantesAgencia = viajanteServico.pesquisarViajantes(filtro);
    	
    	return viajantesAgencia;
    }

    public void listarGastosViajante(String numeroDocumento) throws DocumentoNEncontradoException {

	Boolean achou = false;
	int indice = 0;

	while (!achou && indice < viajantes.size()) {

	    Viajante viajante = viajantes.get(indice);

	    if (viajante.getNumeroDocumento().equals(numeroDocumento)) {

		System.out.println(viajante.getGastosViajante());
		achou = true;
	    }
	}

	if (!achou) {

	    throw new DocumentoNEncontradoException("Documento não encontrado");
	}
    }

    public Viajante getViajante(String numeroDocumento) throws DocumentoNEncontradoException {

	for (Viajante viajante : viajantes) {

	    if (viajante.getNumeroDocumento().equals(numeroDocumento)) {

		return viajante;
	    } else {

		throw new DocumentoNEncontradoException("Documento não encontrado");
	    }
	}

	return null;
    }

    public Viagem getViagem(int codViagem) {

	for (Viagem viagem : viagens) {

	    if (viagem.getCodViagem() == codViagem) {
		return viagem;
	    }
	}

	return null;
    }
    
    public void apagar(Long id) throws ServicoException {
    	
    	viajanteServico.apagarViajante(id);
    }
}

