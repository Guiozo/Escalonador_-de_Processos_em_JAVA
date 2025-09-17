/**
 * Classe que representa um processo no escalonador iCEVOS
 */
public class Processo {
    private int id;
    private String nome;
    private int prioridade; // 1-Alta, 2-Média, 3-Baixa
    private int ciclos_necessarios;
    private String recurso_necessario; // pode ser null
    private int prioridade_original; // para lembrar a prioridade original após bloqueio

    public Processo(int id, String nome, int prioridade, int ciclos_necessarios, String recurso_necessario) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.prioridade_original = prioridade;
        this.ciclos_necessarios = ciclos_necessarios;
        this.recurso_necessario = recurso_necessario;
    }
// Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public int getPrioridadeOriginal() { return prioridade_original; }
    public int getCiclosNecessarios() { return ciclos_necessarios; }
    public String getRecursoNecessario() { return recurso_necessario; }

    // Setters
    public void setPrioridade(int prioridade) { this.prioridade = prioridade; }
    public void setCiclosNecessarios(int ciclos) { this.ciclos_necessarios = ciclos; }
    public void setRecursoNecessario(String recurso) { this.recurso_necessario = recurso; }
    
    // Libera o recurso necessário (usado após desbloqueio)
    public void liberarRecurso() { this.recurso_necessario = null; }

    // Método para diminuir ciclos necessários
    public void executarCiclo() {
        if (ciclos_necessarios > 0) {
            ciclos_necessarios--;
        }
    }
  // Verifica se o processo terminou
    public boolean terminou() {
        return ciclos_necessarios <= 0;
    }

    // Verifica se precisa do recurso DISCO
    public boolean precisaDisco() {
        return "DISCO".equals(recurso_necessario);
    }
    
    // Verifica se foi bloqueado por recurso DISCO na primeira vez
    public boolean foiBloqueadoPorDisco() {
        return "DISCO".equals(recurso_necessario);
    }

    @Override
    public String toString() {
        return String.format("P%d(%s, Prio:%d, Ciclos:%d, Recurso:%s)", 
                id, nome, prioridade, ciclos_necessarios, 
                recurso_necessario != null ? recurso_necessario : "Nenhum");
    }
}
