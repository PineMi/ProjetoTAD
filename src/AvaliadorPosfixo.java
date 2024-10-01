// PROJETO - ESTRUTURA DE DADOS - TAD
// Bruno Germanetti Ramalho - RA 10426491
// Miguel Piñeiro Coratolo Simões - RA 10427085
// 01/10/2024 - 3ºSemestre - Ciências da Computação
// Universidade Presbiteriana Mackenzie - FCI

public class AvaliadorPosfixo {
    private FilaCircular<ParVariavelValor> filaVariaveis;

    public AvaliadorPosfixo() {
        this.filaVariaveis = new FilaCircular<>(); // Inicializa a fila de variáveis
    }
    
    // Exibe todas as variáveis e seus valores
    public void listarVariaveis() {
        System.out.println("Variáveis definidas:");
        for (int i = 0; i < filaVariaveis.totalElementos(); i++) {
            try {
                ParVariavelValor par = filaVariaveis.dequeue(); 
                System.out.println(par.variavel + " = " + par.valor); 
                filaVariaveis.enqueue(par);
            } catch (Exception e) {
                System.out.println("Erro ao listar variáveis: " + e.getMessage());
                break; 
            }
        }
    }

    // Reinicia a fila de variáveis
    public void resetVariaveis() {
        filaVariaveis = new FilaCircular<>(); 
    }

    // Atribui um valor a uma variável
    public void atribuir(String variavel, int valor) {
        int tamanhoFila = filaVariaveis.totalElementos();
        boolean encontrado = false; 

        // Percorre a fila para atualizar o valor da variável
        for (int i = 0; i < tamanhoFila; i++) {
            try {
                ParVariavelValor par = filaVariaveis.dequeue();
                if (par.variavel.equalsIgnoreCase(variavel)) {
                    par.valor = valor; 
                    encontrado = true;
                }
                filaVariaveis.enqueue(par);
            } catch (Exception e) {
                System.out.println("Erro ao atribuir variável: " + e.getMessage());
            }
        }

        // Se a variável não foi encontrada, adiciona uma nova
        if (!encontrado) {
            filaVariaveis.enqueue(new ParVariavelValor(variavel, valor)); // Adiciona nova variável
        }
    }
 	// Método para verificar se a expressão pós-fixa é válida
    public boolean expressaoValida(String expressaoPosfixa) {
        int operandos = expressaoPosfixa.replaceAll("[^\\dA-Za-z]", "").length(); // Conta operandos
        int operadores = expressaoPosfixa.replaceAll("[^+\\-*/^=]", "").length(); // Conta operadores

        return (operandos - operadores <= 1);
    }

    // Avalia uma expressão pós-fixa
    public String avaliar(String expressaoPosfixa) {
        Pilha p = new Pilha(expressaoPosfixa.length());
        StringBuilder erros = new StringBuilder(); 
        
        
        // Processa cada caractere da expressão pós-fixa
        for (int i = 0; i < expressaoPosfixa.length(); i++) {
            char simbolo = expressaoPosfixa.charAt(i);

            // Empilha dígitos
            if (Character.isDigit(simbolo)) {
                p.push(Character.getNumericValue(simbolo));
            } 
            // Substitui variáveis pelos seus valores
            else if (Character.isLetter(simbolo)) {
                Integer valor = null;

                // Busca o valor da variável na fila
                for (int k = 0; k < filaVariaveis.totalElementos(); k++) {
                    try {
                        ParVariavelValor par = filaVariaveis.dequeue();
                        if (par.variavel.equalsIgnoreCase(String.valueOf(simbolo))) {
                            valor = par.valor;
                        }
                        filaVariaveis.enqueue(par); 
                    } catch (Exception e) {
                        return null; 
                    }
                }

                if (valor != null) {
                    p.push(valor); 
                } else {
                    erros.append("Valor da variável ").append(simbolo).append(" não encontrado.\n");
                    p.push(1); 
                }
            } 
            
            // Opera com os valores da pilha
            else if (isOperator(simbolo)) {
                if (simbolo == '~') { // Negação unária
                    if (p.sizeElements() < 1) {
                        return "Erro: operação inválida. Não há operandos suficientes.";
                    }
                    int operando = (int) p.pop();
                    p.push(-operando); 
                } else {
                    if (p.sizeElements() < 2) {
                        return "Erro: operação inválida. Não há operandos suficientes.";
                    }

                    int operando2 = (int) p.pop();
                    int operando1 = (int) p.pop();

                    int resultado;
                    switch (simbolo) {
                        case '+':
                            resultado = operando1 + operando2;
                            break;
                        case '-':
                            resultado = operando1 - operando2;
                            break;
                        case '*':
                            resultado = operando1 * operando2;
                            break;
                        case '/':
                            if (operando2 != 0) {
                                resultado = operando1 / operando2; 
                            } else {
                                return "Erro: Divisão por zero.";
                            }
                            break;
                        case '^':
                            resultado = (int) Math.pow(operando1, operando2); 
                            break;
                        default:
                            return "Erro: operação " + simbolo + " não suportada.";
                    }
                    p.push(resultado); 
                }
            } else {
                return "Erro: operação " + simbolo + " não suportada.";
            }
        }

        // Exibe mensagens de erro, se houver
        if (erros.length() > 0) {
            return erros.toString();
        }

        // Retorna o resultado final da pilha
        if (p.sizeElements() == 1) {
            return String.valueOf((int) p.pop());
        } else {
            return "Erro: expressão inválida. Verifique os operandos.";
        }
    }

    // Verifica se o caractere é um operador
    private boolean isOperator(char simbolo) {
        return simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^' || simbolo == '~';
    }
}
