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
            
            if (input.contains("=")) {
                String[] partes = input.split("=");
                String variavel = partes[0].trim();
                int valor = Integer.parseInt(partes[1].trim());
                avaliador.atribuir(variavel, valor);
                System.out.println("Atribuído: " + variavel + " = " + valor);
            } else {
                String input_posfixo = conversor.Posfixo(input);
                System.out.print("\nExpressão Pós-fixa: " + input_posfixo + "\n");

                int resultado = avaliador.avaliar(input_posfixo);
                if (resultado != -1) { // Verifica se ocorreu algum erro
                    System.out.print("\nResultado: " + resultado);
                }
            }

            if (input.equalsIgnoreCase("EXIT")) {
                System.out.print("\nEncerrando o programa...");
                break;
            }
        }

        scanner.close(); // Fechar o scanner para evitar vazamento de recursos
    }
}
