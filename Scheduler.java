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
    
    // Executa um ciclo de CPU
    public void executarCicloDeCPU() {
        ciclo_atual++;
        System.out.println("\n=== CICLO " + ciclo_atual + " ===");
        
        // 1. Desbloqueia processo mais antigo se houver
        desbloquearProcesso();
        
        // 2. Mostra estado atual das listas
        mostrarEstadoListas();
        
        // 3. Seleciona processo para execução
        Processo processoParaExecutar = selecionarProximoProcesso();
        
        if (processoParaExecutar == null) {
            System.out.println("Nenhum processo para executar.");
            return;
        }
        
        // 4. Verifica se processo precisa de recurso DISCO
        if (processoParaExecutar.precisaDisco()) {
            System.out.println("Processo " + processoParaExecutar.getId() + 
                             " solicita recurso DISCO - BLOQUEANDO (Total bloqueados: " + 
                             (lista_bloqueados.getTamanho() + 1) + ")");
            // Simplesmente adiciona o processo à lista de bloqueados
            lista_bloqueados.adicionarNoFinal(processoParaExecutar);
            return;
        }
        
        // 5. Executa o processo
        System.out.println("Executando: " + processoParaExecutar);
        processoParaExecutar.executarCiclo();
        
        // 6. Verifica se processo terminou
        if (processoParaExecutar.terminou()) {
            System.out.println("Processo " + processoParaExecutar.getId() + " TERMINOU");
        } else {
            // Reinsere o processo no final da sua lista de origem
            adicionarProcesso(processoParaExecutar);
            System.out.println("Processo " + processoParaExecutar.getId() + 
                             " recolocado na lista (ciclos restantes: " + 
                             processoParaExecutar.getCiclosNecessarios() + ")");
        }
    }

    // Desbloqueia o processo mais antigo da lista de bloqueados
    private void desbloquearProcesso() {
        if (!lista_bloqueados.estaVazia()) {
            Processo processoDesbloqueado = lista_bloqueados.removerDoInicio();
            // Libera o recurso para que não seja bloqueado novamente
            processoDesbloqueado.liberarRecurso();
            adicionarProcesso(processoDesbloqueado);
            System.out.println("Processo " + processoDesbloqueado.getId() + 
                             " DESBLOQUEADO (Bloqueados restantes: " + 
                             lista_bloqueados.getTamanho() + ")");
        }
    }
    
// Seleciona o próximo processo seguindo regras de anti-inanição
    private Processo selecionarProximoProcesso() {
        // Regra de prevenção de inanição
        if (contador_ciclos_alta_prioridade >= 5) {
            System.out.println("Anti-inanição ativada! Executando processo de prioridade média/baixa");
            contador_ciclos_alta_prioridade = 0;
            
            // Tenta média prioridade primeiro
            if (!lista_media_prioridade.estaVazia()) {
                return lista_media_prioridade.removerDoInicio();
            }
            // Se não houver média, tenta baixa
            if (!lista_baixa_prioridade.estaVazia()) {
                return lista_baixa_prioridade.removerDoInicio();
            }
        }
        
        // Execução padrão: alta -> média -> baixa
        if (!lista_alta_prioridade.estaVazia()) {
            contador_ciclos_alta_prioridade++;
            return lista_alta_prioridade.removerDoInicio();
        }
        
        if (!lista_media_prioridade.estaVazia()) {
            return lista_media_prioridade.removerDoInicio();
        }
        
        if (!lista_baixa_prioridade.estaVazia()) {
            return lista_baixa_prioridade.removerDoInicio();
        }
        
        return null; // Nenhum processo disponível
    }

    // Mostra o estado atual de todas as listas
    private void mostrarEstadoListas() {
        System.out.println("Lista Alta Prioridade: " + lista_alta_prioridade);
        System.out.println("Lista Média Prioridade: " + lista_media_prioridade);
        System.out.println("Lista Baixa Prioridade: " + lista_baixa_prioridade);
        System.out.println("Lista Bloqueados: " + lista_bloqueados);
        System.out.println("Contador Anti-Inanição: " + contador_ciclos_alta_prioridade + "/5");
    }

    // Verifica se ainda há processos para executar
    public boolean temProcessos() {
        return !lista_alta_prioridade.estaVazia() || 
               !lista_media_prioridade.estaVazia() || 
               !lista_baixa_prioridade.estaVazia() || 
               !lista_bloqueados.estaVazia();
    }
}
