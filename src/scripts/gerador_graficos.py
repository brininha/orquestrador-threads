import matplotlib.pyplot as plt

from configuracao import CORES, DADOS, PDF_SEM_DATA, ROTULOS, carregar_tempos

def gerar_graficos():
    # Lê o CSV gerado pelo Java
    df = carregar_tempos()

    # Calcula a média real do tempo agrupando pela proporção de leitores e pelo tipo de trava
    # Isso garante que as 50 repetições de cada cenário sejam condensadas em um único ponto no gráfico
    df_media = df.groupby(['proporcao_leitores', 'tipo_trava'])['tempo_medio_ms'].mean().reset_index()

    # Separa os dados para facilitar a plotagem
    df_global = df_media[df_media['tipo_trava'] == 'global']
    df_rw = df_media[df_media['tipo_trava'] == 'leitor_escritor']

    plt.figure(figsize=(10, 6))

    plt.plot(df_global['proporcao_leitores'], df_global['tempo_medio_ms'], 
             label=ROTULOS['global'], color=CORES['global'], linewidth=2)
    plt.plot(df_rw['proporcao_leitores'], df_rw['tempo_medio_ms'], 
             label=ROTULOS['leitor_escritor'], color=CORES['leitor_escritor'], linewidth=2)

    plt.title('Desempenho de concorrência: trava global vs leitores e escritores', fontsize=14)
    plt.xlabel('Proporção de threads leitoras (%)', fontsize=12)
    plt.ylabel('Tempo médio de execução (ms)', fontsize=12)
    
    # Adiciona grid para facilitar a leitura dos pontos de interseção (onde ocorre o ganho)
    plt.grid(True, linestyle='--', alpha=0.7)
    plt.legend(fontsize=12)
    
    # Salva diretamente em PDF para manter a imagem vetorizada e não perder qualidade no relatório
    plt.tight_layout()
    plt.savefig(DADOS / 'grafico_desempenho.pdf', format='pdf', metadata=PDF_SEM_DATA)
    plt.close()
    print("Gráfico de desempenho salvo em data/grafico_desempenho.pdf")

if __name__ == "__main__":
    gerar_graficos()