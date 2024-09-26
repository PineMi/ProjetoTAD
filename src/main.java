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
            
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.print("\nEncerrando o programa...");
                break;
            }

            // Lidar com o comando "VARS"
            if (input.equalsIgnoreCase("VARS")) {
                avaliador.listarVariaveis();
                continue; // Volta ao início do loop
            }

            // Lidar com o comando "RESET"
            if (input.equalsIgnoreCase("RESET")) {
                avaliador.resetVariaveis();
                continue; // Volta ao início do loop
            }
            
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
        }

        scanner.close(); // Fechar o scanner para evitar vazamento de recursos
    }
}
