# 🏢 Simulador de Elevador
Este projeto foi desenvolvido em parceria entre **ditimon01** e **ylapiy**, utilizando a ferramenta **Code With Me** do IntelliJ IDEA, que permitiu a colaboração em tempo real na codificação do sistema.
Este projeto é um simulador de elevadores em um prédio com múltiplos andares, onde diferentes algoritmos de decisão são aplicados para controlar os elevadores em situações simuladas.

## 🚀 Objetivo

Simular o comportamento de elevadores em um prédio com base em diferentes estratégias de despacho de chamadas:
- **Normal**: Prioriza o elevador com menos destinos ativos.
- **Economia**: Escolhe o elevador com menor consumo de energia para atender a chamada.
- **Felicidade**: Tenta reduzir o tempo de espera das pessoas.

---

## 🛠️ Tecnologias Utilizadas

- Java 
- Estruturas de dados personalizadas (listas, filas, nós encadeados)
- Serialização de dados para simulação com arquivos `.dat`

---

## 📁 Estrutura do Projeto

Simulador_Elevador/

├── src/    
│ ├── Main.java # Classe principal para execução    
│ ├── classes/ # Lógica principal do sistema    
│ │ ├── Andar.java  
│ │ ├── Elevador.java   
│ │ ├── Pessoa.java     
│ │ └── ...     
│ └── estruturas/ # Estruturas de dados implementadas       
│ ├── ListaEstatica.java    
│ ├── FilaPrioridade.java   
│ └── ...   
├── saves/ # Arquivos de simulação  
│ └── predio.dat    
└── README.md # Este arquivo

---

## ▶️ Como Executar

1. Abra o projeto em uma IDE como **IntelliJ** ou **Eclipse**.
2. Compile todos os arquivos do diretório `src/`.
3. Insira os valores desejados na classe `Main.java`.
4. Execute a classe `Main.java`.

---

## 📊 Modos de Simulação

### Modo Normal
- Seleciona o elevador com menos destinos (mais "livre").
- Menor complexidade computacional.

### Modo Economia
- Tenta minimizar o consumo de energia.
- Avalia distância e quantidade de mudanças de rota.

### Modo Felicidade
- Prioriza a satisfação dos usuários.
- Tenta atender o andar com o menor tempo de espera possível.

---

## 💾 Salvamento

A simulação utiliza serialização para carregar e salvar dados no diretório `saves/`.

---

## ✍️ Autor

Projeto acadêmico para fins educacionais. Comentários e melhorias são bem-vindos!
