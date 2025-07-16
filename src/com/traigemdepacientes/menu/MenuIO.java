package com.traigemdepacientes.menu;

import java.util.Scanner;

public class MenuIO {
	 public static int menu(Scanner sc) {
		 
		 System.out.print("*** MENU PRINCIPAL***\n \n");
		 System.out.println("Escolha uma " + "opção de 1 -5 \n" + "1 - CADASTRAR PACIENTE \n"  + "2- VISUALIZAR FILAS \n" 
		 +"5 - SAIR\n");
						return sc.nextInt();
		
	 }

}
