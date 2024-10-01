// PROJETO - ESTRUTURA DE DADOS - TAD
// Bruno Germanetti Ramalho - RA 10426491
// Miguel Piñeiro Coratolo Simões - RA 10427085
// 01/10/2024 - 3ºSemestre - Ciências da Computação
// Universidade Presbiteriana Mackenzie - FCI


public class Pilha {
    // Atributos privados
    private static final int TAM_DEFAULT = 100;
    private int topoPilha;
    private Object[] e; // Usamos generics para armazenar tanto ParVariavelValor quanto outros objetos

    public Pilha(int tamanho) {
        this.e = new Object[tamanho];
        this.topoPilha = -1;
    }

    public Pilha() {
        this(TAM_DEFAULT);
    }

    // Verifica se a pilha está vazia
    public boolean isEmpty() {
        return this.topoPilha == -1;
    }

    // Verifica se a pilha está cheia
    public boolean isFull() {
        return this.topoPilha == this.e.length - 1;
    }

    // Insere um elemento no topo da pilha
    public void push(Object e) {
        if (!this.isFull())
            this.e[++this.topoPilha] = e;
        else
            System.out.println("overflow - Estouro de Pilha");
    }

    // Remove um elemento do topo da pilha
    public Object pop() {
        if (!this.isEmpty())
            return this.e[this.topoPilha--];
        else {
            System.out.println("underflow - Esvaziamento de Pilha");
            return null; // Retorna null em caso de erro
        }
    }

    // Método para encontrar o valor de uma variável
    public Integer popValue(String variavel) {
        Pilha temp = new Pilha(); // Usamos uma pilha temporária
        Integer valor = null;

        while (!isEmpty()) {
            ParVariavelValor par = (ParVariavelValor) pop();
            if (par.variavel.equals(variavel)) {
                valor = par.valor; // Encontrou o valor
                break;
            }
            // Se não for a variável procurada, armazena na pilha temporária
            temp.push(par);
        }

        // Restaura a pilha original
        while (!temp.isEmpty()) {
            push(temp.pop());
        }

        return valor; // Retorna o valor encontrado ou null se não encontrado
    }

    // Método para empilhar uma variável e seu valor
    public void push(String variavel, int valor) {
        ParVariavelValor par = new ParVariavelValor(variavel, valor);
        push(par);
    }

 // Método para encontrar o valor de uma variável sem removê-la
    public Integer getValue(String variavel) {
        Pilha temp = new Pilha(); // Criamos uma pilha temporária para restaurar os elementos
        Integer valor = null;

        // Percorremos a pilha original
        while (!isEmpty()) {
            ParVariavelValor par = (ParVariavelValor) pop(); // Remove do topo da pilha
            if (par.variavel.equals(variavel)) {
                valor = par.valor; // Encontramos o valor da variável
            }
            temp.push(par); // Armazena na pilha temporária
        }

        // Restaura a pilha original com os elementos da pilha temporária
        while (!temp.isEmpty()) {
            push(temp.pop());
        }

        return valor; // Retorna o valor encontrado ou null se não encontrado
    }

    
    // Sobrescrita do método toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Pilha] topoPilha: ")
          .append(topoPilha)
          .append(", capacidade: ")
          .append(e.length)
          .append(", quantidade de elementos: ")
          .append(sizeElements());

        if (topoPilha != -1) {
            sb.append(", valor do Topo: ")
              .append(topo());
        } else sb.append(", valor do Topo: PILHA VAZIA");

        sb.append("\nConteudo da Pilha: [ ");

        for (int i = 0; i <= topoPilha; ++i)
            if (i != topoPilha) sb.append(e[i]).append(", ");
            else sb.append(e[i]);
        sb.append(" ]");
        return sb.toString();
    }

    // Obtém o total de elementos armazenados na pilha
    public int sizeElements() {
        return topoPilha + 1;
    }

    // Retorna o elemento que está no topo da pilha
    public Object topo() {
        if (!this.isEmpty())
            return this.e[this.topoPilha];
        else {
            System.out.println("Underflow - Esvaziamento de Pilha");
            return null; // Retorna um valor nulo para char
        }
    }
}
