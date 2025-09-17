iCEVOS - Sistema Operacional com Escalonador de Processos

Overview

iCEVOS é um simulador de sistema operacional focado no desenvolvimento de um escalonador de processos. O projeto implementa completamente um escalonador que gerencia múltiplos níveis de prioridade, previne inanição (starvation) de processos de baixa prioridade, e simula o comportamento de um sistema operacional real. O sistema utiliza estruturas de dados implementadas do zero (sem usar bibliotecas prontas) para gerenciar filas de processos com diferentes prioridades e estados.

Estado Atual

PROJETO COMPLETO E FUNCIONANDO

Todas as especificações do projeto foram implementadas
Algoritmo de escalonamento com 3 níveis de prioridade funcionando
Sistema anti-inanição após 5 processos de alta prioridade
Gerenciamento de recursos DISCO com bloqueio/desbloqueio FIFO
Leitura de dados de arquivo CSV/TXT
Saída detalhada no console com logs de cada ciclo
Bug crítico de corrupção da fila de bloqueados identificado e corrigido
Testes com múltiplos processos DISCO passando com sucesso
Como executar o projeto iCEVOS na IDEA

Passo a Passo:

Abrir o Projeto na IDEA(Vscode ou IntelliJ o de preferência)
Abra o IntelliJ ou Vscode IDEA
Clique em "Open" e selecione a pasta raiz do projeto (que contém as pastas src/, config/ e examples/)
Aguarde o IntelliJ indexar o projeto
Configurar a Classe Principal
No painel do projeto, navegue até: src/iCEVOS.java
Clique com botão direito no arquivo iCEVOS.java
Selecione "Run 'iCEVOS.main()'"
Configuração Automática O código foi adaptado para funcionar automaticamente na IDEA. Ele procura o arquivo de configuração nos seguintes locais:
config/processos.txt (execução a partir da raiz do projeto)
../config/processos.txt (execução a partir da pasta src)
Outros caminhos alternativos
Execução Alternativa Você também pode:
Criar uma nova configuração de execução:
Run > Edit Configurations
Criar nova "Application"
Main class: iCEVOS
Working directory: [pasta raiz do projeto]
Personalização
Para usar um arquivo de processos diferente:
Run > Edit Configurations
Adicione o nome do arquivo em "Program arguments"
Exemplo: "meu_arquivo.txt"
Arquivos de Teste
O projeto inclui dois arquivos de exemplo:
config/processos.txt (configuração principal)
examples/teste_bloqueio.txt (teste de múltiplos bloqueios)
Estrutura do Projeto:

projeto/ 
- src/ │ 
- iCEVOS.java (classe principal) │
- Scheduler.java (escalonador) │ 
- Processo.java (classe processo) │ 
- ListaDeProcessos.java (lista customizada) 
  - └── No.java (nó da lista ligada) 
- config/ 
 -└── processos.txt (configuração de processos) 
 -└── examples/
 -└── teste_bloqueio.txt (arquivo de teste)

Notas:
o código foi feito utilizando o auxilio de I
O projeto não usa bibliotecas externas do Java
A execução é rápida e mostra o resultado completo no console
