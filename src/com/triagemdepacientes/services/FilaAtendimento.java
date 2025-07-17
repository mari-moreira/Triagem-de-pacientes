package com.triagemdepacientes.services;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.triagemdepacientes.enums.ClassificacaoRisco;
import com.triagemdepacientes.model.*;

public class FilaAtendimento {
	private final Map<ClassificacaoRisco, Queue<Paciente>> filas;
	private final Map<ClassificacaoRisco, Integer> contadoresSenha;
	private final Set<String> cpfsRegistrados;

	public FilaAtendimento() {

		this.filas = new EnumMap<>(ClassificacaoRisco.class);
		this.contadoresSenha = new EnumMap<>(ClassificacaoRisco.class);
		this.cpfsRegistrados = new HashSet<>();

		for (ClassificacaoRisco c : ClassificacaoRisco.values()) {
			filas.put(c, new LinkedList<>());
			contadoresSenha.put(c, 1);
		}
		
	}

	public  String adicionarPaciente(Paciente paciente, ClassificacaoRisco classificacaoRisco) {
		if(cpfsRegistrados.contains(paciente.getCpf())) 
			throw new IllegalArgumentException("CPF " + paciente.getCpf() + " já foi Cadastrado");
		
		int sequenciaSenha = contadoresSenha.get(classificacaoRisco);
		String senha = classificacaoRisco.getSigla() + "." + sequenciaSenha;
		paciente.setSenha(senha);
		paciente.setHorarioEnfileiramento(LocalDateTime.now());
		
		Queue<Paciente> filaAdequada = this.filas.get(classificacaoRisco);
		filaAdequada.add(paciente);
		
		contadoresSenha.put(classificacaoRisco, sequenciaSenha + 1);
		cpfsRegistrados.add(paciente.getCpf());
		
		
		return senha;
		
	}
	
	public void visualizarFilas() {
	       System.out.println("\n==============================================");
	        System.out.println("        ESTADO ATUAL DE TODAS AS FILAS");
	        System.out.println("==============================================");
	        
	        for(ClassificacaoRisco c : ClassificacaoRisco.values()) {
	        	System.out.println("\n --- FILA: " + c.getDescricao()+ "---");
	        	Queue<Paciente> fila = filas.get(c);
	        	if(fila.isEmpty())
	        		System.out.println(" [ FILA VAZIA] ");
	        	else {
	        		fila.forEach(System.out::println);
	        	}
	        }
	        System.out.println("==============================================");   
	}
}
