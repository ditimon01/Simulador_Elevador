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

        ListaEstatica<Andar> andaresOrdenados = ordenarAndaresPorProximidade(elevador);

        for(int i = 0; i < andaresOrdenados.getTamanho(); i++){
            Andar andar = andaresOrdenados.getElemento(i);
            if(andar.temChamada()){
                if(elevador.getAndarAtual() == andar.getNumero()){
                    addPessoas(elevador, andar);
                }else{
                    elevador.addDestino(andar.getNumero());
                    elevador.atualizarDestino();
                    System.out.println("Elevador " + elevador.getNumeroElevador() + " com destino definido para o andar " + elevador.getDestino());
                    break;
                }
            }
        }
    }


    private ListaEstatica<Andar> ordenarAndaresPorProximidade(Elevador elevador) {
        ListaEstatica<Andar> andares = predio.getAndares();
        ListaEstatica<Andar> ordenados = new ListaEstatica<>(andares.getTamanho());

        // Copia os andares
        for(int i = 0; i < andares.getTamanho(); i++) {
            ordenados.add(andares.getElemento(i), i);
        }

        // Ordenação simples por proximidade
        for(int i = 0; i < ordenados.getTamanho() - 1; i++) {
            for(int j = 0; j < ordenados.getTamanho() - i - 1; j++) {
                Andar a1 = ordenados.getElemento(j);
                Andar a2 = ordenados.getElemento(j+1);
                int dist1 = Math.abs(a1.getNumero() - elevador.getAndarAtual());
                int dist2 = Math.abs(a2.getNumero() - elevador.getAndarAtual());

                if(dist2 < dist1) {
                    ordenados.set(a2, i);
                    ordenados.set(a1, j);
                }
            }
        }

        return ordenados;
    }


    private void addPessoas(Elevador elevador, Andar andar){

        boolean pessoasEntraram = false;

        while(elevador.getPessoasDentro().tamanho() < Elevador.getCapacidadeMaxima()){
            Pessoa p = andar.removerPessoa();
            if(p == null) break;

            elevador.adicionarPessoa(p);
            pessoasEntraram = true;
            elevador.addDestino(p.getAndarDestino());
            System.out.println("Pessoa " + p.getId() + " entrou no elevador " +
                    elevador.getNumeroElevador() + " com destino para o andar " + p.getAndarDestino());
        }

        if(pessoasEntraram){
            elevador.removeDestino(andar.getNumero());
            elevador.atualizarDestino();
        }


        andar.verificaPessoas();

    }


    public ListaEstatica<Elevador> getElevadores() { return elevadores; }

}
