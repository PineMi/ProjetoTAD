import java.util.Scanner;

public class main {
	public static void main(String[] args) {
	    // Instância dos objetos principais
	    Scanner scanner = new Scanner(System.in);
	    ConversorInfPos conversor = new ConversorInfPos();
	    AvaliadorPosfixo avaliador = new AvaliadorPosfixo();
	    FilaCircular<String> comandos = new FilaCircular<>(10); // Fila para gravar os comandos 
	    boolean gravando = false;

	    // Loop de execução principal
	    while (true) {
	        // Input de comandos e expressões
	        System.out.print("> ");
	        String input = scanner.nextLine().trim();

	        if (input.equalsIgnoreCase("EXIT")) {
	            System.out.print("\nEncerrando o programa...");
	            break;
	        }

	        // Comando "REC" para iniciar gravação
	        if (input.equalsIgnoreCase("REC")) {
	            if (gravando) {
	                System.out.println("Já está em modo REC.");
	            } else {
	                gravando = true;
	                System.out.println("Iniciando gravação... (REC: 0/10)");
	            }
	            continue; // Volta ao início do loop
	        }

	        // Comando "STOP" para parar gravação
	        if (input.equalsIgnoreCase("STOP")) {
	            if (!gravando) {
	                System.out.println("Erro: não há gravação em andamento.");
	            } else {
	                gravando = false;
	                System.out.println("Encerrando gravação...");
	            }
	            continue; // Volta ao início do loop
	        }

	        // Comando "PLAY" para reproduzir a gravação
	        if (input.equalsIgnoreCase("PLAY")) {
	            if (gravando) {
	                System.out.println("Erro: comando inválido para gravação.");
	            } else if (comandos.qIsEmpty()) {
	                System.out.println("Não há gravação para ser reproduzida.");
	            } else {
	                System.out.println("Reproduzindo gravação...");
	                
	                int totalComandos = comandos.totalElementos();
	                for (int i = 0; i < totalComandos; i++) {
	                    try {
	                        String comando = comandos.dequeue(); // Remove o comando da fila
	                        if (comando.matches(".*[a-zA-Z].*") && comando.matches(".*[^a-zA-Z0-9=\\s].*")) {
	                        	System.out.print("["+comando+"] ");
	                        }
	                        executarComando(comando, comandos, conversor, avaliador, false, true); // Executa o comando gravado
	                        comandos.enqueue(comando);
	                    } catch (Exception e) {
	                        System.out.println("Erro ao reproduzir comando: " + e.getMessage());
	                    }
	                }
	            }
	            continue; // Volta ao início do loop
	        }

	        // Comando "ERASE" para apagar a gravação
	        if (input.equalsIgnoreCase("ERASE")) {
	            comandos = new FilaCircular<>(10); // Limpa a fila de comandos
	            System.out.println("Gravação apagada.");
	            continue; // Volta ao início do loop
	        }
	        
	        
	        
            // Chamando executarComando
	        executarComando(input, comandos, conversor, avaliador, gravando, true);
	    }

	    scanner.close(); // Fecha o scanner para evitar vazamento de recursos
	}
	
 // Função auxiliar para executar comandos, com gravação
    private static void executarComando(String comando, FilaCircular<String> comandos, ConversorInfPos conversor, AvaliadorPosfixo avaliador, boolean gravando, boolean exibirResultado) {
        if (gravando) {
            try {
                comandos.enqueue(comando); // Grava o comando na fila
                System.out.println("(REC: " + comandos.totalElementos() + "/10) " + comando);
                if (comandos.totalElementos() >= 10) {
                    System.out.println("Limite de gravação atingido. Encerrando gravação.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Erro ao gravar comando: " + e.getMessage());
            }
        } else {
            if (comando.contains("=")) {
                String[] partes = comando.split("=");
                String variavel = partes[0].trim();
                int valor = Integer.parseInt(partes[1].trim());
                avaliador.atribuir(variavel, valor);
                if (exibirResultado) {
                    System.out.println("Atribuído: " + variavel + " = " + valor);
                }
            } else if (comando.equalsIgnoreCase("VARS")) {
                avaliador.listarVariaveis();
            } else if (comando.equalsIgnoreCase("RESET")) {
                avaliador.resetVariaveis();
                System.out.println("Variáveis reiniciadas.");
            } else if (comando.length() > 1 && comando.matches("[a-zA-Z]+")) {
	            System.out.println("Erro: comando inválido '" + comando + "'."); // Gera erro para comando inválido	            
            } else {
                try {
                    String resultado = avaliador.avaliar(conversor.Posfixo(comando));
                    if (exibirResultado) {
                        System.out.println(resultado);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao avaliar a expressão: " + comando);
                }
            }
        }
    }
}
