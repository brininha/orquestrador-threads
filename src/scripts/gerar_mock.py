import pandas as pd
import numpy as np
import os

# Cria o diretório data caso não exista no seu Ubuntu
os.makedirs('data', exist_ok=True)

dados = []

# Simula as proporções de 0 a 100 leitores (101 cenários) e 50 repetições
for leitores in range(101):
    escritores = 100 - leitores
    
    for _ in range(50):
        # Simula tempo da trava global (costuma ser mais lento e constante)
        tempo_global = np.random.normal(loc=150, scale=15)
        dados.append([leitores, escritores, 'global', round(tempo_global, 2)])
        
        # Simula tempo de leitores e escritores (prioridade leitor deve melhorar com mais leitores)
        # Se tem muito escritor, o starvation pode gerar tempos mais altos
        tempo_rw = np.random.normal(loc=180 - leitores, scale=20) if leitores > 10 else np.random.normal(loc=200, scale=30)
        dados.append([leitores, escritores, 'leitor_escritor', round(max(10, tempo_rw), 2)])

df_mock = pd.DataFrame(dados, columns=['proporcao_leitores', 'proporcao_escritores', 'tipo_trava', 'tempo_medio_ms'])
df_mock.to_csv('data/saida_tempos.csv', index=False)

print("Arquivo data/saida_tempos.csv gerado com sucesso para testes!")