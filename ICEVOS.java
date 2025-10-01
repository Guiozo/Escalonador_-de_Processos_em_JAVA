import java.io.*;
import java.util.Scanner;

/**
 * Classe principal do simulador iCEVOS
 * Lê dados de arquivo e executa o escalonador
 */
public class ICEVOS {
    public static void main(String[] args) {
        System.out.println("=== SIMULADOR iCEVOS - ESCALONADOR DE PROCESSOS ===\n");
        
        String nomeArquivo = "processos.txt";
        
        // Permite especificar arquivo via linha de comando
        if (args.length > 0) {
            nomeArquivo = args[0];
        }
        
        Scheduler scheduler = new Scheduler();
        
        // Carrega processos do arquivo - tenta múltiplos caminhos
        if (!carregarProcessosComCaminhos(scheduler, nomeArquivo)) {
            // Se não conseguir carregar do arquivo, cria processos de exemplo
            System.out.println("Arquivo não encontrado em nenhum caminho. Criando processos de exemplo...\n");
            criarProcessosExemplo(scheduler);
        }
        
        // Executa simulação
        executarSimulacao(scheduler);
    }

    // Carrega processos tentando múltiplos caminhos (compatível com IntelliJ e outros IDEs)
    private static boolean carregarProcessosComCaminhos(Scheduler scheduler, String nomeArquivo) {
        // Lista de caminhos possíveis para o arquivo
        String[] caminhosPossiveis = {
            nomeArquivo,                          // Caminho direto
            "config/" + nomeArquivo,              // Para execução no diretório raiz
            "../config/" + nomeArquivo,           // Para execução no diretório src
            "src/../config/" + nomeArquivo,       // Para IntelliJ
            "./config/" + nomeArquivo,            // Caminho relativo
            System.getProperty("user.dir") + "/config/" + nomeArquivo  // Caminho absoluto
        };
        
        for (String caminho : caminhosPossiveis) {
            if (carregarProcessos(scheduler, caminho)) {
                return true;
            }
        }
        
        return false;
    }
    
// Carrega processos de um arquivo específico
    private static boolean carregarProcessos(Scheduler scheduler, String caminhoArquivo) {
        try {
            File arquivo = new File(caminhoArquivo);
            if (!arquivo.exists()) {
                return false;
            }
            
            Scanner scanner = new Scanner(arquivo);
            System.out.println("Carregando processos do arquivo: " + caminhoArquivo);
            
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine().trim();
                
                // Ignora linhas vazias e comentários
                if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                }
                
                // Formato esperado: id ,nome , prioridade ,ciclos_necessarios ,recurso_necessario
                String[] dados = linha.split(",");
                
                if (dados.length >= 4) {
                    try {
                        int id = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        int prioridade = Integer.parseInt(dados[2].trim());
                        int ciclos = Integer.parseInt(dados[3].trim());
                        String recurso = null;
                        
                        if (dados.length > 4 && !dados[4].trim().isEmpty() && !dados[4].trim().equals("null")) {
                            recurso = dados[4].trim();
                        }
                        
                        Processo processo = new Processo(id, nome, prioridade, ciclos, recurso);
                        scheduler.adicionarProcesso(processo);
                        System.out.println("Carregado: " + processo);
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao processar linha: " + linha);
                    }
                }
            }
            
            scanner.close();
            return true;
            
        } catch (FileNotFoundException e) {
            // Não exibe erro aqui pois será tentado com outros caminhos
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo " + caminhoArquivo + ": " + e.getMessage());
            return false;
        }
    }

     private static void criarProcessosExemplo(Scheduler scheduler) {
        // Processos de exemplo com diferentes prioridades e recursos
        Processo p1 = new Processo(1, "Sistema", 1, 3, null);
        Processo p2 = new Processo(2, "Editor", 2, 5, "DISCO");
        Processo p3 = new Processo(3, "Backup", 3, 4, null);
        Processo p4 = new Processo(4, "Antivirus", 1, 2, null);
        Processo p5 = new Processo(5, "Navegador", 2, 6, null);
        Processo p6 = new Processo(6, "Compilador", 1, 4, "DISCO");
        Processo p7 = new Processo(7, "Limpeza", 3, 3, null);
        
        scheduler.adicionarProcesso(p1);
        scheduler.adicionarProcesso(p2);
        scheduler.adicionarProcesso(p3);
        scheduler.adicionarProcesso(p4);
        scheduler.adicionarProcesso(p5);
        scheduler.adicionarProcesso(p6);
        scheduler.adicionarProcesso(p7);
        
        System.out.println("Processos de exemplo criados:");
        System.out.println("P1: Sistema (Alta, 3 ciclos)");
        System.out.println("P2: Editor (Média, 5 ciclos, DISCO)");
        System.out.println("P3: Backup (Baixa, 4 ciclos)");
        System.out.println("P4: Antivirus (Alta, 2 ciclos)");
        System.out.println("P5: Navegador (Média, 6 ciclos)");
        System.out.println("P6: Compilador (Alta, 4 ciclos, DISCO)");
        System.out.println("P7: Limpeza (Baixa, 3 ciclos)\n");
    }

    // Executa a simulação do escalonador
    private static void executarSimulacao(Scheduler scheduler) {
        System.out.println("=== INICIANDO SIMULAÇÃO ===");
        
        int maxCiclos = 999999999; // Limite de segurança
        int ciclo = 0;
        
        while (scheduler.temProcessos() && ciclo < maxCiclos) {
            scheduler.executarCicloDeCPU();
            ciclo++;

        }
        
        if (ciclo >= maxCiclos) {
            System.out.println("\n*** SIMULAÇÃO INTERROMPIDA - Limite de ciclos atingido ***");
        } else {
            System.out.println("\n*** SIMULAÇÃO CONCLUÍDA - Todos os processos terminaram ***");
        }
        
        System.out.println("Total de ciclos executados: " + ciclo);
    }
}
