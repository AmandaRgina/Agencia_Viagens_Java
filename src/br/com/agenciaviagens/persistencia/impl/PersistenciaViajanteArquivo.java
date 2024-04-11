package br.com.agenciaviagens.persistencia.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.agenciaviagens.entidades.Viajante;
import br.com.agenciaviagens.enums.TipoDocumento;
import br.com.agenciaviagens.exceptions.PersistenciaException;
import br.com.agenciaviagens.persistencia.IPersistenciaViajante;

public class PersistenciaViajanteArquivo implements IPersistenciaViajante {

	private static final String SEPARADOR = ";";

	private String caminhoArquivoCsv;

	private List<String> cabecalhos;

	private Map<Long, Viajante> viajantes;

	public PersistenciaViajanteArquivo(String caminhoArquivoCsv) {
		this.caminhoArquivoCsv = caminhoArquivoCsv;
		this.cabecalhos = List.of("id", "nome", "tipoDocumento", "numeroDocumento", "email");
		this.viajantes =  new HashMap<Long, Viajante>();

		inicializaArquivo();
	}



	/**
	 * Metodo responsavel pela inicializacao do arquivo de persistencia.
	 */
	private void inicializaArquivo() {
		// Se o arquivo ainda nao existir, vamos cria-lo.
		Path path = Paths.get(caminhoArquivoCsv);
		if (Files.exists(path)) {
			System.out.println(String.format("Informação: O arquivo '%s' já existe.", caminhoArquivoCsv));
		} else {
			try {
				// Criar o arquivo se não existir
				Files.createFile(path);

				// Escrever o cabeçalho do CSV (opcional)
				if (cabecalhos != null && cabecalhos.size() > 0) {

					BufferedWriter writer = null;
					try {
						writer = new BufferedWriter(new FileWriter(caminhoArquivoCsv, false));
						writer.append(String.join(SEPARADOR, cabecalhos));
						writer.newLine();

					} finally {
						writer.close();
					}
				}

				System.out.println(String.format("Informação: Arquivo '%s' criado com sucesso.", caminhoArquivoCsv));
			} catch (Exception e) {
				System.err.println(String.format("Erro: Falha ao tentar inicializar o arquivo '%s': %s",
						caminhoArquivoCsv,
						e.getMessage()));
			}
		}
	}

