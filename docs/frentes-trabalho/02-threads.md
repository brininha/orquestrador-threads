# Frente 02: engenharia de threads
**Responsável:** Manu
**Objetivo:** Modelar o operário que realiza o trabalho pesado, respeitando travas que ele mesmo não gerencia.
**Tarefas:** T2.1 (Implementação das threads Leitora e Escritora).
**Contratos:** A classe deve instanciar recebendo `RegiaoCritica`. O método `run()` deve chamar `pedirEntrada()`, fazer um loop exato de 100 acessos aleatórios usando os métodos da Frente 01, dar um `sleep(1)` (1 milissegundo simulando validação), e garantir que `avisarSaida()` rode no bloco `finally`.
**Testes esperados:** Uma thread roda os 100 ciclos e aplica o `sleep(1)` corretamente isolada do sistema, antes de liberar a trava.