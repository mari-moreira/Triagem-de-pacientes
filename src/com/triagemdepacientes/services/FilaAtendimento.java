package com.triagemdepacientes.services;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.triagemdepacientes.enums.ClassificacaoRisco;
import com.triagemdepacientes.model.*;
import com.triagemdepacientes.util.TerminalColor;

public class FilaAtendimento implements Serializable{
	private static final long serialVersionUID= 1L;
	private final Map<ClassificacaoRisco, Queue<Paciente>> filas;
	private final Map<ClassificacaoRisco, Integer> contadoresSenha;
	private final Set<String> cpfsRegistrados;
	private final Map<String, Paciente> pacientesEmFilaCpf;
	private final Map<ClassificacaoRisco, Integer> totalPacientesAtendidosPorClassificacao;
	private final Map<ClassificacaoRisco, Integer> totalPacientesAtendidosNoTempoPorClassificacao;

	public FilaAtendimento() {

		this.filas = new EnumMap<>(ClassificacaoRisco.class);
		this.contadoresSenha = new EnumMap<>(ClassificacaoRisco.class);
		this.cpfsRegistrados = new HashSet<>();
		this.pacientesEmFilaCpf = new HashMap<String, Paciente>();
		this.totalPacientesAtendidosPorClassificacao = new EnumMap<>(ClassificacaoRisco.class);
		this.totalPacientesAtendidosNoTempoPorClassificacao = new EnumMap<>(ClassificacaoRisco.class);

		for (ClassificacaoRisco c : ClassificacaoRisco.values()) {
			filas.put(c, new LinkedList<>());
			contadoresSenha.put(c, 1);

			this.totalPacientesAtendidosPorClassificacao.put(c, 0);
			this.totalPacientesAtendidosNoTempoPorClassificacao.put(c, 0);
		}

	}
	//TODO: REFACTOR: ESTÁ ESTRANHO UM MÉTODO QUE ADICIONA PACINTE E RETORNA UMA SENHA

	public String adicionarPaciente(Paciente paciente, ClassificacaoRisco classificacaoRisco) {
		 if (cpfJaExiste(paciente.getCpf())) {
		        throw new IllegalArgumentException("CPF " + paciente.getCpf() + " já foi Cadastrado");
		    }
		int sequenciaSenha = contadoresSenha.get(classificacaoRisco);
		String senha = classificacaoRisco.getSigla() + "." + sequenciaSenha;
		paciente.setSenha(senha);
		paciente.setHorarioEnfileiramento(LocalDateTime.now());
		paciente.setClassificacaoRisco(classificacaoRisco);
		Queue<Paciente> filaAdequada = this.filas.get(classificacaoRisco);
		filaAdequada.add(paciente);

		contadoresSenha.put(classificacaoRisco, sequenciaSenha + 1);
		cpfsRegistrados.add(paciente.getCpf());
		this.pacientesEmFilaCpf.put(paciente.getCpf(), paciente);

		return senha;

	}
	
	public boolean cpfJaExiste(String cpf) {
		return this.cpfsRegistrados.contains(cpf);
	}

	public Paciente chamarProximoPaciente() {
		for (ClassificacaoRisco prioridade : ClassificacaoRisco.values()) {
			Queue<Paciente> fila = filas.get(prioridade);
			if (!fila.isEmpty())
				return chamarProximoPaciente(prioridade);
		}
		System.out.println("As filas estão vazias");
		return null;
	}

