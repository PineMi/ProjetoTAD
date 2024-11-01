// PROJETO - ESTRUTURA DE DADOS - TAD 2
// Bruno Germanetti Ramalho - RA 10426491
// Miguel Piñeiro Coratolo Simões - RA 10427085
// 01/11/2024 - 3ºSemestre - Ciências da Computação
// Universidade Presbiteriana Mackenzie - FCI
import java.util.Scanner;

public class Main {
    private static boolean unsavedChanges = false;
	private static String currentFile = null;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// Ideias: 
		// 1. Implementar a função "Help" que mostra os possíveis comandos
		
		// REPL Loop
		while (true) {
			System.out.print("> ");
			String comando = scanner.nextLine().trim();
			String[] partes = comando.split(" ", 2); 
			String instrucaoComando = partes[0].toUpperCase(); 
			String argumentos = (partes.length > 1) ? partes[1] : "";
			
			switch (instrucaoComando) {
				case "LOAD": loadFile(argumentos);  break;
				case "LIST": listCode();            break;
                case "RUN":	runCode();              break;
                case "INS": insertLine(argumentos); break;
                case "DEL": deleteLine(argumentos); break;
                case "SAVE": saveFile(argumentos);  break;
                case "HELP": printHelp();           break;
                case "EXIT": exit(); scanner.close(); return;
                default:
                    System.out.println("Comando não reconhecido.\nEscreva \"HELP\" para visualizar a lista de comandos");
                    break;
			}	
		}	
	}
	
	private static void loadFile(String arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Comando inválido. Use: LOAD <ARQUIVO.ED1>");
            return;
        }
        String fileName = arguments;
        System.out.println("Comando LOAD chamado para o arquivo: " + fileName);
        // Implementação será feita depois
    }

    private static void listCode() {
        System.out.println("Comando LIST chamado");
        // Implementação será feita depois
    }

    private static void runCode() {
        System.out.println("Comando RUN chamado");
        // Implementação será feita depois
    }

    private static void insertLine(String arguments) {
        String[] insParts = arguments.split(" ", 2);
        if (insParts.length < 2) {
            System.out.println("Comando inválido. Use: INS <LINHA> <INSTRUÇÃO>");
            return;
        }
        try {
            int lineNumber = Integer.parseInt(insParts[0]);
            String instruction = insParts[1];
            System.out.println("Comando INS chamado para a linha " + lineNumber + " com a instrução: " + instruction);
            // Implementação será feita depois
        } catch (NumberFormatException e) {
            System.out.println("Número de linha inválido.");
        }
    }

    private static void deleteLine(String arguments) {
        String[] delParts = arguments.split(" ");
        try {
            if (delParts.length == 1) {
                int lineNumber = Integer.parseInt(delParts[0]);
                System.out.println("Comando DEL chamado para a linha " + lineNumber);
                // Implementação será feita depois
            } else if (delParts.length == 2) {
                int startLine = Integer.parseInt(delParts[0]);
                int endLine = Integer.parseInt(delParts[1]);
                System.out.println("Comando DEL chamado para o intervalo de linhas " + startLine + " a " + endLine);
                // Implementação será feita depois
            } else {
                System.out.println("Comando inválido. Use: DEL <LINHA> ou DEL <LINHA_I> <LINHA_F>");
            }
        } catch (NumberFormatException e) {
            System.out.println("Número de linha inválido.");
        }
    }

    private static void saveFile(String arguments) {
        if (arguments.isEmpty() && currentFile != null) {
            System.out.println("Comando SAVE chamado para o arquivo atual: " + currentFile);
            // Implementação será feita depois
        } else if (!arguments.isEmpty()) {
            String fileName = arguments;
            System.out.println("Comando SAVE chamado para salvar como: " + fileName);
            // Implementação será feita depois
        } else {
            System.out.println("Nenhum arquivo carregado. Use LOAD <ARQUIVO.ED1> para especificar um arquivo.");
        }
    }

    private static void printHelp() {
        System.out.println("Comando HELP chamado");
        // Implementação será feita depois
    }
    
    private static void exit() {
        System.out.println("Comando EXIT chamado");
        // Implementação será feita depois
    }
}

