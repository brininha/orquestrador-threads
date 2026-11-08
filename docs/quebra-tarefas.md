# Decomposição de tarefas

| ID | Nome da tarefa | Frente responsável | Dependências | Status |
| :--- | :--- | :--- | :--- | :--- |
| **T1.1** | Carga das 36.242 palavras do arquivo `bd.txt` para a memória | 01-dados-io | Nenhuma | A fazer |
| **T1.2** | Métodos de leitura e sobrescrita ("MODIFICADO") na base | 01-dados-io | T1.1 | A fazer |
| **T1.3** | Exportação dos tempos coletados para arquivo CSV | 01-dados-io | Nenhuma | A fazer |
| **T2.1** | Implementação das threads leitora e escritora (100 acessos + soneca de 1ms) | 02-threads | T1.2 | A fazer |
| **T3.1** | Interface `RegiaoCritica` para padronizar os acessos | 03-concorrencia | Nenhuma | A fazer |
| **T3.2** | Sincronizador de trava global (bloqueio total da base) | 03-concorrencia | T3.1 | A fazer |
| **T3.3** | Sincronizador Leitores/Escritores (com prioridade para leitores) | 03-concorrencia | T3.1 | A fazer |
| **T4.1** | Instanciação e embaralhamento aleatório do arranjo de 100 threads | 04-orquestracao | T2.1 | A fazer |
| **T4.2** | Loop de 101 proporções x 50 repetições, acionamento do `join()` e timers | 04-orquestracao | T4.1, T3.1 | A fazer |
| **T5.1** | Leitura do CSV e geração de gráficos de tempo médio com scripts (Python/Pandas) | 05-analise | T1.3, T4.2 | A fazer |
| **T5.2** | Redação do relatório PDF avaliando o ganho de desempenho | 05-analise | T5.1 | A fazer |