	public Paciente chamarProximoPaciente(ClassificacaoRisco classificacaoRisco) {
		Queue<Paciente> fila = filas.get(classificacaoRisco);
		Paciente pacienteChamado = fila.poll();

		if (pacienteChamado != null) {
			this.pacientesEmFilaCpf.remove(pacienteChamado.getCpf());
			LocalDateTime horaAtual = LocalDateTime.now();
			LocalDateTime horarioEntrada = pacienteChamado.getHorarioEnfileiramento();

			long tempoEmFila = Duration.between(horarioEntrada, horaAtual).toMinutes();

			System.out.println(TerminalColor.YELLOW_BOLD + "\n---Paciente Chamado ---" + TerminalColor.RESET);
			System.out.println("Nome: " + pacienteChamado.getNomeCompleto());
			System.out.println("Senha: " + pacienteChamado.getSenha());
			System.out.println("Fila: " + classificacaoRisco.getDescricao());
			System.out.println("Tempo de permanência: " + tempoEmFila + " minutos");

			System.out.println("\n---------------------- ---");

			long minutosEmFila = Duration.between(pacienteChamado.getHorarioEnfileiramento(), LocalDateTime.now())
					.toMinutes();
			int tempoMetaProtocolo = classificacaoRisco.getTempo();

			// TODO: Refatorar
			int totalAtendidos = totalPacientesAtendidosPorClassificacao.get(classificacaoRisco);
			totalPacientesAtendidosPorClassificacao.put(classificacaoRisco, totalAtendidos++);

			if (minutosEmFila <= tempoMetaProtocolo) {
				int pacientesAtendidosNoTempo = totalPacientesAtendidosNoTempoPorClassificacao.get(classificacaoRisco);
				totalPacientesAtendidosNoTempoPorClassificacao.put(classificacaoRisco, pacientesAtendidosNoTempo++);
			}
		} else {
			System.out.println(" A fila " + classificacaoRisco.getSigla() + "está vazia");
		}
		return pacienteChamado;
	}

	public void consultarProximoPaciente(ClassificacaoRisco classificacaoRisco) {
		Queue<Paciente> fila = filas.get(classificacaoRisco);
		Paciente proximoPaciente = fila.peek();
		if (proximoPaciente != null) {
			long minutosEmFila = Duration.between(proximoPaciente.getHorarioEnfileiramento(), LocalDateTime.now())
					.toMinutes();

			System.out.println(TerminalColor.YELLOW_BOLD + "\nPRÓXIMO PACIENTE DA FILA " + classificacaoRisco.getDescricao().toUpperCase() + ":" + TerminalColor.RESET);
			System.out.println("-----------------------------------------");
			System.out.println("  DADOS: " + proximoPaciente);
			System.out.println("  TEMPO ATUAL DE ESPERA: " + minutosEmFila + " minutos.");
			System.out.println("-----------------------------------------");
		} else {
			System.out.println(TerminalColor.YELLOW_BOLD + "\nℹ️ A fila " + classificacaoRisco.getDescricao() + " está vazia." + TerminalColor.RESET);
		}
	}

	public void verificarIminencia(String cpf) {
		Paciente paciente = this.pacientesEmFilaCpf.get(cpf);
		if (paciente == null) {
			System.out.println(TerminalColor.RED_BOLD + "\n Paciente com CPF " + cpf + " não foi encontrado em nenhuma fila de espera." + TerminalColor.RESET);
			return;
		}
		Queue<Paciente> fila = filas.get(paciente.getClassificacaoRisco());

		Paciente primeiroDaFila = fila.peek();
		if (paciente.equals(primeiroDaFila))
			System.out.println(TerminalColor.GREEN_BOLD+ "\n✅ SIM! O atendimento para " + paciente.getNomeCompleto() + " é iminente. Ele(a) "
					+ "é o próximo(a) da fila " + paciente.getClassificacaoRisco() + "." + TerminalColor.RESET);
		else
			System.out.println(TerminalColor.RED_BOLD+"\n❌ NÃO. O atendimento para " + paciente.getNomeCompleto() + " não é iminente."+ TerminalColor.RESET);

	}

	public void visualizarFilas() {
		System.out.println(TerminalColor.CYAN_BOLD+ "\n==============================================");
		System.out.println("        ESTADO ATUAL DE TODAS AS FILAS");
		System.out.println("==============================================" + TerminalColor.RESET);

		for (ClassificacaoRisco c : ClassificacaoRisco.values()) {
			System.out.println(TerminalColor.GREEN_BOLD+"\n --- FILA: " + c.getDescricao() + "---"+  TerminalColor.RESET);
			Queue<Paciente> fila = filas.get(c);
			if (fila.isEmpty())
				System.out.println(TerminalColor.YELLOW_BOLD +" [ FILA VAZIA] " + TerminalColor.RESET);
			else {
				fila.forEach(System.out::println);
			}
		}
		System.out.println("==============================================");
	}

