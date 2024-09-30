public class AvaliadorPosfixo {
    private FilaCircular<ParVariavelValor> filaVariaveis;

    public AvaliadorPosfixo() {
        this.filaVariaveis = new FilaCircular<>(); // Inicializa a fila
    }
    
    
    public void listarVariaveis() {
        System.out.println("Variáveis definidas:");

        // Percorre todos os elementos da fila
        for (int i = 0; i < filaVariaveis.totalElementos(); i++) {
            try {
                ParVariavelValor par = filaVariaveis.dequeue(); // Remove da fila
                System.out.println(par.variavel + " = " + par.valor); // Exibe a variável e seu valor
                filaVariaveis.enqueue(par); // Reinsere o par no final da fila
            } catch (Exception e) {
                System.out.println("Erro ao listar variáveis: " + e.getMessage());
                break; // Para o loop em caso de erro
            }
        }
    }



    // Método para reiniciar todas as variáveis
    public void resetVariaveis() {
    	filaVariaveis = new FilaCircular<>(); // Reinicia a pilha de variáveis
    }

    public void atribuir(String variavel, int valor) {
        int tamanhoFila = filaVariaveis.totalElementos(); // Captura o total de elementos na fila
        boolean encontrado = false; // Flag para verificar se a variável foi encontrada

        // Itera sobre os elementos da fila
        for (int i = 0; i < tamanhoFila; i++) {
            try {
                ParVariavelValor par = filaVariaveis.dequeue(); // Remove da fila

                if (par.variavel.equalsIgnoreCase(variavel)) {
                    // Se a variável já existe, atualizamos o valor
                    par.valor = valor; // Atualiza o valor da variável
                    encontrado = true; // Atualiza a flag
                }
                filaVariaveis.enqueue(par);

            } catch (Exception e) {
                System.out.println("Erro ao atribuir variável: " + e.getMessage());
            }
        }
        // Se a variável não foi encontrada, adicionamos a nova variável
        if (!encontrado) {
        	filaVariaveis.enqueue(new ParVariavelValor(variavel, valor)); // Cria e adiciona a nova variável
        } 
    }


    public String avaliar(String expressaoPosfixa) {
        Pilha p = new Pilha(expressaoPosfixa.length());
        
        // Percorre cada caractere da expressão pós-fixa
        for (int i = 0; i < expressaoPosfixa.length(); i++) {
            char simbolo = expressaoPosfixa.charAt(i);

            // Se é um dígito, empilha
            if (Character.isDigit(simbolo)) {
                p.push(Character.getNumericValue(simbolo)); // Empilha o número
            } else if (Character.isLetter(simbolo)) {
                // Se é uma letra, busca seu valor na Fila de variáveis
            	
                Integer valor = null;

                for (int k = 0; k < filaVariaveis.totalElementos(); k++) {
                    try {
                        ParVariavelValor par = filaVariaveis.dequeue(); // Remove o elemento do início da fila
                        if (par.variavel.equalsIgnoreCase(String.valueOf(simbolo))) {
                            valor = par.valor; // Retorna o valor correspondente
                        }
                        filaVariaveis.enqueue(par); // Reinsere o elemento na fila
                    } catch (Exception e) {
                        return null; // Retorna null se houver erro na fila
                    }
                }
           
                if (valor != null) {
                    p.push(valor);
                } else {
                    return "Valor da variável " + simbolo + " não encontrado."; // Retorna mensagem de erro
                }
            } else if (simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^') {
                // Verifica se há pelo menos dois operandos na pilha
                if (p.sizeElements() < 2) {
                    return "Erro: operação inválida. Não há operandos suficientes."; // Retorna mensagem de erro
                }

                int operando2 = (int) p.pop(); // Operando do topo da pilha
                int operando1 = (int) p.pop(); // Segundo operando

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
                            resultado = operando1 / operando2; // Cuidado com divisão por zero
                        } else {
                            return "Erro: Divisão por zero."; // Retorna mensagem de erro
                        }
                        break;
                    case '^':
                        resultado = (int) Math.pow(operando1, operando2);
                        break;
                    default:
                        return "Erro: operação " + simbolo + " não suportada."; // Retorna mensagem de erro
                }
                p.push(resultado); // Empilha o resultado
            } else {
                // Se o símbolo não for um operador válido
                return "Erro: operação " + simbolo + " não suportada."; // Retorna mensagem de erro
            }
        }

        // O resultado final será o único item restante na pilha
        if (p.sizeElements() == 1) {
            return String.valueOf((int) p.pop()); // Retorna o resultado final como String
        } else {
            return "Erro: expressão inválida. Verifique os operandos."; // Retorna mensagem de erro
        }
    }
}
