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
        for(int i = 0; i < elevadores.getTamanho(); i++){
            Elevador elevador = elevadores.getVetor()[i];
            elevador.atualizar(minutosSimulados);

            if(elevador.getEstado() == Elevador.EstadoElevador.PARADO){
                receberPessoas(elevador);
            }
        }
    }


    private void receberPessoas(Elevador elevador){
        // sempre verifica as chamadas do andar terreo ao topo
        // modificar para verificar os andares mais proximos ao elevador primeiro
        for(int i = 0; i < predio.getAndares().getTamanho(); i++){
            Andar andar = predio.getAndares().getVetor()[i];
            if(andar.temChamada()){
                if(elevador.getAndarAtual() == andar.getNumero()){
                    addPessoas(elevador, andar);
                }else{
                    elevador.setDestino(andar.getNumero());
                    System.out.println("Elevador " + elevador.getAndarAtual() + " destino " + andar.getNumero() + " com destino definido para o andar " + andar.getNumero());
                    break;
                }
            }
        }
    }


    private void addPessoas(Elevador elevador, Andar andar){
        Pessoa p = andar.removerPessoa();
        while(p != null && elevador.getPessoasDentro().tamanho() < Elevador.getCapacidadeMaxima()){
            elevador.adicionarPessoa(p);
            p = andar.removerPessoa();
        }
        andar.getPainel().resetar();
    }


    public ListaEstatica<Elevador> getElevadores() { return elevadores; }

}
