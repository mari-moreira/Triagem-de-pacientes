package com.traigemdepacientes.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.triagemdepacientes.model.Paciente;
import com.triagemdepacientes.enums.ClassificacaoRisco;
import com.triagemdepacientes.services.FilaAtendimento;
import com.triagemdepacientes.util.TerminalColor;

public class CadastroPacienteIO {

	public static void SubMenuCadastro(FilaAtendimento gerenciador, Scanner sc) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println(TerminalColor.YELLOW_BOLD + "\n--- Cadastro de Novo Paciente ---\n"+ TerminalColor.RESET);

		sc.nextLine();

		System.out.println("Digite seu nome Completo");
		String nomeCompleto = sc.nextLine();

		LocalDate dataNasc = null;

		while (dataNasc == null) {
			System.out.println("Digite sua Data de Nascimento (dd/MM/yyyy)");
			String data = sc.nextLine();
			try {
				dataNasc = LocalDate.parse(data, formatter);
			} catch (DateTimeParseException e) {
				System.out.println(TerminalColor.RED_BOLD + "Formato de data inválido. Por favor, use o formato dd/MM/yyyy." + TerminalColor.RESET);
			}
		}

		System.out.println("Digite Gênero (F|M)");
		String sexo = sc.nextLine();
		String cpf;
		while (true) { 
		    System.out.println("Digite seu CPF");
		    cpf = sc.nextLine();

		    if (gerenciador.cpfJaExiste(cpf)) {
		    	  System.err.println("ERRO: Este CPF já está cadastrado. Por favor, digite um CPF diferente.");
		    	    
		    } else {
		        break; 
		    }
		}
		System.out.println("O que você está sentindo");
		String relatoQueixas = sc.nextLine();

		Paciente paciente = new Paciente(nomeCompleto, cpf, sexo, dataNasc, relatoQueixas);

		ClassificacaoRisco classificacao = solicitarClassificacao(sc);

		try {
			String senha = gerenciador.adicionarPaciente(paciente, classificacao);
			System.out.printf(TerminalColor.GREEN_BOLD+"\n[ SUCESSO! Paciente: %s cadastrado na fila:  %s com a senha: %s ]\n\n",
					paciente.getNomeCompleto(), classificacao.getDescricao(), senha);
		} catch (IllegalArgumentException e) {
			System.err.println(TerminalColor.RED_BOLD + "\nERRO AO CADASTRAR: " + e.getMessage() + "\n" + TerminalColor.RESET);
		}
	}

	public static ClassificacaoRisco solicitarClassificacao(Scanner sc) {
		int opcao = 0;
		while (true) {
			System.out.println(TerminalColor.YELLOW_BOLD + "\n--- Classificação de Risco ---" + TerminalColor.RESET);
			System.out.println("1 - Emergência (Vermelho)");
			System.out.println("2 - Muito Urgente (Laranja)");
			System.out.println("3 - Urgente (Amarelo)");
			System.out.println("4 - Pouco Urgente (Verde)");
			System.out.println("5 - Não Urgente (Azul)");
			System.out.print("Escolha a classificação: ");

			try {
				opcao = Integer.parseInt(sc.nextLine());
				switch (opcao) {
				case 1:
					return ClassificacaoRisco.EMERGENCIA;
				case 2:
					return ClassificacaoRisco.MUITO_URGENTE;
				case 3:
					return ClassificacaoRisco.URGENTE;
				case 4:
					return ClassificacaoRisco.POUCO_URGENTE;
				case 5:
					return ClassificacaoRisco.NAO_URGENTE;
				default:
					System.out.println(TerminalColor.RED_BOLD + "Opção inválida. Tente novamente." + TerminalColor.RESET);
				}
			} catch (NumberFormatException e) {
				System.out.println(TerminalColor.RED_BOLD + "Entrada inválida. Por favor, digite um número." + TerminalColor.RESET);
			}
		}
	}
}