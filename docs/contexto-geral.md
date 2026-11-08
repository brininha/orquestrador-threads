# Contexto central - EP2 de Sistemas operacionais (Leitores e escritores)

**Objetivo do EP2**
Implementar e analisar o desempenho de soluções para o problema clássico de Leitores e Escritores em concorrência, comparando uma abordagem de exclusão mútua simples (bloqueio total) com uma abordagem que prioriza leitores.

**Descrição geral da solução**
O sistema orquestrará 100 threads (leitores ou escritores) operando simultaneamente sobre uma base de dados compartilhada na memória RAM[cite: 1]. Serão testadas 101 proporções (de 0 a 100 leitores), com 50 repetições cada, para gerar tempos médios de execução e embasar um relatório analítico final.

**Requisitos e restrições do enunciado**
* **Base de dados:** O arquivo "bd.txt" (36.242 palavras) deve ser carregado na memória. Não é permitido escrever em disco durante a execução.
* **Acesso:** Cada thread realiza 100 acessos em índices aleatórios. Leitores apenas guardam o valor localmente; escritores alteram a string para "MODIFICADO".
* **Gargalo intencional:** Após os 100 acessos, a thread deve executar `sleep(1)` ainda dentro da região crítica.
* **Granulação:** A base inteira deve ser travada como uma única região crítica.
* **Regra de prioridade:** Na Versão 1, escritores devem esperar até que não haja mais leitores na base.
* **Tempo:** O cronômetro inicia após o array de threads ser populado e para após o término da última thread (uso de `join()` é obrigatório)[cite: 2]. Prints no console são proibidos durante a tomada de tempo.

**Arquitetura definida**
* Padrão Strategy para injeção do mecanismo de sincronização (Mutex vs. Leitores/Escritores).
* Separação estrita em camadas: I/O de dados, Modelagem das threads, Núcleo de concorrência, Orquestrador de baterias, e Exportador de relatórios.
* Recriação completa do array de threads a cada nova rodada para evitar cache e estado viciado do Sistema Operacional.

**Linguagem e ferramentas**
* Linguagem: Java (padrão)
* Bibliotecas: Apenas bibliotecas nativas de concorrência e I/O.
* Testes/Métricas: Exportação para CSV.

**Limitações e alertas**
* Risco de inanição (starvation) dos escritores no cenário com 99 leitores.
* Risco de estouro de memória se os objetos thread não forem descartados corretamente após as 50 execuções.