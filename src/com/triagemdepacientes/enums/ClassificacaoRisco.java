package com.triagemdepacientes.enums;

//TODO: Testar a cor 
public enum ClassificacaoRisco {
	EMERGENCIA("E", 4),
	MUITO_URGENTE("M", 10), 
	URGENTE("U", 50), POUCO_URGENTE("P", 120),
	NAO_URGENTE("N", 240);

	private final String sigla;
	private final int tempo;

	private ClassificacaoRisco(String sigla, int tempo) {
		this.sigla = sigla;
		this.tempo = tempo;
	}

	public String getSigla() {
		return sigla;
	}

	public int getTempo() {
		return tempo;
	}

}
