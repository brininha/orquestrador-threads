import pandas as pd
import matplotlib.pyplot as plt

def gerar_graficos_variancia():
    df = pd.read_csv('data/saida_tempos.csv')

    # Calcula média e desvio padrão para o gráfico de área sombreada
    df_stats = df.groupby(['proporcao_leitores', 'tipo_trava'])['tempo_medio_ms'].agg(['mean', 'std']).reset_index()
    
    df_global = df_stats[df_stats['tipo_trava'] == 'global']
    df_rw = df_stats[df_stats['tipo_trava'] == 'leitor_escritor']

    # Gráfico de linha contínua com desvio padrão
    plt.figure(figsize=(10, 6))
    
    plt.plot(df_global['proporcao_leitores'], df_global['mean'], label='Trava global', color='#d62728', linewidth=2)
    plt.fill_between(df_global['proporcao_leitores'], 
                     df_global['mean'] - df_global['std'], 
                     df_global['mean'] + df_global['std'], 
                     color='#d62728', alpha=0.2)
                     
    plt.plot(df_rw['proporcao_leitores'], df_rw['mean'], label='Leitores e escritores', color='#1f77b4', linewidth=2)
    plt.fill_between(df_rw['proporcao_leitores'], 
                     df_rw['mean'] - df_rw['std'], 
                     df_rw['mean'] + df_rw['std'], 
                     color='#1f77b4', alpha=0.2)

    # Títulos e rótulos seguindo as regras de capitalização do português brasileiro
    plt.title('Tempo médio de execução com margem de desvio padrão', fontsize=14)
    plt.xlabel('Proporção de threads leitoras (%)', fontsize=12)
    plt.ylabel('Tempo de execução (ms)', fontsize=12)
    plt.grid(True, linestyle='--', alpha=0.7)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.savefig('data/grafico_desvio_padrao.pdf', format='pdf')
    plt.close()

    # Gráfico de boxplot para cenários isolados (10%, 50% e 90% de leitoras)
    cenarios = [10, 50, 90]
    df_box = df[df['proporcao_leitores'].isin(cenarios)]
    
    plt.figure(figsize=(10, 6))
    
    dados_plot = []
    labels = []
    cores = []
    
    for c in cenarios:
        # Extrai os dados puros das 50 repetições para a trava global
        dados_plot.append(df_box[(df_box['proporcao_leitores'] == c) & (df_box['tipo_trava'] == 'global')]['tempo_medio_ms'])
        labels.append(f'{c}%\nGlobal')
        cores.append('#d62728')
        
        # Extrai os dados puros das 50 repetições para leitores e escritores
        dados_plot.append(df_box[(df_box['proporcao_leitores'] == c) & (df_box['tipo_trava'] == 'leitor_escritor')]['tempo_medio_ms'])
        labels.append(f'{c}%\nL/E')
        cores.append('#1f77b4')
        
    bplot = plt.boxplot(dados_plot, patch_artist=True, label=labels)
    
    # Aplica as cores correspondentes para manter a identidade visual
    for patch, color in zip(bplot['boxes'], cores):
        patch.set_facecolor(color)
        patch.set_alpha(0.7)

    plt.title('Variância e distribuição do tempo em cenários selecionados', fontsize=14)
    plt.ylabel('Tempo de execução (ms)', fontsize=12)
    plt.grid(True, axis='y', linestyle='--', alpha=0.7)
    plt.tight_layout()
    plt.savefig('data/grafico_boxplots.pdf', format='pdf')
    plt.close()
    
    print("Gráficos de variância salvos em PDF com sucesso!")

if __name__ == "__main__":
    gerar_graficos_variancia()