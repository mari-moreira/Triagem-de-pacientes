package com.triagemdepacientes.enums;

public enum ClassificacaoRisco {
	// Agora passamos 3 valores: a descrição, a sigla e o tempo
	EMERGENCIA("Emergência", "E", 4),
	MUITO_URGENTE("Muito Urgente", "M", 10), 
	URGENTE("Urgente", "U", 50), 
	POUCO_URGENTE("Pouco Urgente", "P", 120),
	NAO_URGENTE("Não Urgente", "N", 240);

	private final String descricao; 
	private final String sigla;
	private final int tempo;


	private ClassificacaoRisco(String descricao, String sigla, int tempo) {
		this.descricao = descricao;
		this.sigla = sigla;
		this.tempo = tempo;
	}

	// Novo getter para a descrição
	public String getDescricao() {
		return descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public int getTempo() {
		return tempo;
	}
}