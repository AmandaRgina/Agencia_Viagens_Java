package br.com.agenciaviagens;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import br.com.agenciaviagens.entidades.Gasto;
import br.com.agenciaviagens.entidades.Viagem;
import br.com.agenciaviagens.entidades.Viajante;
import br.com.agenciaviagens.enums.TipoDocumento;
import br.com.agenciaviagens.enums.TipoGasto;
import br.com.agenciaviagens.exceptions.DocumentoNEncontradoException;
import br.com.agenciaviagens.exceptions.ServicoException;
import br.com.agenciaviagens.exceptions.ViagemNEncontradaException;
import br.com.agenciaviagens.utils.DataUtils;
import br.com.agenciaviagens.utils.NumeroUtils;

public class Aplicacao {

	/**
	 * Executa o sistema de gestao da agência de viagens.
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		Scanner sc = new Scanner(System.in);
		Agencia agencia = new Agencia();

		while (true) {
			exibeMenu("********** Sistema de Gestao da Agência de Viagens **********");
			int opcao = obtemOpcaoMenu(sc, 1, 10);
			switch (opcao) {
			case 1:
				// Listar todas as viagens
				agencia.listarViagens();
				break;
			case 2:
				// Pesquisar viajantes
				// Alterar ListarViajantes para receber as informações a serem filtradas, instanciar um objeto viajante
				// que será o filtro. Na classe Agencia implementar um novo método de pesquisa de viajantes que vai chamar o método de 
				// pesquisarViajantes que vai chamar o método pronto em ViajanteServico
				pesquisarViajantes(sc,agencia);
				break;
			case 3:
				// Listar todos os gastos de um viajante
				listarGastosViajante(sc, agencia);
				break;
			case 4:
				// Cadastrar uma viagem
				cadastrarViagem(sc, agencia);
				break;
			case 5:
				// Cadastrar um viajante
				cadastrarViajante(sc, agencia);
				break;
			case 6:
				// Cadastrar uma viagem para um viajante
				cadastrarViagemViajante(sc, agencia);
				break;
			case 7:
				// Cadastrar um gasto para um viajante
				cadastrarGastoViajante(sc, agencia);
				break;
				
			case 8:
				// Cadastrar um gasto para um viajante
				apagarViajante(sc, agencia);
				break;
			case 9:
				// Sair
				sair(sc);
				break;
			default:
				System.out.println("Opcao invalida. Digite uma opcao entre 1 e 10.");
			}
			System.out.println("Pressione alguma tecla para continuar ...");
			sc.nextLine();
		}
	}

	private static void apagarViajante(Scanner sc, Agencia agencia) {
		
		System.out.println("Informe o id do viajante");
		Long id = sc.nextLong();
		try {
			agencia.apagar(id);
			System.out.println("Viajante removido com sucesso!");
		}catch (ServicoException e) {
			System.out.println("Não foi possível apagar viajante");
		}
	}

	private static void pesquisarViajantes(Scanner sc, Agencia agencia) {

		System.out.println("Informe o nome do viajante: ");
		String nome = sc.nextLine();
		System.out.println("Informe o tipo de documento: ");
		for (TipoDocumento tipo : TipoDocumento.values()) {
			System.out.println(tipo.name());
		}
		String tipoDocumento = sc.nextLine();
		TipoDocumento tipo = null;
		if(tipoDocumento!=null && !tipoDocumento.isBlank()) {
			tipo = TipoDocumento.valueOf(tipoDocumento);
		}
		System.out.println("Informe o número do documento: ");
		String numeroDocumento = sc.nextLine();

		System.out.println("Informe o e-mail do viajante: ");
		String email = sc.nextLine();
		Viajante filtro = new Viajante(null, nome, tipo, numeroDocumento, email);

		try {
			Collection<Viajante> retorno = agencia.pesquisarViajantes(filtro);
			System.out.println(retorno);
		} catch (ServicoException e) {
			System.out.println("Não foi possível encontrar viajante");
		}
	}

	private static void sair(Scanner sc) {
		System.out.println("Encerrando a aplicacao ...");
		sc.close();
		System.exit(0);
	}

	private static void cadastrarGastoViajante(Scanner sc, Agencia agencia) {

		System.out.println("Informe o tipo de gasto: ");
		for (TipoGasto tipoGasto : TipoGasto.values()) {
			System.out.println(tipoGasto.name());
		}
		String tipoGasto = sc.nextLine();
		TipoGasto tipoGastos = TipoGasto.valueOf(tipoGasto);

		System.out.println("Informe o valor gasto no formato 0,00: ");
		String valorString = sc.nextLine();
		Double valor = null;
		try {
			valor = NumeroUtils.converte(valorString);
		} catch (ParseException e) {
			System.out.println("Valor informado inválido.");
		}

		System.out.println("Informe o número do documento do viajante: ");
		String numeroDocumento = sc.nextLine();
		Viajante viajante = null;

		try {
			viajante = agencia.getViajante(numeroDocumento);
		} catch (DocumentoNEncontradoException e) {

			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Informe o código da viagem: ");
		int codViagem = sc.nextInt();

		Viagem viagem = agencia.getViagem(codViagem);

		Gasto gasto = new Gasto(tipoGastos, valor, viagem, viajante);
		try {
			agencia.cadastrarGastoViajante(numeroDocumento, gasto);
		} catch (DocumentoNEncontradoException e) {

			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Gasto adicionado com sucesso");
	}

	private static void cadastrarViagemViajante(Scanner sc, Agencia agencia) {

		System.out.println("Informe o número do documento do viajante");
		String numeroDocumento = sc.nextLine();
		System.out.println("Informe o código da viagem");
		int codViagem = sc.nextInt();
		try {
			agencia.cadastrarViagemViajante(numeroDocumento, codViagem);
		} catch (DocumentoNEncontradoException | ViagemNEncontradaException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Viagem cadastrada com sucesso");
	}

	private static void cadastrarViajante(Scanner sc, Agencia agencia) {

		System.out.println("Informe o nome do viajante: ");
		String nome = sc.nextLine();
		System.out.println("Informe o tipo de documento: ");
		for (TipoDocumento tipo : TipoDocumento.values()) {
			System.out.println(tipo.name());
		}
		String tipoDocumento = sc.nextLine();
		TipoDocumento tipo = TipoDocumento.valueOf(tipoDocumento);
		System.out.println("Informe o número do documento: ");
		String numeroDocumento = sc.nextLine();

		System.out.println("Informe o e-mail do viajante: ");
		String email = sc.nextLine();

		try {
			agencia.cadastrarViajante(nome, tipo, numeroDocumento, email);
			System.out.println("Viajante cadastrado com sucesso!");
		} catch (ServicoException e) {
			System.out.println(String.format("Erro %d - %s", e.getCodigoErro(), e.getMessage()));
		}
	}

	private static void cadastrarViagem(Scanner sc, Agencia agencia) {

		System.out.println("Informe o destino");
		String destino = sc.nextLine();
		System.out.println("Informe a data de inicio para o destino no formato dd/MM/yyyy");
		String dataInicio = sc.nextLine();

		Date dateInicio;
		try {
			dateInicio = DataUtils.converteData(dataInicio);
		} catch (ParseException e) {
			System.out.println("Data de início inválida!");
			return;
		}

		System.out.println("Informe a data de término para o destino no formato dd/MM/yyyy");
		String dataTermino = sc.nextLine();

		Date dateTermino;
		try {
			dateTermino = DataUtils.converteData(dataTermino);
		} catch (ParseException e) {
			System.out.println("Data de término inválida!");
			return;
		}

		agencia.cadastrarViagem(destino, dateInicio, dateTermino);

		System.out.println("Viagem cadastrada com sucesso!");

	}

	private static void listarGastosViajante(Scanner sc, Agencia agencia) {

		System.out.println("Informe o número do documento do viajante");
		String numeroDocumento = sc.nextLine();

		try {
			agencia.listarGastosViajante(numeroDocumento);
		} catch (DocumentoNEncontradoException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Exibe o menu de funcionlidades do programa.
	 * 
	 * @param cabecalho Cabeçalho do programa.
	 */
	private static void exibeMenu(String cabecalho) {
		String[] opcoes = new String[] { "Listar todas as viagens ", "Pesquisar viajantes",
				"Listar todos os gastos de um viajante", "Cadastrar uma viagem", "Cadastrar uma viajante",
				"Cadastrar uma viagem para um viajante", "Cadastrar um gasto para um viajante","apagar um viajante", "Sair" };
		Menu menu = new Menu(opcoes);
		System.out.println(menu.exibeMenu(cabecalho));
	}

	/**
	 * Obtem a opcao escolhida pelo usuario.
	 * 
	 * @param inicio das opcoes de escolhas, deve ser maior que 0 (zero).
	 * @param fim das opcoes de escolhas, tambem deve ser maior que 0 (zero).
	 * 
	 * @return Opcao escolhida pelo usuario.
	 */
	private static int obtemOpcaoMenu(Scanner sc, int inicio, int fim) {
		int opcao = 0;
		System.out.println("");
		System.out.println("Informe o numero da opcao desejada: ");
		while (sc.hasNextLine()) {
			try {
				opcao = Integer.parseInt(sc.nextLine());
				if (opcao < inicio || opcao > fim) {
					throw new Exception();
				}
				return opcao;
			} catch (Exception e) {
				System.out.println(String.format("Opcao invalida. Digite uma opcao entre %d e %d.", inicio, fim));
			}
		}
		return opcao;
	}

}
