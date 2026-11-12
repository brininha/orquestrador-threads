# Frente 04: orquestração e medição
**Responsável:** Sabrina
**Objetivo:** Construir a esteira de montagem, gerenciar ciclos de vida e controlar o cronômetro oficial do teste.
**Tarefas:** T4.1 (Construtor de proporções e embaralhamento do array de 100 threads), T4.2 (Laço master e tomada de tempo).
**Contratos:** Importar classes de todas as outras frentes. Fazer o loop das proporções (0-100 a 100-0), executando 50 vezes cada proporção. O tempo (`currentTimeMillis`) começa após popular o array de threads e termina após o `join()` da última. Exportar array bidimensional via Frente 01.
**Riscos:** Distorção de tempo se houver I/O ou `println` no loop de medição; vazamento de memória nas 5.050 execuções. O array de threads deve ser recriado e reembaralhado a cada rodada.