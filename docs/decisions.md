# Registro de decisões arquiteturais (ADR)

*   **ADR 001: Padrão Strategy para travas**
    *   *Status:* Aprovada.
    *   *Decisão:* As threads não farão distinção interna sobre qual versão do projeto está rodando. Elas receberão uma interface de travamento imposta pelo motor.
    *   *Justificativa:* Evita duplicação de código da Thread e facilita a testabilidade unitária.
*   **ADR 002: Base de dados em RAM como array estático simples**
    *   *Status:* Proposta.
    *   *Decisão:* Usar um array puro (`String[] base = new String[36242]`) em vez de estruturas dinâmicas como `ArrayList`.
    *   *Justificativa:* O tamanho é fixo e conhecido. Reduz o *overhead* de memória, vital para a execução das mais de 5.000 baterias de testes.