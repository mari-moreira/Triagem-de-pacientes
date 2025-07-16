import java.util.Scanner;

import com.traigemdepacientes.menu.CadastroPacienteIO;
import com.traigemdepacientes.menu.MenuIO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		int opcao = 0;

		do {
			opcao = MenuIO.menu(sc);
			switch (opcao) {
			case 1: {
				CadastroPacienteIO.SubMenuCadastro();
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
