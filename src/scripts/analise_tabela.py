import pandas as pd

def gerar_tabela_ganho():
    df = pd.read_csv('data/saida_tempos.csv')

    # Calcula a média de tempo para cada proporção e tipo de trava
    df_media = df.groupby(['proporcao_leitores', 'tipo_trava'])['tempo_medio_ms'].mean().reset_index()

    # Dinamiza a tabela para colocar os tipos de trava em colunas separadas
    df_pivot = df_media.pivot(index='proporcao_leitores', columns='tipo_trava', values='tempo_medio_ms').reset_index()

    # Calcula o ganho percentual (valores positivos indicam que a solução Leitores/Escritores foi mais rápida)
    df_pivot['ganho_percentual'] = ((df_pivot['global'] - df_pivot['leitor_escritor']) / df_pivot['global']) * 100

    # Renomeia os cabeçalhos seguindo as convenções de maiúsculas apenas no início
    df_pivot.rename(columns={
        'proporcao_leitores': 'Proporção de leitoras (%)',
        'global': 'Trava global (ms)',
        'leitor_escritor': 'Leitores e escritores (ms)',
        'ganho_percentual': 'Ganho percentual (%)'
    }, inplace=True)

    # Arredonda os valores para duas casas decimais
    df_pivot = df_pivot.round(2)

    # Salva o arquivo CSV padrão
    df_pivot.to_csv('data/tabela_ganho.csv', index=False)

    # Exporta a tabela formatada diretamente para LaTeX
    df_pivot.to_latex('data/tabela_ganho.tex', index=False)
    
    print("Tabelas exportadas em CSV e LaTeX com sucesso!")

if __name__ == "__main__":
    gerar_tabela_ganho()