"""Gera todos os gráficos e tabelas do relatório a partir de data/saida_tempos.csv.

Uso: python src/scripts/gerar_tudo.py [caminho_csv]
"""
from analise_piores_cenarios import gerar_grafico_pior_cenario
from analise_tabela import gerar_tabela_ganho
from analise_variancia import gerar_graficos_variancia
from gerador_graficos import gerar_graficos

if __name__ == '__main__':
    gerar_graficos()
    gerar_graficos_variancia()
    gerar_tabela_ganho()
    gerar_grafico_pior_cenario()
