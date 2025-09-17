/**
 * Classe que representa um nó na lista ligada de processos
 */
public class No {
    private Processo processo;
    private No proximo;

    public No(Processo processo) {
        this.processo = processo;
        this.proximo = null;
    }

    // Getters
    public Processo getProcesso() { return processo; }
    public No getProximo() { return proximo; }

    // Setters
    public void setProcesso(Processo processo) { this.processo = processo; }
    public void setProximo(No proximo) { this.proximo = proximo; }
}