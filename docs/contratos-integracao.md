# Contratos de integração

Para evitar conflitos durante a união do código, todas as frentes devem respeitar estritamente as assinaturas abaixo.

## 1. Contrato de acesso a dados (Frente 01 -> Frente 02)
A classe que armazena a base deve expor:
*   `String ler(int indice)`: Retorna a string. Dispara erro se índice > 36.241.
*   `void escrever(int indice, String palavra)`: Substitui o valor.

## 2. Contrato de sincronização (Frente 03 -> Frente 02)
A interface `EstrategiaSincronizacao` dita como as threads pedem licença:
*   `void pedirEntradaLeitor()`
*   `void avisarSaidaLeitor()`
*   `void pedirEntradaEscritor()`
*   `void avisarSaidaEscritor()`

*Uso obrigatório nas threads (Frente 02):* A thread leitora deve chamar `pedirEntradaLeitor()` imediatamente antes do loop de 100 acessos, realizar os acessos e o `sleep`, e chamar `avisarSaidaLeitor()` obrigatoriamente dentro de um bloco `finally` para evitar deadlock em caso de erro.

## 3. Contrato de orquestração (Frente 04 -> Todas)
As threads devem ser construídas recebendo a regra vigente:
*   `new Leitor(EstrategiaSincronizacao regra)`
*   `new Escritor(EstrategiaSincronizacao regra)`