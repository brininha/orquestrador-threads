# Frente 01: Infraestrutura de dados e ferramentas
**Responsável:** [Integrante A]

**Objetivo:** Garantir a leitura veloz da base e ferramentas utilitárias estáveis para as threads operarem.
**Tarefas:** T1.1 (Carga do arquivo), T1.2 (Métodos de acesso), T2.1 (Sorteador randômico).
**Contratos:** Deve expor `ler(indice)` e `escrever(indice, palavra)` com tratamento de `IndexOutOfBounds`.
**Testes esperados:** Validação se a linha 36.241 retorna a palavra correta do arquivo original.