# Estratégia de testes

1.  **Testes unitários (Fase 1):**
    *   Validar limite de array no carregamento de `bd.txt`.
    *   Rodar `Leitor` e `Escritor` injetando uma estratégia Mock (que não faz bloqueios) para garantir que o sorteio de índices e o `sleep` funcionam isolados.
2.  **Testes de integração (Fase 2 - Mutex):**
    *   Injetar `SincronizadorMutex` em 100 threads. Validar via logs temporários se as entradas ocorrem de forma estritamente sequencial.
3.  **Testes de estresse e starvation (Fase 3 - Prioridade):**
    *   Injetar `SincronizadorLeitoresEscritores` com 99 Leitores e 1 Escritor.
    *   *Critério de aceitação:* O sistema não deve gerar *deadlock*. O tempo do escritor será intencionalmente prejudicado (*starvation*), mas a execução deve terminar com sucesso.