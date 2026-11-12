# Frente 03: núcleo de concorrência
**Responsável:** Mikelly
**Objetivo:** O coração do trabalho teórico. Implementar travas seguras contra Deadlocks.
**Tarefas:** T3.1 (Interface base `RegiaoCritica`), T3.2 (Versão Trava Global), T3.3 (Versão Leitores/Escritores com prioridade de leitura).
**Contratos:** Responsável por honrar a interface exigida pela Frente 02. Em ambas as implementações, a base inteira vira uma única região crítica (não há bloqueio por registro individual).
**Riscos:** Inanição natural no cenário 99x1 é esperada, mas deadlocks (travamento absoluto) reprovam o trabalho.