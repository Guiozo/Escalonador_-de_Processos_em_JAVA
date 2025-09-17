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
