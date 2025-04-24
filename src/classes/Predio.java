package classes;

import estruturas.Random;
import estruturas.ListaEstatica;


public class Predio extends Serializacao {

    private ListaEstatica<Andar> andares;
    private CentralDeControle centralDeControle;
    private Random randomizacao;


    public Predio(int numeroAndares, int numeroElevadores){
        this.andares = new ListaEstatica(numeroAndares);
        for (int i = 0; i < numeroAndares; i++){
            andares.add(new Andar(i),i);
        }
        this.centralDeControle = new CentralDeControle(numeroElevadores, this);

    }

    @Override
    public void atualizar(int minutosSimulados ) {
        if (minutosSimulados % 5 == 0) { // A cada 5 minutos, chega uma pessoa
            int andarOrigem = randomizacao.GeradorDeNumeroAleatorio((andares.getTamanho()));
            int andarDestino = randomizacao.GeradorDeNumeroAleatorio((andares.getTamanho()));
            while (andarDestino == andarOrigem) {
                andarDestino = randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho());
            }
            Pessoa p = new Pessoa(minutosSimulados, randomizacao.GeradorDeNumeroAleatorio(3) , andarOrigem, andarDestino);
            andares.getVetor()[andarOrigem].adicionarPessoa(p);
        }

        centralDeControle.atualizar(minutosSimulados);
    }

    public CentralDeControle getCentralDeControle() { return centralDeControle; }
    public ListaEstatica<Andar> getAndares() { return andares;}


}