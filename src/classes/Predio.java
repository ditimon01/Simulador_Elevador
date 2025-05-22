package classes;

import estruturas.*;


public class Predio extends Serializacao {

    private final ListaEstatica<Andar> andares;
    private final CentralDeControle centralDeControle;
    private final Random randomizacao;
    private int tempoMovimentoElevador;
    private boolean horarioPico;
    private boolean andaresAleatorios;


    public Predio(int numeroAndares, int numeroElevadores, int capacidadeElevador, CentralDeControle.EstadoCentralDeControle estado, int tempoMovimentoElevador, boolean horarioPico , boolean andaresAleatorios, int energiaDeslocamento, int energiaParada){
        if(numeroAndares < 5)throw new IllegalArgumentException("Numero de andares deve ser maior ou igual a 5");
        if(capacidadeElevador < 5)throw new IllegalArgumentException("Capacidade do elevador deve ser maior ou igual a 5");
        if(numeroElevadores < 1)throw new IllegalArgumentException("Numero de elevadores deve ser maior que 0");
        if(tempoMovimentoElevador < 1)throw new IllegalArgumentException("Tempo de movimento de elevador deve ser maior que 0");
        if(energiaDeslocamento < 1)throw new IllegalArgumentException("Energia de deslocamento deve ser maior que 0");
        if(energiaParada < 1)throw new IllegalArgumentException("Energia de parada deve ser maior que 0");
        this.andares = new ListaEstatica<>(numeroAndares);
        this.randomizacao = new Random();
        for (int i = 0; i < numeroAndares; i++){
            andares.add(new Andar(i),i);
        }
        this.centralDeControle = new CentralDeControle(numeroElevadores, capacidadeElevador, this, estado, energiaDeslocamento, energiaParada);
        this.tempoMovimentoElevador = tempoMovimentoElevador;
        this.horarioPico = horarioPico;
        this.andaresAleatorios = andaresAleatorios;
    }

    @Override
    public void atualizar(int segundosSimulados ) {

        // intervalo de tempo entre a geração de pessoas
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

        if (segundosSimulados % intervalo == 0) {

            ListaEstatica<Integer> destinos;

            int cont = 1;
            if(horarioPico){
                cont++;
            }

            for(int e = 0; e < cont; e++) {
                int andarOrigem;
                if(andaresAleatorios){
                    andarOrigem = randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho());}
                else{
                    andarOrigem = 0;
                }
                do {
                    destinos = new ListaEstatica<>(randomizacao.GeradorDeNumeroAleatorio(5));
                } while (destinos.getTamanho() <= 1);


                for (int i = 0; i < destinos.getTamanho() - 1; i++) {
                    int num = randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho());
                    if (num != 0) {
                        destinos.add(num, i);
                    } else i--;
                }

                destinos.add(0, destinos.getTamanho() - 1);

                int id ;
                if(horarioPico){
                    id = ((segundosSimulados / intervalo) * 2) + e;
                }else{
                    id = segundosSimulados / intervalo;
                }

                Pessoa p = new Pessoa(id, randomizacao.GeradorDeNumeroAleatorio(2), andarOrigem, destinos);
                andares.getElemento(andarOrigem).adicionarPessoaFila(p);
            }
        }


        for(int i = 0; i < andares.getTamanho(); i++){
            Andar andar = andares.getElemento(i);
            Node<Pessoa> atual = andar.getPessoas().getHead();
            while(atual != null){
                Pessoa p = atual.getElemento();
                p.atualizar(segundosSimulados);

                if(p.getTempoAndar() == 0 && !p.estaDentroDoElevador()){
                    andar.adicionarPessoaFila(p);
                }
                atual = atual.getNext();
            }
        }

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


            andar.verificaPessoas();
        }

        centralDeControle.atualizar(segundosSimulados);
    }


    public ListaEstatica<Andar> getAndares() { return andares;}

    public CentralDeControle getCentralDeControle() {
        return centralDeControle;
    }


    public boolean isHorarioPico() {
        return horarioPico;
    }

}