package com.triagemdepacientes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.triagemdepacientes.enums.ClassificacaoRisco;

public class Paciente {

	private String nomeCompleto;
	private String cpf;
	private String sexo;
	private LocalDate dataNasc ;
	private String relatoQueixas;
	private LocalDateTime horarioEnfileiramento;
	private String senha;
	private ClassificacaoRisco classificacaoRisco;
	
	public Paciente(String nomeCompleto, String cpf, String sexo, LocalDate dataNasc, String relatoQueixas) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
		this.relatoQueixas = relatoQueixas;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(LocalDate dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getRelatoQueixas() {
		return relatoQueixas;
	}

	public void setRelatoQueixas(String relatoQueixas) {
		this.relatoQueixas = relatoQueixas;
	}

	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDateTime getHorarioEnfileiramento() {
		return horarioEnfileiramento;
	}
	

	public void setHorarioEnfileiramento(LocalDateTime horarioEnfileiramento) {
		this.horarioEnfileiramento = horarioEnfileiramento;
	}
	
	@Override
	public String toString() {
	    String senhaStr = (senha != null) ? senha : "N/A";
	    String horarioStr = "N/A";
	    
	        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
	        horarioStr = horarioEnfileiramento.format(formatador);
	    
	    return String.format("[Senha: %s, Nome: %s, CPF: %s, Entrada: %s]",
	            senhaStr,
	            nomeCompleto,
	            cpf,
	            horarioStr
	    );
	}

	public ClassificacaoRisco getClassificacaoRisco() {
		return classificacaoRisco;
	}

	public void setClassificacaoRisco(ClassificacaoRisco classificacaoRisco) {
		this.classificacaoRisco = classificacaoRisco;
	}
	
}