	// TODO: Dividir esse método em vários
	public void exibirEstatisticas() {
		//Pacientes por fila
		System.out.println(TerminalColor.GREEN_BOLD + "==============================================");
		System.out.println ("\n\n RELATÓRIO ESTATÍSTICO DO ATENDIMENTO");
		System.out.println("==============================================\n\n" + TerminalColor.RESET) ;
		
		System.out.println(TerminalColor.CYAN_BOLD+"\nPACIENTES AGUARDANDO POR FILA"+ TerminalColor.RESET);
		for (ClassificacaoRisco c : ClassificacaoRisco.values()) {
			System.out.printf("  - %-15s: %d paciente(s)\n", c.getDescricao(), filas.get(c).size());
		}

		//Pacientes por fila
		System.out.println("\nPACIENTES AGUARDANDO POR FAIXA ETÁRIA");
		int criancas = 0, adolescentes = 0, adultos = 0, idosos = 0;
		LocalDate dataHoje = LocalDate.now();

		for (Queue<Paciente> fila : filas.values()) {
			for (Paciente p : fila) {
				LocalDate dataNasc = p.getDataNasc();
				int idade = (int) ChronoUnit.YEARS.between(dataNasc, dataHoje);
				if (idade <= 12)
					criancas++;
				else if (idade <= 17)
					adolescentes++;
				else if (idade <= 59)
					adultos++;
				else
					idosos++;
			}
		}
		
		System.out.printf("  - Crianças (0-12): %d\n", criancas);
		System.out.printf("  - Adolescentes (13-17): %d\n", adolescentes);
		System.out.printf("  - Adultos (18-59): %d\n", adultos);
		System.out.printf("  - Idosos (60+): %d\n", idosos);

		//Tempo médio de espera
		System.out.println(TerminalColor.CYAN_BOLD+"\n TEMPO MÉDIO DE ESPERA ATUAL [em minutos]\n"+TerminalColor.RESET);
		for (ClassificacaoRisco c : ClassificacaoRisco.values()) {
			Queue<Paciente> fila = filas.get(c);
			if (fila.isEmpty()) {
				System.out.printf("  - %-15s: N/A (fila vazia)\n", c.getDescricao());
			} else {
				long somaDoTempo = 0;
				for (Paciente p : fila) {
					somaDoTempo += Duration.between(p.getHorarioEnfileiramento(), LocalDateTime.now()).toMinutes();
				}
				double media = (double) somaDoTempo / fila.size();
				System.out.printf("  - %-15s: %.1f min\n", c.getDescricao(), media);
			}
		}
			
//Performance de atendimento
			System.out.println(TerminalColor.CYAN_BOLD+"\n PERFORMANCE DE ATENDIMENTO (dos que já foram atendidos):\n"+TerminalColor.RESET);
			for (ClassificacaoRisco c : ClassificacaoRisco.values()) {
				int totalPacientesAtendidos = totalPacientesAtendidosNoTempoPorClassificacao.get(c);
				if (totalPacientesAtendidos == 0) {
					System.out.printf("  - %-15s: N/A (nenhum paciente atendido)\n", c.getDescricao());
				} else {
					int totalPacientesAtendidosNoTempo = totalPacientesAtendidosNoTempoPorClassificacao.get(c);
					double percentual = ((double) totalPacientesAtendidosNoTempo / totalPacientesAtendidos) * 100.0;
					System.out.printf("  - %-15s: %.1f%% atendidos dentro da meta de %d min (%d de %d).\n", 
							 c.getDescricao(), percentual, c.getTempo(), totalPacientesAtendidosNoTempo, totalPacientesAtendidos);				}
			}
		}
		
	
	public boolean isSistemaVazio() {
		return this.pacientesEmFilaCpf.isEmpty();
	}
	
}
