package classes;

import estruturas.ListaEstatica;
import estruturas.Node;

public class CentralDeControle extends Serializacao {
    private ListaEstatica<Elevador> elevadores;
    private Predio predio;
    private final EstadoCentralDeControle estado;
    private int energiaGasta;
    private int tempoEsperaTotal;
    private int maiorTempoEspera;
    private int chamadasAtendidas;

    public enum EstadoCentralDeControle{
        Economia,
        Felicidade,
        Normal;
    }

    public CentralDeControle(int numeroElevadores, Predio predio, EstadoCentralDeControle estado ) {
        this.elevadores = new ListaEstatica<Elevador>(numeroElevadores);
        this.predio = predio;
        for (int i = 0; i < numeroElevadores; i++) {
            elevadores.add(new Elevador(i), i);
        }
        this.energiaGasta = 0;
        this.estado = estado;
        tempoEsperaTotal = 0;
        maiorTempoEspera = 0;
        chamadasAtendidas = 0;
    }

    @Override
    public void atualizar(int segundosSimulados) {


        for (int i = 0; i < elevadores.getTamanho(); i++) {
            Elevador elevador = elevadores.getElemento(i);
            int andar_anterior = elevador.getAndarAtual();
            Elevador.EstadoElevador estado_anterior = elevador.getEstado();

            // adiciona as pessoas quando o elevador já está no andar
            receberPessoas(elevador);
            //escolha baseado na heurística escolhida
            if(estado == EstadoCentralDeControle.Economia){
                verificaChamadasEconomia();
            }else if(estado == EstadoCentralDeControle.Felicidade){
                verificaChamadasFelicidade();
            }else if(estado == EstadoCentralDeControle.Normal){
            }

            elevador.atualizar(segundosSimulados);


            if(andar_anterior != elevador.getAndarAtual()){ energiaGasta+=2;}
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
            tempoEsperaTotal += p.getTempoEspera();
            if(p.getTempoEspera() > maiorTempoEspera){
                maiorTempoEspera = p.getTempoEspera();
            }

            elevador.adicionarPessoa(p);
            chamadasAtendidas++;
        }

        andar.verificaPessoas();

        if (!andar.temChamada() || elevador.getPessoasDentro().tamanho() == Elevador.getCapacidadeMaxima()) {
            elevador.removeDestino(andar.getNumero());
        }
    }


    public void verificaChamadasEconomia() {
        ListaEstatica<Andar> andares = predio.getAndares();
        int menorEnergiaGasta = Integer.MAX_VALUE;
        Elevador elevadorEnviado = null;

        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            andarAtual.verificaPessoas();
            if (andarAtual.temChamada()) {
                Elevador elevadorAtual;
                int energiaGastaAtual = 0;
                for (int e = 0; e < elevadores.getTamanho(); e++) {
                    elevadorAtual = elevadores.getElemento(e);
                    energiaGastaAtual = (Math.abs(andarAtual.getNumero() - elevadorAtual.getAndarAtual())) * 2;

                    if(elevadorAtual.getPessoasDentro().tamanho() == Elevador.getCapacidadeMaxima()){
                        continue;
                    }

                    if(elevadorAtual.getAndarAtual() == andarAtual.getNumero()){
                        elevadorEnviado = null;
                        break;
                    }


                    //cálculo do gasto baseado nos destinos de cada elevador
                    Node<Integer> andarDestinoAtual = elevadorAtual.getDestinos().getHead();

                    if(andarDestinoAtual != null && andarAtual.getNumero() > andarDestinoAtual.getElemento()){
                        andarDestinoAtual = andarDestinoAtual.getNext();
                        while(andarDestinoAtual != null){
                            if(andarAtual.getNumero() > andarDestinoAtual.getElemento()){
                                energiaGastaAtual++;
                            }
                            andarDestinoAtual = andarDestinoAtual.getNext();
                        }
                    }else if(andarDestinoAtual != null && andarAtual.getNumero() < andarDestinoAtual.getElemento()){
                        andarDestinoAtual = andarDestinoAtual.getNext();
                        while(andarDestinoAtual != null){
                            if(andarAtual.getNumero() < andarDestinoAtual.getElemento()){
                                energiaGastaAtual++;
                            }
                            andarDestinoAtual = andarDestinoAtual.getNext();
                        }
                    }

                    //escolha do elevador com menor gasto
                    if (elevadorAtual.getEstado() == Elevador.EstadoElevador.PARADO) {
                        energiaGastaAtual++;
                        if(energiaGastaAtual < menorEnergiaGasta){
                            menorEnergiaGasta = energiaGastaAtual;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if (elevadorAtual.getEstado() == Elevador.EstadoElevador.SUBINDO && elevadorAtual.getAndarAtual() < andarAtual.getNumero()) {
                        if(energiaGastaAtual < menorEnergiaGasta){
                            menorEnergiaGasta = energiaGastaAtual;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if (elevadorAtual.getEstado() == Elevador.EstadoElevador.DESCENDO && elevadorAtual.getAndarAtual() > andarAtual.getNumero()) {
                        if(energiaGastaAtual < menorEnergiaGasta){
                            menorEnergiaGasta = energiaGastaAtual;
                            elevadorEnviado = elevadorAtual;
                        }
                    }
                }
                if(elevadorEnviado != null){
                    elevadorEnviado.addDestino(andarAtual.getNumero());
                    elevadorEnviado.atualizarDestino();
                }
            }
        }
    }


    public void verificaChamadasFelicidade() {
        ListaEstatica<Andar> andares = predio.getAndares();
        int distancia = andares.getTamanho() + 1;
        Elevador elevadorEnviado = null;

        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            andarAtual.verificaPessoas();
            if (andarAtual.temChamada()) {

                Elevador elevadorAtual;
                int dist;
                for (int e = 0; e < elevadores.getTamanho(); e++) {

                    elevadorAtual = elevadores.getElemento(e);
                    dist = Math.abs(elevadorAtual.getAndarAtual() - andarAtual.getNumero());

                    if(elevadorAtual.getPessoasDentro().tamanho() == Elevador.getCapacidadeMaxima()){
                        continue;
                    }

                    if(elevadorAtual.getAndarAtual() == andarAtual.getNumero()){
                        elevadorEnviado = null;
                        break;
                    }

                    if (elevadorAtual.getEstado() == Elevador.EstadoElevador.PARADO) {
                        if (dist < distancia) {
                            distancia = dist;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if(elevadorAtual.getEstado() == Elevador.EstadoElevador.SUBINDO && elevadorAtual.getAndarAtual() < andarAtual.getNumero()){
                        if (dist <= distancia) {
                            distancia = dist;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if (elevadorAtual.getEstado() == Elevador.EstadoElevador.DESCENDO && elevadorAtual.getAndarAtual() > andarAtual.getNumero()){
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

    public int getTempoEsperaTotal() {
        return tempoEsperaTotal;
    }

    public int getMaiorTempoEspera() {
        return maiorTempoEspera;
    }

    public int getChamadasAtendidas() {
        return chamadasAtendidas;
    }
}
