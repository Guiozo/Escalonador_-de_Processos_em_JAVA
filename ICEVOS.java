import java.io.*;
import java.util.Scanner;

/**
 * Classe principal do simulador iCEVOS
 * Lê dados de arquivo e executa o escalonador
 */
public class iCEVOS {
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
