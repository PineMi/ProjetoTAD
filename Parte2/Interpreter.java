// PROJETO - ESTRUTURA DE DADOS - TAD 2
// Bruno Germanetti Ramalho - RA 10426491
// Miguel Piñeiro Coratolo Simões - RA 10427085
// 01/11/2024 - 3ºSemestre - Ciências da Computação
// Universidade Presbiteriana Mackenzie - FCI

public class Interpreter {
    private ListaEncadeada<String> listaAssembly;
    private FilaCircular<ParRegValor> filaRegistradores;

    public Interpreter(ListaEncadeada<String> listaAssembly) {
        this.listaAssembly = listaAssembly;
        this.filaRegistradores = new FilaCircular<>(26);
        for (char reg = 'A'; reg <= 'Z'; reg++) {filaRegistradores.enqueue(new ParRegValor(reg, 0));}
    }

    public void run() {
        Node<String> nodeInstrucao = listaAssembly.getHead();

        while (nodeInstrucao != null) {
        	String instrucaoCompleta = nodeInstrucao.getDado().toUpperCase().trim();
            String[] partes = instrucaoCompleta.split(" ", 2);
            String numeroLinha = partes[0];
            String instrucao = partes[1];
            
            // Divide a instrução para obter o comando e os argumentos
            String[] comandoArgs = instrucao.split(" ");
            String comando = comandoArgs[0];
            
            // Verifica se há argumentos e armazena
            String arg1 = (comandoArgs.length > 1) ? comandoArgs[1] : null;
            String arg2 = (comandoArgs.length > 2) ? comandoArgs[2] : null;
            
            try {
                switch (comando) {
                    case "MOV": mov(arg1, arg2);                 break;
                    case "INC": inc(arg1);                       break;
                    case "DEC": dec(arg1);                       break;
                    case "ADD": add(arg1, arg2);                 break;
                    case "SUB": sub(arg1, arg2);                 break;
                    case "MUL": mul(arg1, arg2);                 break;
                    case "DIV": div(arg1, arg2);                 break;
                    case "OUT": out(arg1);                       break;
                    case "JNZ": nodeInstrucao = jnz(nodeInstrucao, arg1, arg2); break;

                    default:
                        System.out.println("Instrução desconhecida: " + comando);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Erro na Linha "+ numeroLinha + ": " + instrucao + " -> " + e.getMessage());
                break; 
            }
            
            nodeInstrucao = nodeInstrucao.getProx(); // Avança para a próxima linha
            //System.out.println(filaRegistradores);
        }
    }
    
    
    // Atualiza o valor de um registrador na fila
    public void atualizarRegistrador(char variavel, int novoValor) {
        try {
            for (int i = 0; i < filaRegistradores.totalElementos(); i++) {
                ParRegValor par = filaRegistradores.dequeue();
                if (par.getVariavel() == variavel) {
                    par.setValor(novoValor); 
                    filaRegistradores.enqueue(par); 
                    return; 
                }
                filaRegistradores.enqueue(par); 
            }
            System.out.println("Registrador " + variavel + " não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o registrador: " + e.getMessage());
        }
    }

    
    public int obterValorRegistrador(char variavel) {
        try {
            for (int i = 0; i < filaRegistradores.totalElementos(); i++) {
                ParRegValor par = filaRegistradores.dequeue(); // Remove da fila para verificar
                if (par.getVariavel() == variavel) {
                    filaRegistradores.enqueue(par); 
                    return par.getValor(); 
                }
                filaRegistradores.enqueue(par);
            }
            System.out.println("Registrador " + variavel + " não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao obter o valor do registrador: " + e.getMessage());
        }
        return 0;
    }
    
    
    public void mov(String arg1, String arg2) throws IllegalArgumentException {
        char x = arg1.charAt(0);
        char y = arg2.charAt(0);
        
    	if (Character.isDigit(x)) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        }
    	if (Character.isDigit(y)) {
    		atualizarRegistrador(x, Character.getNumericValue(y));
    	} else {
    		atualizarRegistrador(x, obterValorRegistrador(y));
    	}
    }

