package com.triagemdepacientes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Paciente {

	private String nomeCompleto;
	private String cpf;
	private String sexo;
	private LocalDate dataNasc ;
	private String relatoQueixas;
	private LocalDateTime horarioEnfileiramento;
	
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

	public LocalDateTime getHorarioEnfileiramento() {
		return horarioEnfileiramento;
	}

	public void setHorarioEnfileiramento(LocalDateTime horarioEnfileiramento) {
		this.horarioEnfileiramento = horarioEnfileiramento;
	}
	
	public String consultarFaixaEtaria(int idade) {
		if(idade <= 12 ) {
			return "Criança";
		}else if(idade <=17) {
			return "Adolescente";
		}else if(idade <=59) {
			return "Adulto";
		}else {
			return "Idoso";
		}
	}
	
}
