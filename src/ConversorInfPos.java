public class ConversorInfPos {
    public String Posfixo(String input_infixo) {
        Pilha p = new Pilha(input_infixo.length());
        StringBuilder output = new StringBuilder();
        
        

        for (int i = 0; i < input_infixo.length(); i++) {
            char simbolo = input_infixo.charAt(i);
            
            // Se o caractere é uma letra ou dígito, adiciona ao output
            if (Character.isDigit(simbolo)) {
            	System.out.print("Para realizar operações armazene seus números em variáveis (ex: A = 10)\n");
                throw new IllegalArgumentException("Não são permitidos números");
            }
            
            if (Character.isLetter(simbolo)) {
                output.append(simbolo);
            }
            
            // Se o caractere é um '(', empilha
            else if (simbolo == '(') {
                p.push(simbolo);
            }
            
            // Se o caractere é um ')', desempilha até encontrar '('
            else if (simbolo == ')') {
                while (!p.isEmpty() && (char) p.topo() != '(') {
                    output.append((char) p.pop()); 
                }
                if (!p.isEmpty()) {
                    p.pop(); // Remove o '(' da pilha
                } else {
                    // Se não encontrar o parêntese correspondente, lança um erro
                    throw new IllegalArgumentException("Parênteses não balanceados");
                }
            }
            
            // Se o caractere é um '-', determinar se é uma negação unária
            else if (simbolo == '-') {
                // Verifica se é unário (no início, após '(', ou após operador)
                if (i == 0 || input_infixo.charAt(i - 1) == '(' || isOperator(input_infixo.charAt(i - 1))) {
                    // Trata como unário, usa '~' para representar a negação
                    p.push('~'); // Empilha o operador unário
                } else {
                    // Caso contrário, trata como operador binário
                    while (!p.isEmpty() && ordem_de_operacao(simbolo) <= ordem_de_operacao((char) p.topo())) {
                        output.append((char) p.pop());
                    }
                    p.push(simbolo);
                }
            }
            
            // Se o caractere é um operador
            else if (isOperator(simbolo)) {
                while (!p.isEmpty() && ordem_de_operacao(simbolo) <= ordem_de_operacao((char) p.topo())) {
                    output.append((char) p.pop());
                }
                p.push(simbolo);
            }
        }
        while (!p.isEmpty()) {
            output.append((char) p.pop());
        }
        
        //System.out.print("\nPosfixa: " + output.toString() + "\n");

        if (output.toString().contains("(") || output.toString().contains(")")) {
            throw new IllegalArgumentException("Parênteses não balanceados");
        }

        return output.toString();
    }
    
    
    private boolean isOperator(char simbolo) {
        return simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^' || simbolo == '~';
    }
    
    // Método para determinar a prioridade de cada operador
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
        return -1; // Em caso de operador inválido...
    }
}