    public void inc(String arg1) {
        Character x = arg1.charAt(0);
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {
        	atualizarRegistrador(x, obterValorRegistrador(x)+1);
        }
    }

    public void dec(String arg1) {
        Character x = arg1.charAt(0);
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {
        	atualizarRegistrador(x, obterValorRegistrador(x)-1);
        }
    }

    public void add(String arg1, String arg2) {
        Character x = arg1.charAt(0);
        Character y = arg2.charAt(0);
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {
        	if (Character.isDigit(y)) {
        		atualizarRegistrador(x, obterValorRegistrador(x) + Character.getNumericValue(y));
        	} else {
        		atualizarRegistrador(x, obterValorRegistrador(x) + obterValorRegistrador(y));
        	}	
        }
    }

    public void sub(String arg1, String arg2) {
        Character x = arg1.charAt(0);
        Character y = arg2.charAt(0);
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {
        	if (Character.isDigit(y)) {
        		atualizarRegistrador(x, obterValorRegistrador(x) - Character.getNumericValue(y));
        	} else {
        		atualizarRegistrador(x, obterValorRegistrador(x) - obterValorRegistrador(y));
        	}	
        }
    }

    public void mul(String arg1, String arg2) {
        Character x = arg1.charAt(0);
        Character y = arg2.charAt(0);
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {
        	if (Character.isDigit(y)) {
        		atualizarRegistrador(x, obterValorRegistrador(x) * Character.getNumericValue(y));
        	} else {
        		atualizarRegistrador(x, obterValorRegistrador(x) * obterValorRegistrador(y));
        	}	
        }
    }

    public void div(String arg1, String arg2) {
        Character x = arg1.charAt(0);
        Character y = arg2.charAt(0);
        if (Character.getNumericValue(y) == 0) {
    		throw new IllegalArgumentException("Divisão por Zero");
    	}
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {	
        	if (Character.isDigit(y)) {
        		atualizarRegistrador(x, obterValorRegistrador(x) / Character.getNumericValue(y));
        	} else {
        		atualizarRegistrador(x, obterValorRegistrador(x) / obterValorRegistrador(y));
        	}	
        }
    }

    public Node<String> jnz(Node<String> nodeInstrucao, String arg1, String arg2) {
        // Obtém o valor de arg1
        Integer valor;
        if (Character.isDigit(arg1.charAt(0))) {
            valor = Integer.parseInt(arg1);
        } else {
            valor = obterValorRegistrador(arg1.charAt(0));
        }

        // Se o valor for diferente de zero, realizamos o salto
        if (valor != 0) {
            Node<String> nodeAtual = listaAssembly.getHead();
            Node<String> nodeAnterior = null; // Variável para armazenar o nó anterior

            // Navega pela lista encadeada
            while (nodeAtual != null) {
                String linha = nodeAtual.getDado().trim();
                // Verifica se a linha atual é igual a arg2
                if (linha.startsWith(arg2 + " ")) {
                    // Retorna o nó anterior ou o nó atual se for o primeiro
                    return nodeAnterior != null ? nodeAnterior : nodeAtual; 
                }
                // Atualiza o nó anterior antes de avançar
                nodeAnterior = nodeAtual;
                nodeAtual = nodeAtual.getProx();
            }
            throw new IllegalArgumentException("Rótulo não encontrado: " + arg2); // Mensagem de erro se não encontrar o rótulo
        }

        return nodeInstrucao; // Retorna o nó atual se o salto não ocorrer
    }


    

    public void out(String arg1) {
    	Character x = arg1.charAt(0);
        if (Character.isDigit(x) || arg1.length() > 1) {
            throw new IllegalArgumentException("O Primeiro argumento deve ser um registrador");
        } else {
        	System.out.println(obterValorRegistrador(x));
        }
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}