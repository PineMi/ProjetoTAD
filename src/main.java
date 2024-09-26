import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Instância dos objetos principais
        Scanner scanner = new Scanner(System.in);
        ConversorInfPos conversor = new ConversorInfPos();
        AvaliadorPosfixo avaliador = new AvaliadorPosfixo();
        Pilha comandos = new Pilha(10); // Pilha para gravar os comandos
        boolean gravando = false;
        int gravados = 0;

        // Loop de execução principal
        while (true) {
            // Input de comandos e expressões
            System.out.print("\n> ");
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
                    gravados = 0;
                    System.out.println("Iniciando gravação... (REC: " + gravados + "/10)");
                }
                continue; // Volta ao início do loop
            }

            // Comando "STOP" para parar gravação
            if (input.equalsIgnoreCase("STOP")) {
                if (!gravando) {
                    System.out.println("Erro: não há gravação em andamento.");
                } else {
                    gravando = false;
                    System.out.println("Encerrando gravação... (REC: " + gravados + "/10)");
                }
                continue; // Volta ao início do loop
            }

            // Comando "PLAY" para reproduzir a gravação
            if (input.equalsIgnoreCase("PLAY")) {
                if (gravando) {
                    System.out.println("Erro: comando inválido para gravação.");
                } else if (comandos.isEmpty()) {
                    System.out.println("Não há gravação para ser reproduzida.");
                } else {
                    System.out.println("Reproduzindo gravação...");
                    Pilha temp = new Pilha(); // Pilha temporária para execução na ordem correta
                    while (!comandos.isEmpty()) {
                        String comando = (String) comandos.pop();
                        temp.push(comando); // Reverte a ordem dos comandos
                    }
                    while (!temp.isEmpty()) {
                        String comando = (String) temp.pop();
                        executarComando(comando, conversor, avaliador, true); // Executa o comando gravado
                    }
                }
                continue; // Volta ao início do loop
            }

            // Comando "ERASE" para apagar a gravação
            if (input.equalsIgnoreCase("ERASE")) {
                comandos = new Pilha(10); // Limpa a pilha de comandos
                System.out.println("Gravação apagada.");
                continue; // Volta ao início do loop
            }

            // Comando "VARS"
            if (input.equalsIgnoreCase("VARS")) {
                if (gravando) {
                    comandos.push(input);
                    gravados++;
                    System.out.println("(REC: " + gravados + "/10) " + input);
                    if (gravados >= 10) {
                        gravando = false;
                        System.out.println("Limite de gravação atingido. Encerrando gravação.");
                    }
                } else {
                    avaliador.listarVariaveis();
                }
                continue; // Volta ao início do loop
            }

            // Comando "RESET"
            if (input.equalsIgnoreCase("RESET")) {
                if (gravando) {
                    comandos.push(input);
                    gravados++;
                    System.out.println("(REC: " + gravados + "/10) " + input);
                    if (gravados >= 10) {
                        gravando = false;
                        System.out.println("Limite de gravação atingido. Encerrando gravação.");
                    }
                } else {
                    avaliador.resetVariaveis();
                    System.out.println("Variáveis reiniciadas.");
                }
                continue; // Volta ao início do loop
            }

            // Atribuição de variáveis (ex: A = 10)
            if (input.contains("=")) {
                if (gravando) {
                    comandos.push(input);
                    gravados++;
                    System.out.println("(REC: " + gravados + "/10) " + input);
                    if (gravados >= 10) {
                        gravando = false;
                        System.out.println("Limite de gravação atingido. Encerrando gravação.");
                    }
                } else {
                    String[] partes = input.split("=");
                    String variavel = partes[0].trim();
                    int valor = Integer.parseInt(partes[1].trim());
                    avaliador.atribuir(variavel, valor);
                    System.out.println("Atribuído: " + variavel + " = " + valor);
                }
                continue; // Volta ao início do loop
            }

            // Avaliação de expressões
            if (gravando) {
                comandos.push(input);
                gravados++;
                System.out.println("(REC: " + gravados + "/10) " + input);
                if (gravados >= 10) {
                    gravando = false;
                    System.out.println("Limite de gravação atingido. Encerrando gravação.");
                }
            } else {
                executarComando(input, conversor, avaliador, true); // Executa a expressão
            }
        }

        scanner.close(); // Fecha o scanner para evitar vazamento de recursos
    }

    // Função auxiliar para executar comandos, sem gravar durante PLAY
    private static void executarComando(String comando, ConversorInfPos conversor, AvaliadorPosfixo avaliador, boolean exibirResultado) {
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
        } else {
            try {
                int resultado = avaliador.avaliar(conversor.Posfixo(comando));
                if (exibirResultado && resultado != -1) {
                    System.out.println("Resultado: " + resultado);
                }
            } catch (Exception e) {
                System.out.println("Erro ao avaliar a expressão: " + comando);
            }
        }
    }
}
