import java.time.LocalDate;
import java.util.Scanner;

import com.traigemdepacientes.menu.CadastroPacienteIO;
import com.traigemdepacientes.menu.MenuIO;
import com.triagemdepacientes.enums.ClassificacaoRisco;
import com.triagemdepacientes.model.Paciente;
import com.triagemdepacientes.services.FilaAtendimento;
import com.triagemdepacientes.util.TerminalColor;
import com.triagemdepacientes.services.Persistencia;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		FilaAtendimento filaAtendimento = Persistencia.carregarEstado();
		
		
	
	    if(filaAtendimento.isSistemaVazio()) { 
            gerarDadosDeTeste(filaAtendimento);
       }
       
		int opcao = 0;

		System.out.println(TerminalColor.GREEN_BOLD + "\r\n"
				+ "▀█▀ █▀█ █ ▄▀█ █▀▀ █▀▀ █▀▄▀█   █▀▄ █▀▀   █▀█ ▄▀█ █▀▀ █ █▀▀ █▄░█ ▀█▀ █▀▀ █▀\r\n"
				+ "░█░ █▀▄ █ █▀█ █▄█ ██▄ █░▀░█   █▄▀ ██▄   █▀▀ █▀█ █▄▄ █ ██▄ █░▀█ ░█░ ██▄ ▄█ \n");
		System.out.println( TerminalColor.RESET);

		do {
			opcao = MenuIO.menu(sc);
			switch (opcao) {
			case 1: {
				CadastroPacienteIO.SubMenuCadastro(filaAtendimento, sc);
				break;
			}
			case 2: {
				filaAtendimento.visualizarFilas();
				break;
			}
			case 3: {
				filaAtendimento.chamarProximoPaciente();
				break;
			}
			case 4: {
				
				System.out.println(TerminalColor.YELLOW_BOLD + "De qual fila você deseja consultar o próximo paciente?" + TerminalColor.RESET);
				sc.nextLine();
				ClassificacaoRisco filaEscolhida = CadastroPacienteIO.solicitarClassificacao(sc);

				filaAtendimento.consultarProximoPaciente(filaEscolhida);
				break;
			}
			case 5: {
				System.out.print(TerminalColor.YELLOW_BOLD  + "\nDigite o CPF do paciente para verificar a iminência (só números): "+ TerminalColor.RESET);
				sc.nextLine();
				String cpfParaConsulta = sc.nextLine();

				filaAtendimento.verificarIminencia(cpfParaConsulta);
				break;
			}
			case 6: {
				filaAtendimento.exibirEstatisticas();
				break;
			}
			case 7: {
				Persistencia.salvarEstado(filaAtendimento);
				System.out.println(TerminalColor.GREEN_BOLD + "Programa Encerrado com Sucesso" + TerminalColor.RESET);
				break;
			}
			default:
				System.out.println(TerminalColor.RED_BOLD + "Algo deu errado. Tente novamente mais tarde" + TerminalColor.RESET);
			}

		} while (opcao != 7);
		sc.close();
	}
	public static void gerarDadosDeTeste(FilaAtendimento gerenciador) {
	    System.out.println(TerminalColor.YELLOW_BOLD + ">> MODO DE TESTE: Gerando pacientes fictícios..." + TerminalColor.RESET);

	    // Usaremos try-catch para o caso de você rodar este método mais de uma vez
	    // com um arquivo de estado já salvo. Ele simplesmente ignorará CPFs duplicados.
	    try {
	        // Paciente 1: Idoso, Emergência
	        Paciente p1 = new Paciente("José Bezerra", "11111111111", "M", LocalDate.of(1955, 4, 10), "Dor no peito e falta de ar");
	        gerenciador.adicionarPaciente(p1, ClassificacaoRisco.EMERGENCIA);

	        // Paciente 2: Adulto, Urgente
	        Paciente p2 = new Paciente("Mariana Costa", "22222222222", "F", LocalDate.of(1992, 8, 22), "Fratura exposta no braço");
	        gerenciador.adicionarPaciente(p2, ClassificacaoRisco.URGENTE);

	        // Paciente 3: Criança, Pouco Urgente
	        Paciente p3 = new Paciente("Lucas Martins", "33333333333", "M", LocalDate.of(2017, 1, 15), "Resfriado e tosse leve");
	        gerenciador.adicionarPaciente(p3, ClassificacaoRisco.POUCO_URGENTE);

	        // Paciente 4: Adulto, Urgente
	        Paciente p4 = new Paciente("Carla Dias", "44444444444", "F", LocalDate.of(1988, 7, 30), "Corte profundo no dedo");
	        gerenciador.adicionarPaciente(p4, ClassificacaoRisco.URGENTE);

	        // Paciente 5: Adolescente, Não Urgente
	        Paciente p5 = new Paciente("Rafael Souza", "55555555555", "M", LocalDate.of(2008, 11, 5), "Troca de receita de rotina");
	        gerenciador.adicionarPaciente(p5, ClassificacaoRisco.NAO_URGENTE);
	        
	        // Paciente 6: Idoso, Muito Urgente
	        Paciente p6 = new Paciente("Sônia Abreu", "66666666666", "F", LocalDate.of(1948, 2, 18), "Pico de pressão alta e tontura");
	        gerenciador.adicionarPaciente(p6, ClassificacaoRisco.MUITO_URGENTE);
	        
	        // Paciente 7: Adulto, Pouco Urgente
	        Paciente p7 = new Paciente("Fernando Lima", "77777777777", "M", LocalDate.of(1999, 6, 25), "Dor de garganta");
	        gerenciador.adicionarPaciente(p7, ClassificacaoRisco.POUCO_URGENTE);
	        
	        // Paciente 8: Idoso, Não Urgente
	        Paciente p8 = new Paciente("Teresa Mendes", "88888888888", "F", LocalDate.of(1960, 12, 1), "Alergia de pele leve");
	        gerenciador.adicionarPaciente(p8, ClassificacaoRisco.NAO_URGENTE);

	    } catch (IllegalArgumentException e) {
	        // Se o CPF já existir (de uma execução anterior), apenas imprime um aviso.
	        System.out.println(TerminalColor.YELLOW + ">> AVISO: Dados de teste já parecem existir. " + e.getMessage() + TerminalColor.RESET);
	    }
	    System.out.println(TerminalColor.YELLOW_BOLD + ">> Geração de dados concluída." + TerminalColor.RESET);

	}

}
