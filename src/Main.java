import java.util.Scanner;

import com.traigemdepacientes.menu.CadastroPacienteIO;
import com.traigemdepacientes.menu.MenuIO;
import com.triagemdepacientes.enums.ClassificacaoRisco;
import com.triagemdepacientes.services.FilaAtendimento;
import com.triagemdepacientes.util.TerminalColor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		FilaAtendimento filaAtendimento = new FilaAtendimento();

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
				System.out.println(TerminalColor.GREEN_BOLD + "Programa Encerrado com Sucesso" + TerminalColor.RESET);
				break;
			}
			default:
				System.out.println(TerminalColor.RED_BOLD + "Algo deu errado. Tente novamente mais tarde" + TerminalColor.RESET);
			}

		} while (opcao != 7);
		sc.close();
	}

}
