/**
 * Implementação customizada de lista ligada para processos
 * Não utiliza estruturas de dados prontas do Java
 */
public class ListaDeProcessos {
    private No inicio;
    private No fim;
    private int tamanho;

    public ListaDeProcessos() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    // Adiciona processo no final da lista
    public void adicionarNoFinal(Processo processo) {
        No novoNo = new No(processo);
        
        if (inicio == null) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.setProximo(novoNo);
            fim = novoNo;
        }
        tamanho++;
    }

    // Remove e retorna o primeiro processo da lista
    public Processo removerDoInicio() {
        if (inicio == null) {
            return null;
        }

        Processo processo = inicio.getProcesso();
        inicio = inicio.getProximo();
        
        if (inicio == null) {
            fim = null;
        }
        
        tamanho--;
        return processo;
    }
    
// Verifica se a lista está vazia
    public boolean estaVazia() {
        return inicio == null;
    }

    // Retorna o tamanho da lista
    public int getTamanho() {
        return tamanho;
    }

    // Retorna uma representação em string da lista
    public String toString() {
        if (estaVazia()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        No atual = inicio;
        while (atual != null) {
            sb.append(atual.getProcesso().toString());
            if (atual.getProximo() != null) {
                sb.append(", ");
            }
            atual = atual.getProximo();
        }
        
        sb.append("]");
        return sb.toString();
    }

    // Retorna o primeiro processo sem removê-lo
    public Processo obterPrimeiro() {
        if (inicio == null) {
            return null;
        }
        return inicio.getProcesso();
    }
}
