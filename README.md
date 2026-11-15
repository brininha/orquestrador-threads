# Sistemas Operacionais: leitores e escritores

Repositório do exercício-programa 2 (EP2) da disciplina de Sistemas Operacionais. O projeto simula 100 threads concorrentes acessando uma base de dados em memória (as 36.242 palavras de `data/bd.txt`) e compara o desempenho de duas formas de sincronização:

- **Trava global:** uma única thread por vez na base, seja leitora ou escritora.
- **Leitores e escritores com prioridade para leitores:** várias leitoras ao mesmo tempo; escritoras só entram com a base vazia.

Cada proporção de leitoras/escritoras (0/100 até 100/0) é executada 50 vezes com cada trava.

## Como executar

Requer Java 17+ e Python 3.10+. Todos os comandos partem da raiz do repositório.

**1. Compilar e rodar o experimento** (cerca de 15 minutos; gera `data/saida_tempos.csv`):

```bash
javac -d out -sourcepath src/main/java src/main/java/*/*.java
java -cp out orquestracao.MotorSimulador
```

Para um teste rápido, com 1 repetição e saída em outro arquivo:

```bash
java -cp out orquestracao.MotorSimulador 1 data/bd.txt /tmp/teste.csv
```

**2. Gerar gráficos e tabelas** (em `data/`):

```bash
python3 -m venv .venv
.venv/bin/pip install -r requirements.txt
.venv/bin/python src/scripts/gerar_tudo.py
```

## Organização

| Pasta | Conteúdo |
| :--- | :--- |
| `src/main/java/dados` | Carga da base e exportação do CSV |
| `src/main/java/threads` | Threads leitora e escritora |
| `src/main/java/concorrencia` | Interface `RegiaoCritica` e as duas travas |
| `src/main/java/orquestracao` | Montagem do arranjo de threads e laço do experimento |
| `src/scripts` | Análise em Python (gráficos e tabelas para o relatório) |
| `data` | Base de entrada, tempos medidos e figuras geradas |

## Documentação

- [Contexto geral e requisitos](docs/contexto-geral.md)
- [Arquitetura e contratos de integração](docs/arquitetura.md)
- [Problemas conhecidos](docs/problemas-conhecidos.md)
- [Quebra de tarefas](docs/quebra-tarefas.md) e [frentes de trabalho](docs/frentes-trabalho/)
- [Fluxo de desenvolvimento (Git)](docs/fluxo-desenvolvimento.md)
- [Histórico de sessões](docs/historico-sessoes/)
