// PROJETO - ESTRUTURA DE DADOS - TAD
// Bruno Germanetti Ramalho - RA 10426491
// Miguel Piñeiro Coratolo Simões - RA 10427085
// 01/10/2024 - 3ºSemestre - Ciências da Computação
// Universidade Presbiteriana Mackenzie - FCI

// Referências:
// Reverse Polish Notation and The Stack - https://www.youtube.com/watch?v=7ha78yWRDlE
// Java Guides - https://www.javaguides.net/2024/06/

import java.util.Scanner;

public class main {
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    ConversorInfPos conversor = new ConversorInfPos();
	    AvaliadorPosfixo avaliador = new AvaliadorPosfixo();
	    FilaCircular<String> comandos = new FilaCircular<>(10); 
	    boolean gravando = false; 

	    // Loop principal de execução do programa.
	    while (true) {
	        // Entrada do usuário.
	        System.out.print("> ");
	        String input = scanner.nextLine().trim();

	        // Comando para encerrar o programa.
	        if (input.equalsIgnoreCase("EXIT")) {
	            System.out.print("\nEncerrando o programa...");
	            break;
	        }

	        // Comando para iniciar a gravação de comandos.
	        if (input.equalsIgnoreCase("REC")) {
	            if (gravando) {
	                System.out.println("Já está em modo REC.");
	            } else {
	                gravando = true;
	                System.out.println("Iniciando gravação... (REC: 0/10)");
	            }
	            continue;
	        }

	        // Comando para parar a gravação de comandos.
	        if (input.equalsIgnoreCase("STOP")) {
	            if (!gravando) {
	                System.out.println("Erro: não há gravação em andamento.");
	            } else {
	                gravando = false;
	                System.out.println("Encerrando gravação...");
	            }
	            continue;
	        }

	        // Comando para reproduzir comandos gravados.
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
	                        String comando = comandos.dequeue(); 
	                        
	                        // Verifica se a string contém letras e símbolos não alfabéticos.
	                        if (comando.matches(".*[a-zA-Z].*") && comando.matches(".*[^a-zA-Z0-9=\\s].*")) {
	                        	System.out.print("["+comando+"] ");
	                        }
	                        executarComando(comando, comandos, conversor, avaliador, false, true); // Executa o comando gravado.
	                        comandos.enqueue(comando); // Reinsere o comando após execução.
	                    } catch (Exception e) {
	                        System.out.println("Erro ao reproduzir comando: " + e.getMessage());
	                    }
	                }
	            }
	            continue;
	        }

	        // Comando para apagar gravações armazenadas.
	        if (input.equalsIgnoreCase("ERASE")) {
	            comandos = new FilaCircular<>(10); // Limpa a fila de gravação.
	            System.out.println("Gravação apagada. ");
	            continue;
	        }
	        
	        executarComando(input, comandos, conversor, avaliador, gravando, true);
	    }
	    scanner.close(); 
	}
	
	// Método para executar comandos, gravações e expressões matemáticas.
    private static void executarComando(String comando, FilaCircular<String> comandos, ConversorInfPos conversor, AvaliadorPosfixo avaliador, boolean gravando, boolean exibirResultado) {
        
        // Se estiver no modo de gravação.
        if (gravando) {
            try {
                comandos.enqueue(comando); // Grava o comando na fila.
                System.out.println("(REC: " + comandos.totalElementos() + "/10) " + comando);
                if (comandos.totalElementos() >= 10) {
                    System.out.println("Limite de gravação atingido. Encerrando gravação.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Erro ao gravar comando: " + e.getMessage());
            }
        } else {
            // Tratamento de atribuição de variáveis.
            if (comando.contains("=")) {
                String[] partes = comando.split("=");
                String variavel = partes[0].trim();
                int valor = Integer.parseInt(partes[1].trim());
                avaliador.atribuir(variavel, valor);
                if (exibirResultado) {
                    System.out.println("Atribuído: " + variavel + " = " + valor);
                }
            } 
            // Comando para listar variáveis.
            else if (comando.equalsIgnoreCase("VARS")) {
                avaliador.listarVariaveis();
            }
            // Comando para reiniciar variáveis.
            else if (comando.equalsIgnoreCase("RESET")) {
                avaliador.resetVariaveis();
                System.out.println("Variáveis reiniciadas.");
            } 
            // Erro para comandos inválidos.
            else if (comando.length() > 1 && comando.matches("[a-zA-Z]+")) {
	            System.out.println("Erro: comando inválido '" + comando + "'.");
            } 
            // Avaliação de expressões matemáticas.
            else {
                try {
                	String expressaoPosfixa = conversor.Posfixo(comando);
                	if (avaliador.expressaoValida(expressaoPosfixa)) {
                		String resultado = avaliador.avaliar(expressaoPosfixa);
	                    if (exibirResultado) {
	                        System.out.println(resultado);
	                    }
                	} else {
                		System.out.println("Erro ao avaliar a expressão: " + comando);
                	}
                } catch (Exception e) {
                    System.out.println("Erro ao avaliar a expressão: " + comando);
                }
            }
        }
    }
}
