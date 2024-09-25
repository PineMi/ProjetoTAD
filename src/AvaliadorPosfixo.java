
public class AvaliadorPosfixo {
	public int avaliar(String expressaoPosfixa) {
        Pilha p = new Pilha(expressaoPosfixa.length());
        
        // Percorre cada caractere da expressão pós-fixa
        for (int i = 0; i < expressaoPosfixa.length(); i++) {
            char simbolo = expressaoPosfixa.charAt(i);
            
            // Se é um dígito, empilha (convertemos o char em int)
            if (Character.isDigit(simbolo)) {
                p.push(Character.getNumericValue(simbolo)); // Converte char para int
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
                        resultado = operando1 / operando2; // Cuidado com divisão por zero
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
