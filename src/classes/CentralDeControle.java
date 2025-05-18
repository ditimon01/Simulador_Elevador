package classes;

import estruturas.ListaEstatica;
import estruturas.Node;

public class CentralDeControle extends Serializacao {
    private ListaEstatica<Elevador> elevadores;
    private Predio predio;
    private EstadoCentralDeControle estado;
    private int energiaGasta;

    public enum EstadoCentralDeControle{
        Economia,
        Felicidade;
    }

    public CentralDeControle(int numeroElevadores, Predio predio, EstadoCentralDeControle estado ) {
        this.elevadores = new ListaEstatica<Elevador>(numeroElevadores);
        this.predio = predio;
        for (int i = 0; i < numeroElevadores; i++) {
            elevadores.add(new Elevador(i), i);
        }
        this.energiaGasta = 0;
        this.estado = estado;
    }

    @Override
    public void atualizar(int minutosSimulados) {


        for (int i = 0; i < elevadores.getTamanho(); i++) {
            Elevador elevador = elevadores.getElemento(i);
            int andar_anterior = elevador.getAndarAtual();
            Elevador.EstadoElevador estado_anterior = elevador.getEstado();

            verificaChamadas();
            receberPessoas(elevador);
            elevador.atualizar(minutosSimulados);
            receberPessoas(elevador);

            if(andar_anterior != elevador.getAndarAtual()){ energiaGasta++;}
            if(estado_anterior != elevador.getEstado()){ energiaGasta++;}


            Node<Pessoa> atual = elevador.getPessoasSaida().getHead();


            while (atual != null) {
                Pessoa p = atual.getElemento();
                predio.getAndares().getElemento(elevador.getAndarAtual()).adicionarPessoaAndar(p);
                elevador.getPessoasSaida().removePorElemento(p);
                atual = elevador.getPessoasSaida().getHead();
            }


            Node<Pessoa> atual2 = elevador.getPessoasDentro().getHead();

            while (atual2 != null) {
                Pessoa p = atual2.getElemento();
                p.setAndarAtual(elevador.getAndarAtual());
                atual2 = atual2.getNext();
            }

        }
    }


    private void receberPessoas(Elevador elevador) {

        if (elevador.getEstado() == Elevador.EstadoElevador.PARADO) {
            Andar andarAtual = predio.getAndares().getElemento(elevador.getAndarAtual());
            addPessoas(elevador, andarAtual);
        }

        elevador.atualizarDestino();
    }


    private void addPessoas(Elevador elevador, Andar andar) {

        while (elevador.getPessoasDentro().tamanho() < Elevador.getCapacidadeMaxima() && !andar.isEmpty()) {
            Pessoa p = andar.removerPessoa();
            if (p == null) break;
            elevador.adicionarPessoa(p);
        }

        andar.verificaPessoas();

        if (!andar.temChamada()) {
            elevador.removeDestino(andar.getNumero());
        }
    }

    public void verificaChamadas() {
        ListaEstatica<Andar> andares = predio.getAndares();
        int distancia = andares.getTamanho() + 1;
        Elevador elevadorEnviado = null;
        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            if (andarAtual.temChamada()) {
                Elevador elevadorAtual;
                int dist;
                for (int e = 0; e < elevadores.getTamanho(); e++) {
                    elevadorAtual = elevadores.getElemento(e);
                    dist = Math.abs(elevadorAtual.getAndarAtual() - andarAtual.getNumero());
                    if (elevadorAtual.getEstado() == Elevador.EstadoElevador.PARADO) {
                        if (dist < distancia) {
                            distancia = dist;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if(elevadorAtual.getEstado() == Elevador.EstadoElevador.SUBINDO && elevadorAtual.getAndarAtual() <= andarAtual.getNumero()){
                        if (dist <= distancia) {
                            distancia = dist;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if (elevadorAtual.getEstado() == Elevador.EstadoElevador.DESCENDO && elevadorAtual.getAndarAtual() >= andarAtual.getNumero()){
                        if (dist <= distancia) {
                            distancia = dist;
                            elevadorEnviado = elevadorAtual;
                        }
                    }
                }
                if(elevadorEnviado != null) {
                    elevadorEnviado.addDestino(andarAtual.getNumero());
                    elevadorEnviado.atualizarDestino();
                }
            }
        }
    }


    public ListaEstatica<Elevador> getElevadores() { return elevadores; }

    public int getEnergiaGasta() {
        return energiaGasta;
    }
}
