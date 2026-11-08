# Contexto geral do sistema

Este projeto é um Exercício-Programa (EP) da disciplina de Sistemas Operacionais. O objetivo é simular e medir o desempenho do acesso concorrente a uma estrutura de dados, comparando uma abordagem com Leitores e Escritores (com prioridade para leitores) contra uma abordagem de bloqueio global (trava única para qualquer acesso).

## Requisitos de negócio e regras do experimento
- A base de dados será carregada na memória a partir do arquivo `bd.txt` (36.242 palavras, uma por linha) em um arranjo, lista ligada ou estrutura equivalente de Strings.
- O sistema criará um total exato de 100 threads, que podem ser instanciadas como Leitoras ou Escritoras dependendo da proporção da rodada.
- O arranjo de 100 threads deve ser embaralhado aleatoriamente antes do início da execução para dissipar efeitos determinísticos.
- Cada thread fará exatos 100 acessos a posições aleatórias da estrutura de dados. 
- Se a thread for Leitora, ela lê a palavra da posição e armazena em uma variável local (que não será mais usada). Se for Escritora, ela escreve "MODIFICADO" na posição.
- Terminados os 100 acessos, a thread deve dormir por 1ms **ainda dentro da região crítica**, saindo dela somente após acordar.
- O sistema deve ser executado 50 vezes para cada proporção possível de Leitores/Escritores (de 0/100 até 100/0), extraindo o tempo médio de cada cenário.

## Decisões de design e restrições
- **Zero I/O no disco durante a medição:** Nenhuma modificação será salva no disco para não prejudicar o tempo.
- **Minimização de chamadas de sistema:** Estão proibidos `println` ou logs no console durante as regiões críticas e laços de acesso, pois isso acorda o escalonador do SO e distorce os tempos.
- **Medição de tempo:** O tempo (`currentTimeMillis` ou `nanoTime`) deve ser marcado logo após o povoamento das threads (quando todas estão em estado `new` ou `runnable`, mas antes de efetivamente trabalharem) e o término da última thread (usando `join()`).
- **Linguagem:** Java (versão moderna).