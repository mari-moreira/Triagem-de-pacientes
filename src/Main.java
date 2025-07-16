import java.util.Scanner;

import com.traigemdepacientes.menu.CadastroPacienteIO;
import com.traigemdepacientes.menu.MenuIO;
import com.triagemdepacientes.services.FilaAtendimento;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		FilaAtendimento filaAtendimento = new FilaAtendimento();
		
		int opcao = 0;
		
		System.out.println("===============================\n");
		System.out.println("Bem vindo a Triagem de Pacientes \n");
		System.out.println("===============================\n");

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
			case 5: {
				System.out.println("Programa encerrado ");
				break;
			}
			default:
				System.out.println("Algo deu errado. Tente novamente mais tarde");
			}

		} while (opcao != 5);

	}

}
