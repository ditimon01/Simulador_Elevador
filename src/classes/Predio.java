package classes;

import estruturas.*;

// Classe que representa um prédio que possui andares e uma central de controle dos elevadores
public class Predio extends Serializacao {

    // Lista de andares do prédio
    private final ListaEstatica<Andar> andares;

    // Central que gerencia os elevadores
    private final CentralDeControle centralDeControle;

    // Objeto de randomização usado para gerar números aleatórios
    private final Random randomizacao;

    // Tempo em segundos que um elevador leva para se mover entre andares
    private int tempoMovimentoElevador;

    // Define se está em horário de pico (gera mais pessoas)
    private boolean horarioPico;

    // Define se os andares de origem são aleatórios ou sempre o térreo (0)
    private boolean andaresAleatorios;


    /**
     * Construtor do prédio
     */
    public Predio(int numeroAndares, int numeroElevadores, int capacidadeElevador, CentralDeControle.EstadoCentralDeControle estado, int tempoMovimentoElevador, boolean horarioPico , boolean andaresAleatorios, int energiaDeslocamento, int energiaParada){
        // Validações básicas
        if(numeroAndares < 5)throw new IllegalArgumentException("Numero de andares deve ser maior ou igual a 5");
        if(capacidadeElevador < 5)throw new IllegalArgumentException("Capacidade do elevador deve ser maior ou igual a 5");
        if(numeroElevadores < 1)throw new IllegalArgumentException("Numero de elevadores deve ser maior que 0");
        if(tempoMovimentoElevador < 1)throw new IllegalArgumentException("Tempo de movimento de elevador deve ser maior que 0");
        if(energiaDeslocamento < 1)throw new IllegalArgumentException("Energia de deslocamento deve ser maior que 0");
        if(energiaParada < 1)throw new IllegalArgumentException("Energia de parada deve ser maior que 0");

        // Cria lista de andares
        this.andares = new ListaEstatica<>(numeroAndares);
        this.randomizacao = new Random();

        // Inicializa os andares
        for (int i = 0; i < numeroAndares; i++){
            andares.add(new Andar(i),i);
        }

        // Inicializa a central de controle dos elevadores
        this.centralDeControle = new CentralDeControle(numeroElevadores, capacidadeElevador, this, estado, energiaDeslocamento, energiaParada);

        this.tempoMovimentoElevador = tempoMovimentoElevador;
        this.horarioPico = horarioPico;
        this.andaresAleatorios = andaresAleatorios;
    }

    /**
     * Método chamado a cada atualização de tempo simulado.
     * Responsável por gerar pessoas, atualizar pessoas e os elevadores.
     */
    @Override
    public void atualizar(int segundosSimulados ) {

        // Cálculo do intervalo de tempo para geração de pessoas
        int intervaloMax = 60; // prédio pequeno (5 andares)
        int intervaloMin = tempoMovimentoElevador;  // prédio muito grande
        double fator = 0.8;

        // Fórmula proporcional
        double intervalo_temp =  intervaloMax - (fator * (andares.getTamanho() - 5));

        int multiplo = (int) Math.ceil(intervalo_temp / intervaloMin);
        intervalo_temp = intervaloMin * multiplo;

        // Garante que nunca fique abaixo do mínimo
        if (intervalo_temp < intervaloMin) {
            intervalo_temp = intervaloMin;
        }

        int intervalo = (int) intervalo_temp;

        // Geração de novas pessoas no prédio
        if (segundosSimulados % intervalo == 0) {

            ListaEstatica<Integer> destinos;

            // Se for horário de pico, gera o dobro de pessoas
            int cont = 1;
            if(horarioPico){
                cont++;
            }

            for(int e = 0; e < cont; e++) {
                // Determina o andar de origem
                int andarOrigem;
                if(andaresAleatorios){
                    andarOrigem = randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho());}
                else{
                    andarOrigem = 0;
                }

                // Geração dos destinos da pessoa (mínimo 2 e máximo 4)
                do {
                    destinos = new ListaEstatica<>(randomizacao.GeradorDeNumeroAleatorio(5));
                } while (destinos.getTamanho() <= 1);

                // Preenche os destinos (exceto o térreo inicialmente)
                for (int i = 0; i < destinos.getTamanho() - 1; i++) {
                    int num = randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho());
                    if (num != 0) {
                        destinos.add(num, i);
                    } else i--;// Evita destino 0 antes da posição final
                }

                // O último destino sempre é o térreo (0)
                destinos.add(0, destinos.getTamanho() - 1);

                // Geração do ID da pessoa
                int id ;
                if(horarioPico){
                    id = ((segundosSimulados / intervalo) * 2) + e;
                }else{
                    id = segundosSimulados / intervalo;
                }

                // Cria a pessoa
                Pessoa p = new Pessoa(id, randomizacao.GeradorDeNumeroAleatorio(2), andarOrigem, destinos);

                // Adiciona na fila do andar de origem
                andares.getElemento(andarOrigem).adicionarPessoaFila(p);
            }
        }

        // Atualiza todas as pessoas nos andares
        for(int i = 0; i < andares.getTamanho(); i++){
            Andar andar = andares.getElemento(i);
            Node<Pessoa> atual = andar.getPessoas().getHead();
            while(atual != null){
                Pessoa p = atual.getElemento();
                p.atualizar(segundosSimulados);

                // Se o tempo de espera acabou e não está no elevador, volta para a fila
                if(p.getTempoAndar() == 0 && !p.estaDentroDoElevador()){
                    andar.adicionarPessoaFila(p);
                }
                atual = atual.getNext();
            }
        }

        // Adiciona tempo de espera para quem está na fila
        for(int i = 0; i < andares.getTamanho(); i++){

            Andar andar = andares.getElemento(i);
            NodePrior atual  = andar.getFila().getHead();
            while(atual != null){
                NodeDuplo<Pessoa> atual2 = atual.getFila().getHead();
                while(atual2 != null){
                    Pessoa p = atual2.getElemento();
                    if(p != null){
                        p.addTempoEspera(tempoMovimentoElevador);
                    }
                    atual2 = atual2.getNext();
                }
                atual = atual.getNext();
            }


            andar.verificaPessoas();// Limpa ou atualiza status da fila
        }

        // Atualiza a central de controle (elevadores)
        centralDeControle.atualizar(segundosSimulados);
    }

    // Getters
    public ListaEstatica<Andar> getAndares() { return andares;}

    public CentralDeControle getCentralDeControle() {
        return centralDeControle;
    }

    public boolean isHorarioPico() {
        return horarioPico;
    }

}