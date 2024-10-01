// PROJETO - ESTRUTURA DE DADOS - TAD
// Bruno Germanetti Ramalho - RA 10426491
// Miguel Piñeiro Coratolo Simões - RA 10427085
// 01/10/2024 - 3ºSemestre - Ciências da Computação
// Universidade Presbiteriana Mackenzie - FCI

public class ConversorInfPos {
    
    // Método para converter uma expressão infixa em pós-fixa
    public String Posfixo(String input_infixo) {
        Pilha p = new Pilha(input_infixo.length()); // Pilha para armazenar operadores
        StringBuilder output = new StringBuilder(); // String para armazenar a expressão pós-fixa

        // Itera sobre cada caractere da expressão
        for (int i = 0; i < input_infixo.length(); i++) {
            char simbolo = input_infixo.charAt(i);
            
            if (Character.isDigit(simbolo)) {
            	System.out.print("Para realizar operações armazene seus números em variáveis (ex: A = 10)\n");
                throw new IllegalArgumentException("Não são permitidos números");
            }
            
            if (Character.isLetter(simbolo)) {
                output.append(simbolo);
            }
            
            else if (simbolo == '(') {
                p.push(simbolo);
            }
            
            // Desempilha até encontrar '(' ao fechar parênteses ')'
            else if (simbolo == ')') {
                while (!p.isEmpty() && (char) p.topo() != '(') {
                    output.append((char) p.pop());
                }
                if (!p.isEmpty()) {
                    p.pop(); // Remove '(' da pilha
                } else {
                    throw new IllegalArgumentException("Parênteses não balanceados");
                }
            }
            
            else if (simbolo == '-') {
                if (i == 0 || input_infixo.charAt(i - 1) == '(' || isOperator(input_infixo.charAt(i - 1))) {
                    p.push('~'); // Negação unária com '~'
                } else {
                    // Trata como operador binário
                    while (!p.isEmpty() && ordem_de_operacao(simbolo) <= ordem_de_operacao((char) p.topo())) {
                        output.append((char) p.pop());
                    }
                    p.push(simbolo);
                }
            }
            
            // Processa operadores
            else if (isOperator(simbolo)) {
                while (!p.isEmpty() && ordem_de_operacao(simbolo) <= ordem_de_operacao((char) p.topo())) {
                    output.append((char) p.pop());
                }
                p.push(simbolo);
            }
        }

        // Desempilha quaisquer operadores restantes
        while (!p.isEmpty()) {
            output.append((char) p.pop());
        }

        // Verifica se os parênteses estão balanceados
        if (output.toString().contains("(") || output.toString().contains(")")) {
            throw new IllegalArgumentException("Parênteses não balanceados");
        }

        return output.toString(); 
    }
    
    // Verifica se o símbolo é um operador
    private boolean isOperator(char simbolo) {
        return simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^' || simbolo == '~';
    }
    
    // Define a prioridade de operadores
    private int ordem_de_operacao(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1; 
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            case '~':
                return 4; 
        }
        return -1; 
    }
}
