package com.traigemdepacientes.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.triagemdepacientes.model.Paciente;

public class CadastroPacienteIO {

	public static void SubMenuCadastro() {

		Scanner sc = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println("===============================\n");
		System.out.println("Bem vindo a Triagem de Pacientes \n");
		System.out.println("===============================\n");

		System.out.println("Digite seu nome Completo");
		String nomeCompleto = sc.nextLine();

		System.out.println("Digite sua Data de Nascimento dd/MM/yyyy");
		String data = sc.nextLine();

		LocalDate dataNasc = LocalDate.parse(data, formatter);

		System.out.println("Digite Gênero (F| M");
		String sexo = sc.nextLine();

		// TODO: CPF não pode repetir
		System.out.println("Digite seu CPF");
		String cpf = sc.nextLine();

		System.out.println(" O que você está sentido");
		String relatoQueixas = sc.nextLine();

		Paciente paciente = new Paciente(nomeCompleto, cpf, sexo, dataNasc, relatoQueixas);

		System.out.printf(" O paciente: %s foi Cadastrado com Sucesso \n \n", nomeCompleto);
		
	}
}
