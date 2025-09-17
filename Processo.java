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
