# Frente 03 - núcleo de concorrência

**Data:** 15 de novembro de 2026  
**Responsável:** Mikelly  
**Status da tarefa atual:** T3.1, T3.2 e T3.3 implementadas, compiladas e validadas localmente (sem deadlock nas 101 proporções)  

## Resumo técnico do progresso
Nesta sessão, entregamos as duas travas que as threads da Frente 02 recebem no construtor. Ambas tratam a base inteira como uma única região crítica e honram exatamente o contrato de `arquitetura.md` (`entrarLeitor()`, `sairLeitor()`, `entrarEscritor()`, `sairEscritor()`), sem `throws InterruptedException`, que é o que a `ThreadTrabalhadora` já espera.

1. **T3.1 - interface `RegiaoCritica`:** o arquivo do `main` estava salvo como `RegiaoCritica.Java` (J maiúsculo). O `javac` não reconhece essa extensão, então o pacote `concorrencia` não existia e as threads da Frente 02 não compilavam. Foi renomeado para `RegiaoCritica.java` e documentado o contrato de uso (entrar fora do `try`, sair no `finally`, sem exceção verificada).
2. **T3.2 - `TravaGlobal`:** monitor com um único flag `ocupada`. Leitor e escritor usam o mesmo par `entrar()`/`sair()`, então só uma thread fica na região por vez.
3. **T3.3 - `TravaLeitoresEscritores`:** monitor com `leitoresAtivos`, `leitoresEsperando` e `escritorAtivo`. Leitor só espera se houver escritor ativo. Escritor espera enquanto houver escritor ativo, leitor ativo **ou leitor aguardando**, o que garante a prioridade de leitura mesmo na saída de um escritor.

## Por que não há deadlock
- Um único monitor por trava, sem aquisição aninhada de locks (não existe espera circular).
- Toda espera é `while (condição) wait()`, imune a *spurious wakeups*.
- Toda saída que pode liberar alguém chama `notifyAll()`, então nenhum aviso se perde.
- Leitores só esperam enquanto há escritor ativo, e o escritor sempre sai após 1ms (o `sair...()` está no `finally` da Frente 02).
- Interrupção durante a espera não abandona a fila: a thread continua esperando e o status de interrupção é restaurado ao entrar. Assim `entrar...()` sempre retorna com a trava adquirida, e o `sair...()` do `finally` nunca libera uma trava que não é da thread.
- `sair...()` sem `entrar...()` correspondente lança `IllegalStateException` em vez de corromper os contadores.

A inanição do escritor no cenário 99x1 continua possível e é esperada (consequência da prioridade de leitura), como descrito em `problemas-conhecidos.md`.

## Arquivos criados/modificados
- `src/main/java/concorrencia/RegiaoCritica.java` (renomeado de `RegiaoCritica.Java` + Javadoc do contrato)
- `src/main/java/concorrencia/TravaGlobal.java`
- `src/main/java/concorrencia/TravaLeitoresEscritores.java`
- `src/test/java/concorrencia/TravasTest.java` (validação local, sem JUnit)
- `docs/quebra-tarefas.md` (T3.1, T3.2 e T3.3 marcadas como "Concluído")

## Decisões de design e setup
- **Monitor (`synchronized` + `wait/notifyAll`) nas duas travas:** a mesma primitiva dos dois lados faz o experimento comparar a política de acesso, e não o custo de primitivas diferentes.
- **Não usar `ReentrantLock` na solução clássica de semáforos:** nela o primeiro leitor trava a base e o último libera. Como o dono do `ReentrantLock` é a thread que travou, liberar de outra thread lança `IllegalMonitorStateException`. Com `Semaphore` isso funciona, mas a preferência dos leitores na saída de um escritor passa a depender do escalonador.
- **Constantes `TIPO`:** `TravaGlobal.TIPO = "global"` e `TravaLeitoresEscritores.TIPO = "leitor_escritor"`, valores aceitos por `ExportadorMetricas.salvarRodada()`, para a Frente 04 não digitar a string na mão.
- Sem nenhum `println` nas travas, conforme `problemas-conhecidos.md`.

## Validação
Rodar a partir da raiz do repositório (Java 17):

```bash
javac -d out -sourcepath src/main/java src/main/java/*/*.java src/test/java/concorrencia/TravasTest.java
java -cp out concorrencia.TravasTest
```

Resultado: 18 verificações OK, entre elas exclusão mútua da trava global (máximo 1 thread dentro), vários leitores simultâneos, escritor sempre sozinho, leitor que chega depois passando à frente do escritor da fila, interrupção sem quebrar a trava e execução das 101 proporções com as `ThreadLeitora`/`ThreadEscritora` reais e o `bd.txt` sem deadlock (global ~11,5 s e L/E ~6,3 s, uma rodada por proporção).

## Próximos passos
- **Bloqueantes:** nenhum. Ao commitar, fazer o rename com `git mv src/main/java/concorrencia/RegiaoCritica.Java src/main/java/concorrencia/RegiaoCritica.java`, já que no Windows/macOS o sistema de arquivos não diferencia maiúsculas e o Git pode não perceber a troca.
- **Próxima tarefa:** liberar a **T4.2** (loop de 101 proporções x 50 repetições), que dependia da T3.1. A Frente 04 deve criar uma trava nova a cada rodada (`new TravaGlobal()` / `new TravaLeitoresEscritores()`), assim como as threads.
- **Ação imediata:** branch `feat/T3-concorrencia`, commit apenas de `src/main/java/concorrencia/` e dos dois arquivos de `docs/`, PR para o `main`.
