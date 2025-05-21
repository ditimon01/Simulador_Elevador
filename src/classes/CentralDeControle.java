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
    private int energiaDeslocamento;
    private int energiaParada;

    public enum EstadoCentralDeControle{
        Economia,
        Felicidade,
        Normal;
    }

    public CentralDeControle(int numeroElevadores, Predio predio, EstadoCentralDeControle estado, int energiaDeslocamento, int energiaParada ) {
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
        this.energiaDeslocamento = energiaDeslocamento;
        this.energiaParada = energiaParada;
    }

    @Override
    public void atualizar(int segundosSimulados) {

        //percorre a lista de elevadores
        for (int i = 0; i < elevadores.getTamanho(); i++) {
            Elevador elevador = elevadores.getElemento(i);
            int andar_anterior = elevador.getAndarAtual();
            Elevador.EstadoElevador estado_anterior = elevador.getEstado();


            //escolha baseado na heurística escolhida
            if(estado == EstadoCentralDeControle.Economia){
                verificaChamadasEconomia();
            }else if(estado == EstadoCentralDeControle.Felicidade){
                verificaChamadasFelicidade();
            }else if(estado == EstadoCentralDeControle.Normal){
                verificarChamadasNormal();
            }

            elevador.atualizar(segundosSimulados);

            //adiciona as pessoas que vão desembarcar na lista PessoasSaida
            elevador.desembarquePessoas();
            // adiciona as pessoas quando o elevador já está no andar
            receberPessoas(elevador);

            //cálculos de energia total gasta e maior energia gasta
            if(andar_anterior != elevador.getAndarAtual()){ energiaGasta+=energiaDeslocamento;}
            if(estado_anterior != elevador.getEstado()){ energiaGasta+=energiaParada;}


            Node<Pessoa> atual = elevador.getPessoasSaida().getHead();
            // retira as pessoas que vão sair do elevador da lista PessoasSaida
            while (atual != null) {
                Pessoa p = atual.getElemento();
                predio.getAndares().getElemento(elevador.getAndarAtual()).adicionarPessoaAndar(p);
                elevador.getPessoasSaida().removePorElemento(p);
                atual = elevador.getPessoasSaida().getHead();
            }


            Node<Pessoa> atual2 = elevador.getPessoasDentro().getHead();
            //atualiza o andarAtual de cada pessoa
            while (atual2 != null) {
                Pessoa p = atual2.getElemento();
                p.setAndarAtual(elevador.getAndarAtual());
                atual2 = atual2.getNext();
            }

        }
    }


    private void receberPessoas(Elevador elevador) {

        if (elevador.getEstado() == Elevador.EstadoElevador.PARADO) {
            Andar andar = predio.getAndares().getElemento(elevador.getAndarAtual());
            //enquanto houver espaço no elevador, remove a pessoa da fila de espera do andar, e adiciona ao elevador
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

            //remoção do destino de parada para evitar loop
            if (!andar.temChamada() || elevador.getPessoasDentro().tamanho() == Elevador.getCapacidadeMaxima()) {
                elevador.removeDestino(andar.getNumero());
            }
        }

        elevador.atualizarDestino();
    }

    public void verificarChamadasNormal(){

        ListaEstatica<Andar> andares = predio.getAndares();
        Elevador elevadorEnviado = null;
        int QuantiaDestinos = Integer.MAX_VALUE;

        //normal apenas manda para chamada do elevador mais desocupado (com menos chamadas)

        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            andarAtual.verificaPessoas();
            if (andarAtual.temChamada()) {
                Elevador elevadorAtual;
                for (int e = 0; e < elevadores.getTamanho(); e++) {
                    elevadorAtual = elevadores.getElemento(e);
                    if(elevadorAtual.getPessoasDentro().tamanho() == Elevador.getCapacidadeMaxima()){
                        continue;
                    }
                    if(elevadorAtual.getAndarAtual() == andarAtual.getNumero()){
                        elevadorEnviado = null;
                        break;
                    }
                    if(elevadorAtual.getDestinos().tamanho() < QuantiaDestinos){
                        elevadorEnviado = elevadorAtual;
                        QuantiaDestinos = elevadorAtual.getDestinos().tamanho();
                    }
                }

                if(elevadorEnviado != null){
                    elevadorEnviado.addDestino(andarAtual.getNumero());
                    elevadorEnviado.atualizarDestino();
                }
            }
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


    //
    public void verificaChamadasFelicidade() {
        ListaEstatica<Andar> andares = predio.getAndares();
        int menorTempo = andares.getTamanho() + 1;
        Elevador elevadorEnviado = null;

        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            andarAtual.verificaPessoas();
            if (andarAtual.temChamada()) {

                Elevador elevadorAtual;
                int tempo;
                for (int e = 0; e < elevadores.getTamanho(); e++) {

                    elevadorAtual = elevadores.getElemento(e);
                    tempo = Math.abs(elevadorAtual.getAndarAtual() - andarAtual.getNumero());

                    if(elevadorAtual.getPessoasDentro().tamanho() == Elevador.getCapacidadeMaxima()){
                        continue;
                    }

                    if(elevadorAtual.getAndarAtual() == andarAtual.getNumero()){
                        elevadorEnviado = null;
                        break;
                    }

                    Node<Integer> andarDestinoAtual = elevadorAtual.getDestinos().getHead();

                    if(elevadorAtual.getEstado() == Elevador.EstadoElevador.PARADO){
                        tempo++;
                    }

                    if(andarDestinoAtual != null && andarAtual.getNumero() > andarDestinoAtual.getElemento()){
                        andarDestinoAtual = andarDestinoAtual.getNext();
                        while(andarDestinoAtual != null){
                            if(andarAtual.getNumero() > andarDestinoAtual.getElemento()){
                                tempo++;
                            }
                            andarDestinoAtual = andarDestinoAtual.getNext();
                        }
                    }else if(andarDestinoAtual != null && andarAtual.getNumero() < andarDestinoAtual.getElemento()){
                        andarDestinoAtual = andarDestinoAtual.getNext();
                        while(andarDestinoAtual != null){
                            if(andarAtual.getNumero() < andarDestinoAtual.getElemento()){
                                tempo++;
                            }
                            andarDestinoAtual = andarDestinoAtual.getNext();
                        }
                    }

                    if (elevadorAtual.getEstado() == Elevador.EstadoElevador.PARADO) {
                        if (tempo < menorTempo) {
                            menorTempo = tempo;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if(elevadorAtual.getEstado() == Elevador.EstadoElevador.SUBINDO && elevadorAtual.getAndarAtual() < andarAtual.getNumero()){
                        if (tempo <= menorTempo) {
                            menorTempo = tempo;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if (elevadorAtual.getEstado() == Elevador.EstadoElevador.DESCENDO && elevadorAtual.getAndarAtual() > andarAtual.getNumero()){
                        if (tempo <= menorTempo) {
                            menorTempo = tempo;
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
