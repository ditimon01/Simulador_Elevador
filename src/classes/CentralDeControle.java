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
            Elevador elevador = elevadores.getElemento(i);

            if(elevador.getEstado() == Elevador.EstadoElevador.PARADO){
                receberPessoas(elevador);
            }
            elevador.atualizar(minutosSimulados);
        }
    }


    private void receberPessoas(Elevador elevador){
        // sempre verifica as chamadas do andar terreo ao topo
        // modificar para verificar os andares mais proximos ao elevador primeiro
        for(int i = 0; i < predio.getAndares().getTamanho(); i++){
            Andar andar = predio.getAndares().getElemento(i);
            if(andar.temChamada()){
                if(elevador.getAndarAtual() == andar.getNumero()){
                    addPessoas(elevador, andar);
                }else{
                    elevador.setDestino(andar.getNumero());
                    System.out.println("Elevador " + elevador.getNumeroElevador() + " com destino definido para o andar " + andar.getNumero());
                    break;
                }
            }
        }
    }


    private void addPessoas(Elevador elevador, Andar andar){

        while(elevador.getPessoasDentro().tamanho() < Elevador.getCapacidadeMaxima()){
            Pessoa p = andar.removerPessoa();
            if(p == null) break;
            elevador.adicionarPessoa(p);
            elevador.setDestino(p.getAndarDestino());
            System.out.println("Elevador " + elevador.getNumeroElevador() + " com destino definido para o andar " + elevador.getDestino());
        }

        andar.verificaPessoas();

    }


    public ListaEstatica<Elevador> getElevadores() { return elevadores; }

}
