package com.traigemdepacientes.menu;

import java.util.Scanner;

import com.triagemdepacientes.util.TerminalColor;

public class MenuIO {
	 public static int menu(Scanner sc) {
		 
		 System.out.print(TerminalColor.CYAN_BOLD+ "\r\n"
		 		+ "█▀▄▀█ █▀▀ █▄░█ █░█   █▀█ █▀█ █ █▄░█ █▀▀ █ █▀█ ▄▀█ █░░\r\n"
		 		+ "█░▀░█ ██▄ █░▀█ █▄█   █▀▀ █▀▄ █ █░▀█ █▄▄ █ █▀▀ █▀█ █▄▄ " +  TerminalColor.RESET + "\n \n");
		 System.out.println(TerminalColor.YELLOW + "Escolha uma " + "opção de 1 - 7 \n" + TerminalColor.RESET+ "1 - CADASTRAR PACIENTE \n"  + "2- VISUALIZAR FILAS \n" 
				 + "3- CHAMAR PRÓXIMO PACIENTE \n"+"4- CONSULTAR PRÓXIMO PACIENTE \n" + 
				 "5- CONSULTAR IMINÊNCIA PELO CPF\n"+
				 "6- EXIBIR DADOS ESTATÍSTICOS \n" + "7 - SAIR\n\n"+ "Digite sua opção: ");
						return sc.nextInt();
		
	 }

}
