import pandas as pd
import matplotlib.pyplot as plt

def gerar_grafico_pior_cenario():
    df = pd.read_csv('data/saida_tempos.csv')

    # Filtra exclusivamente o cenário sem nenhum leitor (100% escritores)
    df_pior = df[df['proporcao_leitores'] == 0]
    
    # Calcula a média de tempo para as duas travas nesse cenário
    medias = df_pior.groupby('tipo_trava')['tempo_medio_ms'].mean()
    
    tempo_global = medias.get('global', 0)
    tempo_rw = medias.get('leitor_escritor', 0)

    plt.figure(figsize=(8, 6))
    
    barras = plt.bar(['Trava global', 'Leitores e escritores'], 
                     [tempo_global, tempo_rw], 
                     color=['#d62728', '#1f77b4'],
                     width=0.6)
    
    # Textos seguindo a capitalização do português brasileiro
    plt.title('Desempenho no pior cenário (100% de threads escritoras)', fontsize=14)
    plt.ylabel('Tempo de execução (ms)', fontsize=12)
    
    # Imprime os valores cravados no topo de cada barra para facilitar a leitura no PDF
    for barra in barras:
        altura = barra.get_height()
        plt.text(barra.get_x() + barra.get_width()/2., altura + 2,
                 f'{altura:.2f} ms',
                 ha='center', va='bottom', fontsize=11, fontweight='bold')
                 
    plt.grid(True, axis='y', linestyle='--', alpha=0.7)
    plt.tight_layout()
    plt.savefig('data/grafico_pior_cenario.pdf', format='pdf')
    plt.close()
    
    print("Gráfico do pior cenário exportado em PDF com sucesso!")

if __name__ == "__main__":
    gerar_grafico_pior_cenario()