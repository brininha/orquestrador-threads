# Decomposição de tarefas

| ID | Nome | Frente responsável | Dependências | Status |
| :--- | :--- | :--- | :--- | :--- |
| **T1.1** | Carga do arquivo `bd.txt` | 01-dados-io | Nenhuma | A fazer |
| **T1.2** | Métodos de acesso à base | 01-dados-io | T1.1 | A fazer |
| **T2.1** | Função de sorteio 100 índices | 01-dados-io | Nenhuma | A fazer |
| **T2.2** | Implementação de Leitor/Escritor | 02-threads | T1.2, T2.1 | A fazer |
| **T3.1** | Interface `RegiaoCritica` | 03-concorrencia | Nenhuma | A fazer |
| **T3.2** | Sincronizador Mutex | 03-concorrencia | T3.1 | A fazer |
| **T3.3** | Sincronizador Leitores/Escritores | 03-concorrencia | T3.1 | A fazer |
| **T4.1** | Construtor de cenários (baterias) | 04-orquestracao | T2.2 | A fazer |
| **T4.2** | Loop 101x50, join() e timers | 04-orquestracao | T4.1, T3.1 | A fazer |
| **T5.1** | Tabulação de CSV e gráficos | 05-analise | T4.2 | A fazer |
| **T5.2** | Redação do PDF | 05-analise | T5.1 | A fazer |