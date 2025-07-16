package com.traigemdepacientes.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.triagemdepacientes.model.Paciente;
import com.triagemdepacientes.enums.ClassificacaoRisco;
import com.triagemdepacientes.services.FilaAtendimento; 

public class CadastroPacienteIO {


    public static void SubMenuCadastro(FilaAtendimento gerenciador, Scanner sc) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n--- Cadastro de Novo Paciente ---\n");
        
     
        sc.nextLine(); 

        System.out.println("Digite seu nome Completo");
        String nomeCompleto = sc.nextLine();

        LocalDate dataNasc = null;
        
        while(dataNasc == null) {
            System.out.println("Digite sua Data de Nascimento (dd/MM/yyyy)");
            String data = sc.nextLine();
            try {
                dataNasc = LocalDate.parse(data, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, use o formato dd/MM/yyyy.");
            }
        }

        System.out.println("Digite Gênero (F|M)");
        String sexo = sc.nextLine();

        System.out.println("Digite seu CPF");
        String cpf = sc.nextLine();

        System.out.println("O que você está sentindo");
        String relatoQueixas = sc.nextLine();
        
        // 1. Cria o objeto Paciente
        Paciente paciente = new Paciente(nomeCompleto, cpf, sexo, dataNasc, relatoQueixas);
        
        // 2. Pergunta ao usuário qual a classificação
        ClassificacaoRisco classificacao = solicitarClassificacao(sc);

        // 3. USA O GERENCIADOR PARA SALVAR O PACIENTE NA FILA (PASSO ESSENCIAL)
        try {
            String senha = gerenciador.adicionarPaciente(paciente, classificacao);
            System.out.printf("\nSUCESSO! Paciente '%s' cadastrado na fila %s com a senha: %s\n\n",
                    paciente.getNomeCompleto(), classificacao.getSigla(), senha); // Verifique se o método é getSigla() ou getDescricao() no seu Enum
        } catch (IllegalArgumentException e) {
            System.err.println("\nERRO AO CADASTRAR: " + e.getMessage() + "\n");
        }
    }
    
    
    private static ClassificacaoRisco solicitarClassificacao(Scanner sc) {
        int opcao = 0;
        while (true) {
            System.out.println("\n--- Classificação de Risco ---");
            System.out.println("1 - Emergência (Vermelho)");
            System.out.println("2 - Muito Urgente (Laranja)");
            System.out.println("3 - Urgente (Amarelo)");
            System.out.println("4 - Pouco Urgente (Verde)");
            System.out.println("5 - Não Urgente (Azul)");
            System.out.print("Escolha a classificação: ");

            try {
                // Usar sc.nextLine() aqui também ajuda a evitar o mesmo problema no futuro
                opcao = Integer.parseInt(sc.nextLine()); 
                switch (opcao) {
                    case 1: return ClassificacaoRisco.EMERGENCIA;
                    case 2: return ClassificacaoRisco.MUITO_URGENTE;
                    case 3: return ClassificacaoRisco.URGENTE;
                    case 4: return ClassificacaoRisco.POUCO_URGENTE;
                    case 5: return ClassificacaoRisco.NAO_URGENTE;
                    default: System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
    }

}