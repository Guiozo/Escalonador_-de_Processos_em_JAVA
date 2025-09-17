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
