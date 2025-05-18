package classes;

import estruturas.Node;
import estruturas.Random;
import estruturas.ListaEstatica;


public class Predio extends Serializacao {

    private final ListaEstatica<Andar> andares;
    private final CentralDeControle centralDeControle;
    private final Random randomizacao;


    public Predio(int numeroAndares, int numeroElevadores, CentralDeControle.EstadoCentralDeControle estado){
        this.andares = new ListaEstatica<>(numeroAndares);
        this.randomizacao = new Random();
        for (int i = 0; i < numeroAndares; i++){
            andares.add(new Andar(i),i);
        }
        this.centralDeControle = new CentralDeControle(numeroElevadores, this, estado);

    }

    @Override
    public void atualizar(int minutosSimulados ) {
        if (minutosSimulados % 2 == 0) { // A cada 5 minutos, chega uma pessoa
            int andarOrigem = 0;
            ListaEstatica<Integer> destinos;
            do{
                destinos = new ListaEstatica<>(randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho()+1));
            }while(destinos.getTamanho() <= 1);


            for (int i = 0; i < andares.getTamanho()-1; i++) {
                int num = randomizacao.GeradorDeNumeroAleatorio(andares.getTamanho());
                if(num != 0) {
                    destinos.add(num, i);
                }else i--;

            }

            destinos.add(0, destinos.getTamanho()-1);

            Pessoa p = new Pessoa(minutosSimulados, randomizacao.GeradorDeNumeroAleatorio(3) , andarOrigem, destinos);
            andares.getElemento(andarOrigem).adicionarPessoaFila(p);
        }

        for(int i = 0; i < andares.getTamanho(); i++){
            Andar andar = andares.getElemento(i);
            andar.verificaPessoas();
        }


        for(int i = 0; i < andares.getTamanho(); i++){
            Andar andar = andares.getElemento(i);
            Node<Pessoa> atual = andar.getPessoas().getHead();
            while(atual != null){
                Pessoa p = atual.getElemento();
                p.atualizar(minutosSimulados);
                if(p.getTempoAndar() == 0 && !p.estaDentroDoElevador()){
                    andar.adicionarPessoaFila(p);
                }
                atual = atual.getNext();
            }
        }

        centralDeControle.atualizar(minutosSimulados);
    }


    public ListaEstatica<Andar> getAndares() { return andares;}

    public CentralDeControle getCentralDeControle() {
        return centralDeControle;
    }
}