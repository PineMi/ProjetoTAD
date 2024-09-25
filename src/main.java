import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// Instânciamento dos objetos principais
		Scanner scanner = new Scanner(System.in);
		ConversorInfPos conversor = new ConversorInfPos();
		AvaliadorPosfixo avaliador = new AvaliadorPosfixo();
		
		// Loop de execução principal 
		while (true) {
			// Input de comandos e expressões
			System.out.print("\n> ");
			String input = scanner.nextLine();
			
			String input_posfixo = conversor.Posfixo(input);
			
			// TODO DEBUG
			System.out.print("\nExpressão Pós-fixa: " + input_posfixo);

			int resultado = avaliador.avaliar(input_posfixo);
			// TODO DEBUG
			System.out.print("\nResultado: " + resultado);
			
			
			
			
			
			if (input.equalsIgnoreCase("EXIT")) {
				System.out.print("\nEncerrando o programa...");
				break;
			}
		}
	}
}