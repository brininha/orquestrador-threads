# Requisitos e restrições (EP2)

## Requisitos funcionais (Explícitos)
*   **Carga de dados:** O sistema deve ler o arquivo `bd.txt` (36.242 linhas) e armazenar cada palavra em uma estrutura na memória RAM.
*   **Simulador de concorrência:** O sistema deve gerenciar exatamente 100 threads simultâneas, divididas entre Leitores e Escritores em proporções variáveis.
*   **Comportamento da thread:** Cada thread deve realizar 100 acessos a posições aleatórias da base. O leitor lê para uma variável local. O escritor sobrescreve a posição com "MODIFICADO".
*   **Retenção intencional:** Após os 100 acessos, a thread deve executar uma pausa (`sleep(1ms)`) **dentro** da região crítica.
*   **Bateria de testes:** O sistema deve executar 101 proporções diferentes (de 0 L / 100 E até 100 L / 0 E). Cada proporção deve ser repetida 50 vezes.
*   **Versões de sincronização:** O sistema deve operar sob duas políticas:
    1.  Versão 1: Leitores e Escritores (prioridade absoluta aos leitores).
    2.  Versão 2: Exclusão mútua simples (bloqueio total).

## Restrições técnicas
*   Toda a base de dados deve ser tratada como uma **única região crítica**. Não é permitido bloqueio granular por índice.
*   Proibido realizar operações de I/O em disco ou impressões no console (`println`) durante a tomada de tempo.
*   O array de objetos threads deve ser reconstruído e repopulado com posições aleatórias a cada uma das 50 rodadas, evitando determinismo.
*   O cronômetro principal deve aguardar o fim de todas as threads utilizando o mecanismo de `join()`.

## Critérios de avaliação
*   **Relatório (70% da nota):** Documento em PDF justificando os tempos médios de execução das duas versões, com tabelas e gráficos, explicando teoricamente em quais proporções ocorreu ganho de desempenho.
*   **Código:** Arquivo `.zip` nomeado com o número USP do representante da entrega.