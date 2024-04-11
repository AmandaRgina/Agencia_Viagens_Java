package br.com.agenciaviagens.persistencia;

import java.util.Collection;

import br.com.agenciaviagens.entidades.Viajante;
import br.com.agenciaviagens.exceptions.PersistenciaException;

public interface IPersistenciaViajante {
	
    public Viajante criar(Viajante objeto) throws PersistenciaException;
	
    public Viajante buscarPorId(Long id) throws PersistenciaException;
	
    public Collection<Viajante> buscarTodos() throws PersistenciaException;
	
    public Collection<Viajante> pesquisar(Viajante filtro) throws PersistenciaException;
	
    public void atualizar(Long id, Viajante objeto) throws PersistenciaException;
	
    public void apagar(Long id) throws PersistenciaException;
	
}
