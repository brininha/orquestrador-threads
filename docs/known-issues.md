# Problemas e riscos conhecidos

*   **Risco de Starvation:** A implementação de Leitores/Escritores exige prioridade para os leitores. Em laços com alto volume de leitores, o escritor corre o risco de nunca ser atendido. O log do programa pode parecer travado (deadlock), mas na verdade é inanição.
*   **Vazamento de memória (Garbage Collection):** Criaremos 505.000 objetos `Thread` durante o laço principal. Devemos garantir que o array de threads saia de escopo a cada rodada para o coletor de lixo limpá-lo, evitando `OutOfMemoryError`.
*   **Tomada de tempo viciada:** `println` no meio da simulação aciona o *scheduler* de I/O do SO e paralisa a execução. Todos os logs devem ser removidos do bloco *core* antes da extração de dados finais.