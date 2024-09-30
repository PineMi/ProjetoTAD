public class AvaliadorPosfixo {
    private Pilha pilhaVariaveis;

    
    /////////////////////////////////////////////////////////////////////////////////PILHA INVERTIDA, TROCAR POR FILA
    public AvaliadorPosfixo() {
        this.pilhaVariaveis = new Pilha();
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////PILHA INVERTIDA, TROCAR POR FILA
    public void listarVariaveis() {
        Pilha temp = new Pilha(); // Pilha temporária para não modificar a original
        System.out.println("Variáveis definidas:");
        
        while (!pilhaVariaveis.isEmpty()) {
            ParVariavelValor par = (ParVariavelValor) pilhaVariaveis.pop();
            System.out.println(par.variavel + " = " + par.valor);
            temp.push(par); // Armazena na pilha temporária
        }

        // Restaura a pilha original
        while (!temp.isEmpty()) {
            pilhaVariaveis.push(temp.pop());
        }
    }

    
    /////////////////////////////////////////////////////////////////////////////////PILHA INVERTIDA, TROCAR POR FILA
    // Método para reiniciar todas as variáveis
    public void resetVariaveis() {
        pilhaVariaveis = new Pilha(); // Reinicia a pilha de variáveis
        System.out.println("Todas as variáveis foram reiniciadas.");
    }

    public void atribuir(String variavel, int valor) {
        // Verificar se a variável já existe e atualizar o valor
        Integer valorExistente = pilhaVariaveis.getValue(variavel);
        if (valorExistente != null) {
            // Se a variável já existe, desempilhe-a
            pilhaVariaveis.popValue(variavel);
        }
        // Adiciona ou atualiza a variável
        pilhaVariaveis.push(variavel, valor);
    }

    public int avaliar(String expressaoPosfixa) {
        Pilha p = new Pilha(expressaoPosfixa.length());

        // Percorre cada caractere da expressão pós-fixa
        for (int i = 0; i < expressaoPosfixa.length(); i++) {
            char simbolo = expressaoPosfixa.charAt(i);

            // Se é um dígito, empilha

            //////////////////////////////////////////////////////////////////////////////////////
            // PROVAVELMENTE É AQUI QUE VOCÊ ARRUMA O USO DE NÚMEROS
            //////////////////////////////////////////////////////////////////////////////////////

            if (Character.isDigit(simbolo)) {
                //p.push(Character.getNumericValue(simbolo)); // Converte char para int
            	System.out.print("Erro não é permitido o uso de números: " + simbolo + "\n");
            } else 
            if (Character.isLetter(simbolo)) {
                // Se é uma letra, busca seu valor na pilha de variáveis
                Integer valor = pilhaVariaveis.getValue(String.valueOf(simbolo));
                if (valor != null) {
                    p.push(valor);
                } else {
                    System.out.println("Valor da variável " + simbolo + " não encontrado.");
                    return -1; // Retorna um valor indicando erro
                }
            } 

            
            // Se é um operador, desempilha dois operandos, calcula e empilha o resultado
            else if (simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^') {
                // Verifica se há pelo menos dois operandos na pilha
                if (p.sizeElements() < 2) {
                    System.out.println("Erro: operação inválida. Não há operandos suficientes.");
                    return -1; // Indica erro
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
                            System.out.println("Erro: Divisão por zero.");
                            return -1; // Indica erro
                        }
                        break;
                    case '^':
                        resultado = (int) Math.pow(operando1, operando2);
                        break;
                    default:
                        // Para qualquer operador não suportado
                        System.out.println("Erro: operação " + simbolo + " não suportada.");
                        return -1; // Indica erro
                }
                p.push(resultado); // Empilha o resultado
            } else {
                // Se o símbolo não for um operador válido
                System.out.println("Erro: operação " + simbolo + " não suportada.");
                return -1; // Indica erro
            }
        }

        // O resultado final será o único item restante na pilha
        if (p.sizeElements() == 1) {
            return (int) p.pop(); // Retorna o resultado final
        } else {
            System.out.println("Erro: expressão inválida. Verifique os operandos.");
            return -1; // Indica erro
        }
    }
}
