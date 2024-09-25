public class ConversorInfPos {
    public String Posfixo(String input_infixo) {
        Pilha p = new Pilha(input_infixo.length());
        StringBuilder output = new StringBuilder();
        
        for (int i = 0; i < input_infixo.length(); i++) {
            char simbolo = input_infixo.charAt(i);
            
            // Se o caractere é uma letra ou dígito, adiciona ao output
            if (Character.isLetterOrDigit(simbolo)) {
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
                    p.pop(); 
                }
            }
            
            // Se o caractere é um operador
            else if (simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^') {
                while (!p.isEmpty() && ordem_de_operacao(simbolo) <= ordem_de_operacao((char) p.topo())) {
                    output.append((char) p.pop());
                }
                p.push(simbolo);
            }
        }
        while (!p.isEmpty()) {
            output.append((char) p.pop());
        }
        
        return output.toString();
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
        }
        return -1; // Em caso de operador inválido...
    }
}
