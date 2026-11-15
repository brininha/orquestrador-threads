# Frente 02 - threads leitora e escritora

**Data:** 15 de novembro de 2026  
**Responsável:** Manu  
**Status da tarefa atual:** T2.1 implementada (código escrito, ainda sem compilar nem executar os testes)  

## Resumo técnico do progresso
Nesta sessão, modelamos as threads operárias do EP sem depender da finalização da trava da Frente 03. A `RegiaoCritica` é só recebida no construtor, então a thread nunca gerencia a sincronização. A integração usa as assinaturas já entregues pela Frente 01 (`BaseDados.ler(int)`, `BaseDados.escrever(int)` e a constante `TOTAL_PALAVRAS`).

Em seguida, estruturamos a implementação e os testes de validação:
1. **Classe base `ThreadTrabalhadora`:** concentra o `run()` comum, com entrada na região crítica fora do `try`, laço de exatos 100 acessos aleatórios, `sleep(1)` ainda dentro da região e `sair…()` no `finally`.
2. **`ThreadLeitora` e `ThreadEscritora`:** só definem qual par de métodos da trava chamar e qual acesso fazer na base (`ler()` ou `escrever()`).
3. **Sorteio sem contenção:** índices gerados com `ThreadLocalRandom`, evitando um `Random` compartilhado entre as 100 threads, que distorceria o tempo medido.

## Arquivos criados/modificados
- `src/main/java/threads/ThreadTrabalhadora.java`
- `src/main/java/threads/ThreadLeitora.java`
- `src/main/java/threads/ThreadEscritora.java`
- `src/main/java/concorrencia/RegiaoCritica.java` (provisório, só para compilar e fora do commit, caso a T3.1 não esteja no `main`)
- `src/test/java/threads/RegiaoCriticaFalsa.java` (validação local, fora do commit)
- `src/test/java/threads/ThreadsTest.java` (validação local, fora do commit)

## Decisões de design e setup
- Branch `feat/T21-threads`, seguindo o padrão do `fluxo-desenvolvimento.md`.
- Sem nenhum `println` nas classes, conforme `problemas-conhecidos.md`.
- Campo `ultimaLeitura` na leitora para o JIT não eliminar a leitura e falsear o tempo.
- Usados os nomes de métodos do contrato em `arquitetura (1).md` (`entrarLeitor()`, `sairLeitor()`, `entrarEscritor()`, `sairEscritor()`), e não `pedirEntrada()`/`avisarSaida()` do enunciado da tarefa.
- Commit só de `src/main/java/threads/`, com mensagem em português descrevendo o "o que" e o "por que".

## Próximos passos
- **Bloqueantes:** Confirmar com a Frente 03 os nomes dos métodos da trava e se `entrarLeitor()`/`entrarEscritor()` declaram `throws InterruptedException`, pois isso muda o código. A `RegiaoCritica` (T3.1) também precisa estar no `main` para compilar de verdade.
- **Próxima tarefa:** Liberar a **T4.1** (instanciação e embaralhamento do arranjo de 100 threads), que depende da T2.1.
- **Ação imediata:** Rodar os testes localmente, fazer o commit apenas dos três arquivos de `threads/`, abrir o PR para o `main` e marcar a T2.1 como "Concluído" em `quebra-tarefas.md`.