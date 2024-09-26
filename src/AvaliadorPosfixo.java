public class AvaliadorPosfixo {
    private Pilha pilhaVariaveis;

    public AvaliadorPosfixo() {
        this.pilhaVariaveis = new Pilha();
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
    	//TODO REMOVER DEBUG

        Pilha p = new Pilha(expressaoPosfixa.length());

        // Percorre cada caractere da expressão pós-fixa
        for (int i = 0; i < expressaoPosfixa.length(); i++) {
            char simbolo = expressaoPosfixa.charAt(i);

            // Se é um dígito, empilha
            if (Character.isDigit(simbolo)) {
                p.push(Character.getNumericValue(simbolo)); // Converte char para int
            } else if (Character.isLetter(simbolo)) {
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
                int operando2 = (int) p.pop(); // Operando do topo da pilha
                int operando1 = (int) p.pop(); // Segundo operando

                int resultado = 0;
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
                }
                p.push(resultado); // Empilha o resultado
            }
        }

        // O resultado final será o único item restante na pilha
        return (int) p.pop(); // Retorna o resultado final
    }
}
