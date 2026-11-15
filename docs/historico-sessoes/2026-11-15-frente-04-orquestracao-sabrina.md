# Frente 04 - orquestração e medição (+ T5.2 seção 3)

**Data:** 15 de novembro de 2026  
**Responsável:** Sabrina  
**Status da tarefa atual:** T4.1 e T4.2 concluídas e executadas (50 repetições, dados reais em `data/saida_tempos.csv`). T5.2: seção 3 do relatório escrita.

## Resumo técnico do progresso
1. **Correção de build:** o `main` ainda tinha `concorrencia/RegiaoCritica.Java` (J maiúsculo), então nada compilava. Renomeado para `RegiaoCritica.java`.
2. **T4.1 - `MontadorArranjo`:** cria as 100 threads com `new` e coloca cada uma em uma posição **ainda vazia** sorteada do arranjo (passo 1 do enunciado ao pé da letra, O(1) por inserção). As threads saem no estado `NEW`.
3. **T4.2 - `MotorSimulador`:** laço 101 proporções x 50 repetições x 2 travas. Trava e arranjo novos a cada rodada. Cronômetro (`System.nanoTime`) começa com o arranjo pronto e para após o `join()` da última thread. Tempos ficam em `long[2][101][50]` e só vão para o CSV no fim (o CSV é recriado a cada execução).
4. **T5.2:** seção 3 do `relatorio/relatorio.tex` (resultados e análise) escrita com os dados reais; scripts de análise ajustados para gerar a tabela LaTeX com `longtable`, 2 casas decimais e `%` escapado.

## Decisões de design
- **Aquecimento:** 20 rodadas descartadas antes da medição para o JIT compilar o código (sem isso as primeiras proporções saem mais lentas).
- **Ordem alternada:** em cada repetição as duas travas rodam em sequência, alternando qual vai primeiro.
- **Falha em thread:** `UncaughtExceptionHandler` em cada thread; se alguma lançar exceção, a rodada é abortada em vez de gravar um tempo inválido.
- **Arredondamento:** o contrato do exportador usa `long tempoMs`, então o tempo em ns é arredondado para ms. Resolução de ±0,5 ms, suficiente segundo o enunciado.
- **Base carregada uma vez:** as escritas de `"MODIFICADO"` se acumulam entre rodadas, o que não altera o custo de acesso.
- `println` só entre proporções (fora da medição), para mostrar progresso.

## Como rodar
```bash
javac -d out -sourcepath src/main/java src/main/java/*/*.java
java -cp out orquestracao.MotorSimulador            # 50 repetições (~15 min)
java -cp out orquestracao.MotorSimulador 1 data/bd.txt /tmp/teste.csv   # teste rápido
python3 -m venv .venv && .venv/bin/pip install -r requirements.txt
for s in gerador_graficos analise_variancia analise_tabela analise_piores_cenarios; do .venv/bin/python src/scripts/$s.py; done
```

## Resultados (i7-1185G7, Ubuntu 24.04, OpenJDK 21, 866 s)
- Trava global: ~111,7 ms constante (100 passagens exclusivas de ~1,1 ms).
- L/E: `T ≈ 1,12·escritoras + 3,8 ms` (R² = 0,999), de 112,5 ms (0 leitoras) a 4,2 ms (100 leitoras).
- Ganho a partir de 2 leitoras; acima de 5% a partir de 10; 96% com 100 leitoras. Com 0 e 1 leitora, empate dentro do ruído.

## Próximos passos
- Compilar o relatório (não há LaTeX na máquina; usar Overleaf ou instalar `texlive`) e preencher os nomes/NUSP faltantes.
- As outras seções do relatório ficam com o restante do grupo; os detalhes da metodologia estão em comentários na seção 2 do `.tex`.
