from configuracao import DADOS, carregar_tempos

def gerar_tabela_ganho():
    df = carregar_tempos()

    # Calcula a média de tempo para cada proporção e tipo de trava
    df_media = df.groupby(['proporcao_leitores', 'tipo_trava'])['tempo_medio_ms'].mean().reset_index()

    # Dinamiza a tabela para colocar os tipos de trava em colunas separadas
    df_pivot = df_media.pivot(index='proporcao_leitores', columns='tipo_trava', values='tempo_medio_ms').reset_index()

    # Calcula o ganho percentual (valores positivos indicam que a solução Leitores/Escritores foi mais rápida)
    df_pivot['ganho_percentual'] = ((df_pivot['global'] - df_pivot['leitor_escritor']) / df_pivot['global']) * 100

    # Renomeia os cabeçalhos seguindo as convenções de maiúsculas apenas no início
    df_pivot = df_pivot.rename(columns={
        'proporcao_leitores': 'Proporção de leitoras (%)',
        'global': 'Trava global (ms)',
        'leitor_escritor': 'Leitores e escritores (ms)',
        'ganho_percentual': 'Ganho percentual (%)'
    })

    # Arredonda os valores para duas casas decimais
    df_pivot = df_pivot.round(2)

    # Salva o arquivo CSV padrão
    df_pivot.to_csv(DADOS / 'tabela_ganho.csv', index=False)

    # Exporta a tabela formatada diretamente para LaTeX
    # longtable quebra as 101 linhas entre páginas; float_format evita os 6 dígitos padrão
    df_pivot.to_latex(DADOS / 'tabela_ganho.tex', index=False, longtable=True,
                      float_format='%.2f', escape=True,
                      caption='Tempo médio (50 repetições) por proporção de leitoras e ganho da solução de leitores e escritores sobre a trava global.',
                      label='tab:ganho')

    # O pandas escreve o aviso de quebra de página em inglês
    with open(DADOS / 'tabela_ganho.tex', encoding='utf-8') as f:
        tex = f.read()
    with open(DADOS / 'tabela_ganho.tex', 'w', encoding='utf-8') as f:
        f.write(tex.replace('Continued on next page', 'Continua na próxima página'))
    
    print("Tabelas exportadas em CSV e LaTeX com sucesso!")

if __name__ == "__main__":
    gerar_tabela_ganho()