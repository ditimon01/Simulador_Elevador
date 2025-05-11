package classes;

import estruturas.ListaEstatica;

public class CentralDeControle extends Serializacao  {
    private ListaEstatica<Elevador> elevadores;
    private Predio predio;


    public CentralDeControle(int numeroElevadores, Predio predio) {
        this.elevadores = new ListaEstatica<Elevador>(numeroElevadores);
        this.predio = predio;
        for (int i = 0; i < numeroElevadores; i++) {
            elevadores.add(new Elevador(i), i);
        }
    }

    @Override
    public void atualizar(int minutosSimulados) {

        for(int i = 0; i < predio.getAndares().getTamanho(); i++){
            Andar andar = predio.getAndares().getElemento(i);
            andar.verificaPessoas();
        }

        for(int i = 0; i < elevadores.getTamanho(); i++){
            Elevador elevador = elevadores.getElemento(i);
            elevador.atualizar(minutosSimulados);
            receberPessoas(elevador);
        }
    }


    private void receberPessoas(Elevador elevador){

        if(elevador.getEstado() == Elevador.EstadoElevador.PARADO){
            Andar andarAtual = predio.getAndares().getElemento(elevador.getAndarAtual());
            if(andarAtual.temChamada()){
                addPessoas(elevador, andarAtual);
            }
        }

        for(int i = 0; i < predio.getAndares().getTamanho(); i++){
            Andar andar = predio.getAndares().getElemento(i);

            if(andar.getNumero() == elevador.getAndarAtual() || !andar.temChamada()){
                continue;
            }

            if(!elevador.getDestinos().contem(andar.getNumero())){
                elevador.addDestino(andar.getNumero());
            }
        }
        elevador.atualizarDestino();
    }


    private void addPessoas(Elevador elevador, Andar andar){

        while(elevador.getPessoasDentro().tamanho() < Elevador.getCapacidadeMaxima() && !andar.isEmpty()){
            Pessoa p = andar.removerPessoa();
            if(p == null) break;
            elevador.adicionarPessoa(p);
        }

        andar. verificaPessoas();

        if(!andar.temChamada()){
            elevador.removeDestino(andar.getNumero());
        }
    }


    public ListaEstatica<Elevador> getElevadores() { return elevadores; }

}
