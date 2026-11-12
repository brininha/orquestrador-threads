# Frente 01: infraestrutura de dados e ferramentas
**Responsável:** Mari
**Objetivo:** Garantir a leitura veloz da base e ferramentas utilitárias estáveis para as threads operarem e exportação dos resultados.
**Tarefas:** T1.1 (Carga do arquivo `bd.txt` com 36.242 palavras), T1.2 (Métodos de acesso), T1.3 (Exportador de CSV para a Frente 05).
**Contratos:** Deve expor `ler(indice)` e `escrever(indice)` (que sempre escreverá "MODIFICADO" na posição), com tratamento de `IndexOutOfBounds`. A base de dados deve permanecer toda na memória.
**Testes esperados:** Validação se a linha 36.241 retorna a palavra correta do arquivo original.