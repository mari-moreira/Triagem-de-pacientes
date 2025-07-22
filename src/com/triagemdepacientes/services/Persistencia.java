package com.triagemdepacientes.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.triagemdepacientes.util.TerminalColor;

public class Persistencia {
	
private static final String NOME_ARQUIVO_SALVO = "estado_triagem.dat";

public static void salvarEstado(FilaAtendimento gerenciador) {
	try (FileOutputStream fos = new FileOutputStream(NOME_ARQUIVO_SALVO) ;
			ObjectOutputStream oos = new ObjectOutputStream(fos)){
		oos.writeObject(gerenciador);
		  System.out.println(">> Estado do sistema salvo com sucesso em " + NOME_ARQUIVO_SALVO);
		
	}catch(IOException e) {
		System.err.println("ERRO AO SALVAR O ESTADO DO ARQUIVO " + e.getMessage());
		}
}

public static FilaAtendimento carregarEstado() {
	FilaAtendimento gerenciador = null;
	File arquivo = new File(NOME_ARQUIVO_SALVO);
	if(arquivo.exists()) {
		try(FileInputStream fis = new FileInputStream(arquivo);
				ObjectInputStream ois = new ObjectInputStream(fis)){
			gerenciador = (FilaAtendimento) ois.readObject();
			 System.out.println(TerminalColor.CYAN_BOLD + ">> Estado do sistema carregado com sucesso de " +TerminalColor.RESET+ NOME_ARQUIVO_SALVO);
			
		}catch(IOException | ClassNotFoundException e ) {
			  System.err.println("ERRO AO CARREGAR O ESTADO: " + e.getMessage());
			  gerenciador = new FilaAtendimento();
			  System.out.println(">> Iniciando um novo sistema de triagem.");
		}
				
	}else {
		gerenciador = new FilaAtendimento();
		 System.out.println(TerminalColor.CYAN_BOLD + ">> Nenhum estado salvo encontrado. \nIniciando um novo sistema de triagem"
		 		+ TerminalColor.RESET + "\n\n\n");
    }
	return gerenciador;
	}

}
