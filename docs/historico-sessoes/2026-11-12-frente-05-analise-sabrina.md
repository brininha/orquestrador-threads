# Frente 05 - análise científica e relatório

**Data:** 12 de novembro de 2026  
**Responsável:** Sabrina  
**Status da tarefa atual:** T5.1 concluída (gráficos processados em Python/Pandas)  

## Resumo técnico do progresso
Nesta sessão, adiantamos o trabalho da frente de análise sem depender da finalização do motor em Java (Frentes 01 e 04). Implementamos um gerador de dados fictícios que respeita o contrato do CSV (`proporcao_leitores,proporcao_escritores,tipo_trava,tempo_medio_ms`) com as 5.050 rodadas previstas (101 proporções x 50 repetições).

Em seguida, criamos um pipeline completo de análise estatística em Python/Pandas para extrair os insights necessários para o relatório:
1. **Comparação de médias:** Gráfico de linhas cruzando o desempenho contínuo das duas travas.
2. **Análise de variância (desvio padrão e outliers):** Gráfico de linhas com área sombreada (`fill_between`) e boxplots (para os cenários de 10%, 50% e 90% de leitores) para provar o impacto do escalonador do SO.
3. **Cálculo de overhead no pior cenário:** Gráfico de barras isolando o cenário extremo de 100 escritoras para provar o peso das filas de prioridade.
4. **Tabela de break-even:** Geração de uma tabela calculando o ganho percentual, exportada automaticamente em um arquivo nativo `.tex` para fácil inclusão no LaTeX.

## Arquivos criados/modificados
- `gerar_mock.py` (na raiz, ferramenta de apoio para gerar `data/saida_tempos.csv`)
- `scripts/gerador_graficos.py`
- `scripts/analise_variancia.py`
- `scripts/analise_tabela.py`
- `scripts/analise_piores_cenarios.py`

## Decisões de design e setup
- Todos os gráficos estão sendo salvos em formato `.pdf` para manter a vetorização e evitar perda de qualidade na importação do LaTeX.
- Agrupamento com `.groupby().mean()` e `.groupby().std()` implementado para calcular corretamente as 50 repetições exigidas no enunciado do EP.

## Próximos passos
- **Bloqueantes:** Aguardar a Frente 04 finalizar a orquestração para substituir os dados mockados pelos tempos reais de execução do Java.
- **Próxima tarefa:** Iniciar a **T5.2** (Redação do relatório em PDF).
- **Ação imediata:** Rascunhar os parágrafos em LaTeX justificando o "ponto de virada" (onde Leitores/Escritores ultrapassa a Trava Global) e explicando o overhead de gerenciar prioridades quando o sistema está congestionado por escritores.