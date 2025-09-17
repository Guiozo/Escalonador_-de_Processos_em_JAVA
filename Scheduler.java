/**
 * Classe principal do escalonador de processos iCEVOS
 * Implementa algoritmo de escalonamento com prevenção de inanição
 */
public class Scheduler {
    // Listas de processos por prioridade
    private ListaDeProcessos lista_alta_prioridade;
    private ListaDeProcessos lista_media_prioridade;
    private ListaDeProcessos lista_baixa_prioridade;
    private ListaDeProcessos lista_bloqueados;
    
    // Contador para prevenção de inanição
    private int contador_ciclos_alta_prioridade;
    
    // Contador de ciclos total
    private int ciclo_atual;

    public Scheduler() {
        this.lista_alta_prioridade = new ListaDeProcessos();
        this.lista_media_prioridade = new ListaDeProcessos();
        this.lista_baixa_prioridade = new ListaDeProcessos();
        this.lista_bloqueados = new ListaDeProcessos();
        this.contador_ciclos_alta_prioridade = 0;
        this.ciclo_atual = 0;
    }

    // Adiciona processo à lista de prioridade correta
    public void adicionarProcesso(Processo processo) {
        switch (processo.getPrioridade()) {
            case 1: // Alta prioridade
                lista_alta_prioridade.adicionarNoFinal(processo);
                break;
            case 2: // Média prioridade
                lista_media_prioridade.adicionarNoFinal(processo);
                break;
            case 3: // Baixa prioridade
                lista_baixa_prioridade.adicionarNoFinal(processo);
                break;
            default:
                System.out.println("ERRO: Prioridade inválida para processo " + processo.getId());
        }
    }
