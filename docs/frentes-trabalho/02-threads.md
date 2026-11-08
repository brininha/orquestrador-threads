# Frente 02: Engenharia de threads
**Responsável:** [Integrante B]

**Objetivo:** Modelar o operário que realiza o trabalho pesado, respeitando travas que ele mesmo não gerencia.
**Tarefas:** T2.2 (Thread Leitor e Escritor).
**Contratos:** A classe deve instanciar recebendo `RegiaoCritica`. Deve chamar `pedirEntrada()` e garantir que `avisarSaida()` rode no bloco `finally`.
**Testes esperados:** Uma thread roda os 100 ciclos e aplica o `sleep` corretamente isolada do sistema.