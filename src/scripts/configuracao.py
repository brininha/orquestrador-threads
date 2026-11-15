"""Caminhos e identidade visual compartilhados pelos scripts de análise.

Os caminhos são resolvidos a partir da raiz do repositório, então os scripts funcionam
qualquer que seja o diretório de onde forem chamados.
"""
import sys
from pathlib import Path

import pandas as pd

RAIZ = Path(__file__).resolve().parents[2]
DADOS = RAIZ / 'data'
CSV_PADRAO = DADOS / 'saida_tempos.csv'

ROTULOS = {'global': 'Trava global', 'leitor_escritor': 'Leitores e escritores'}
CORES = {'global': '#d62728', 'leitor_escritor': '#1f77b4'}


def carregar_tempos():
    """Lê o CSV do Java (ou o caminho passado como primeiro argumento do script)."""
    caminho = Path(sys.argv[1]) if len(sys.argv) > 1 else CSV_PADRAO
    return pd.read_csv(caminho)

# Sem data de criação no PDF: regerar os gráficos com os mesmos dados não gera diff no Git
PDF_SEM_DATA = {'CreationDate': None}