	@Override
	public Viajante criar(Viajante objeto) throws PersistenciaException {

		// Chamar o metodo buscarTodos
		if(viajantes.isEmpty()) {

			buscarTodos();
		}

		int tamanhoLista = viajantes.size();

		if(tamanhoLista > 0) {

			// Pegar o id desse ultimo objeto
			// O novo id deve ser o id obtido do ultimo objeto incrementado de 1
			Long novoId = viajantes.keySet().stream().max(Long::compare).get() + 1l;
			// Atribuir o novo valor ao 'objeto' passado (setId)
			objeto.setId(novoId);
		} else {
			objeto.setId((long) 1);
		}

		List<String> dados = new ArrayList<String>();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivoCsv, true))) {
			// Inserir dados no CSV
			for (String cabecalho : cabecalhos) {
				String nomeMetodoGet = "get" + colocarPrimeiraLetraEmMaiusculo(cabecalho);
				Method metodoGet = objeto.getClass().getMethod(nomeMetodoGet);
				Object result = metodoGet.invoke(objeto);
				dados.add(result.toString());
			}

			String linha = String.join(SEPARADOR, dados);
			writer.write(linha);
			writer.newLine();

			// Pegar o objeto e colocar na lista de viajantes.

			viajantes.put(objeto.getId(), objeto);

		} catch (IOException e) {
			throw new PersistenciaException("Falha no acesso ao arquivo.", e);
		} catch (NoSuchMethodException e) {
			throw new PersistenciaException("Método get indisponível para algum atributo.", e);
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new PersistenciaException("Falha na execução do método get de algum atributo.", e);
		}
		return objeto;
	}

	private String colocarPrimeiraLetraEmMaiusculo(String atributo) {
		return Character.toUpperCase(atributo.charAt(0)) + atributo.substring(1);
	}

	@Override
	public Viajante buscarPorId(Long id) throws PersistenciaException {

		if(viajantes.isEmpty()) {

			buscarTodos();
		}
		for (Entry<Long, Viajante> viajante : viajantes.entrySet()) {

			if (viajante.getKey().equals(id)) {

				return (Viajante) viajante;
			}
		}

		throw new PersistenciaException("Viajante não encontrado: " + id);
	}

	@Override
	public Collection<Viajante> buscarTodos() throws PersistenciaException {
		if (!this.viajantes.isEmpty()) {
			return this.viajantes.values();
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivoCsv))) {
			reader.lines()
			.skip(1) // Pulando a linha do cabecalho
			.forEach(linha -> {
				String[] partes = linha.split(SEPARADOR);

				Long id = Long.parseLong(partes[0]);
				String nome = new String(partes[1]);
				TipoDocumento tipoDocumento = TipoDocumento.valueOf(partes[2]);
				String numeroDocumento = new String(partes[3]);
				String email = new String(partes[4]);

				Viajante viajante = new Viajante(id, nome, tipoDocumento, numeroDocumento, email);
				this.viajantes.put(id,viajante);
			});

		} catch (FileNotFoundException e) {
			throw new PersistenciaException("O arquivo não foi encontrado.", e);
		} catch (IOException e) {
			throw new PersistenciaException("Falha no acesso ao arquivo.", e);
		}

		return this.viajantes.values();
	}

	@Override
	public Collection<Viajante> pesquisar(Viajante filtro) throws PersistenciaException {

		if(viajantes.isEmpty()) {

			buscarTodos();
		}

		List<Viajante> listaResultado = new ArrayList<Viajante>();

		for (Iterator<Viajante> iterator = viajantes.values().iterator(); iterator.hasNext();) {
			Viajante viajante = (Viajante) iterator.next();

			if(viajante.atende(filtro)) {

				listaResultado.add(viajante);
			}
		}

		return listaResultado;
	}

	@Override
	public void atualizar(Long id, Viajante objeto) throws PersistenciaException {

		Viajante viajante = buscarPorId(id);

		if(!objeto.getNome().isBlank()) {
			viajante.setNome(objeto.getNome());
		}

		if(!objeto.getEmail().isBlank()) {
			viajante.setEmail(objeto.getEmail());
		}
	}



	private void salvarObjetos() {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivoCsv, false))) {

			if (cabecalhos != null && cabecalhos.size() > 0) {

				writer.append(String.join(SEPARADOR, cabecalhos));
				writer.newLine();
			}
			
			for (Viajante viajante : viajantes.values()) { 
				
				List<String> dados = new ArrayList<String>();
				
				for (String cabecalho : cabecalhos) {
					String nomeMetodoGet = "get" + colocarPrimeiraLetraEmMaiusculo(cabecalho);
					Method metodoGet = viajante.getClass().getMethod(nomeMetodoGet);
					String result = metodoGet.invoke(viajante).toString();
					dados.add((String) result);
				}
				String linha = String.join(SEPARADOR, dados);
				writer.write(linha);
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void apagar(Long id) throws PersistenciaException{

		//Encontrar o objeto no mapa;
		//Remover o objeto do mapa;

		if(viajantes.isEmpty()) {

			buscarTodos();
		}

		viajantes.remove(id);
		salvarObjetos();

		//Encontrar o objeto com o id recebido no parâmetro no arquivo;


		//Quando encontrar, remover do arquivo;

		//Percorrer a lista em buscarTodos até encontrar a linha com o id pedido
		//Remover a linha
		//Criar outra lista "regravando" todas as informações
	}

}
