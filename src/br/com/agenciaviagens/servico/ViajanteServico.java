package br.com.agenciaviagens.servico;

import java.util.Collection;

import br.com.agenciaviagens.entidades.Viajante;
import br.com.agenciaviagens.enums.ListaErros;
import br.com.agenciaviagens.exceptions.PersistenciaException;
import br.com.agenciaviagens.exceptions.ServicoException;
import br.com.agenciaviagens.persistencia.IPersistenciaViajante;
import br.com.agenciaviagens.persistencia.impl.PersistenciaViajanteArquivo;

public class ViajanteServico {

	private IPersistenciaViajante persistencia;

	public ViajanteServico() {
		persistencia = new PersistenciaViajanteArquivo(
				"C:\\Users\\User\\Downloads\\agenciaviagens (3)\\agenciaviagens\\arquivos\\viajantes.csv");
	}

	public Viajante criarViajante(Viajante viajante) throws ServicoException {
		try {
			viajante = persistencia.criar(viajante);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new ServicoException("Não foi possível realizar o cadastro do viajante.", e,
					ListaErros.ERRO_CADASTRO_VIAJANTE.getCodigo());
		}
		return viajante;
	}

	public Collection<Viajante> buscarTodosViajantes() throws ServicoException {
		try {
			return persistencia.buscarTodos();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new ServicoException("Não foi possível buscar todos os viajantes.", e,
					ListaErros.ERRO_LISTA_VIAJANTES.getCodigo());
		}
	}

	public Collection<Viajante> pesquisarViajantes(Viajante filtro)throws ServicoException{

		try {
			return persistencia.pesquisar(filtro);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new ServicoException("Não foi possível pesquisar todos os viajantes.", e,
					ListaErros.ERRO_PESQUISA_VIAJANTES.getCodigo());
		}

	}
	
	public void apagarViajante(Long id)throws ServicoException{

		try {
			persistencia.apagar(id);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new ServicoException("Não foi possível apagar o viajante.", e,
					ListaErros.ERRO_APAGAR_VIAJANTE.getCodigo());
		}

	

}
	}
