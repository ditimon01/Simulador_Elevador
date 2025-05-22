package classes;

import estruturas.ListaEstatica;
import estruturas.Node;

/**
 * Classe que representa a central de controle dos elevadores de um prédio.
 * Gerencia os elevadores, define a estratégia de operação (Economia, Felicidade ou Normal)
 * e calcula estatísticas como energia gasta e tempo de espera.
 */
public class CentralDeControle extends Serializacao {
    // Lista de elevadores controlados
    private ListaEstatica<Elevador> elevadores;

    // Referência ao prédio onde a central está operando (não serializável)
    private transient Predio predio;

    // Estratégia de controle adotada
    private final EstadoCentralDeControle estado;

    // Dados estatísticos
    private int energiaGasta;
    private int tempoEsperaTotal;
    private int maiorTempoEspera;
    private int chamadasAtendidas;

    // Parâmetros de consumo de energia
    private int energiaDeslocamento;
    private int energiaParada;

    /**
     * Enumeração dos estados de operação da central:
     * Economia - prioriza menor gasto de energia;
     * Felicidade - prioriza menor tempo de espera;
     * Normal - balanceado, prioriza elevadores menos ocupados.
     */
    public enum EstadoCentralDeControle{
        Economia,
        Felicidade,
        Normal
    }

    /**
     * Construtor da Central de Controle
     */
    public CentralDeControle(int numeroElevadores, int capacidadeElevador, Predio predio, EstadoCentralDeControle estado, int energiaDeslocamento, int energiaParada ) {
        this.elevadores = new ListaEstatica<>(numeroElevadores);
        this.predio = predio;

        // Cria os elevadores
        for (int i = 0; i < numeroElevadores; i++) {
            elevadores.add(new Elevador(i, capacidadeElevador), i);
        }

        this.estado = estado;
        this.energiaDeslocamento = energiaDeslocamento;
        this.energiaParada = energiaParada;

        // Inicializa os dados estatísticos
        this.energiaGasta = 0;
        tempoEsperaTotal = 0;
        maiorTempoEspera = 0;
        chamadasAtendidas = 0;

    }

    /**
     * Atualiza o estado de todos os elevadores conforme o tempo simulado
     */
    @Override
    public void atualizar(int segundosSimulados) {

        //percorre a lista de elevadores
        for (int i = 0; i < elevadores.getTamanho(); i++) {
            Elevador elevador = elevadores.getElemento(i);
            int andar_anterior = elevador.getAndarAtual();
            Elevador.EstadoElevador estado_anterior = elevador.getEstado();


            // Seleciona a estratégia de operação
            if(estado == EstadoCentralDeControle.Economia){
                verificaChamadasEconomia();
            }else if(estado == EstadoCentralDeControle.Felicidade){
                verificaChamadasFelicidade();
            }else if(estado == EstadoCentralDeControle.Normal){
                verificarChamadasNormal();
            }

            // Atualiza o estado do elevador
            elevador.atualizar(segundosSimulados);

            // Desembarca passageiros (retira as pessoas da lista do elevador e adiciona as pessoas que vão desembarcar na lista PessoasSaida)
            elevador.desembarquePessoas();

            // Recebe passageiros do andar
            receberPessoas(elevador);

            //cálculos de energia total gasta e maior energia gasta
            if(andar_anterior != elevador.getAndarAtual()){ energiaGasta+=energiaDeslocamento;}
            if(estado_anterior != elevador.getEstado()){ energiaGasta+=energiaParada;}


            Node<Pessoa> atual = elevador.getPessoasSaida().getHead();
            // Processa desembarque de pessoas no andar (retira as pessoas que vão sair do elevador da lista PessoasSaida)
            while (atual != null) {
                Pessoa p = atual.getElemento();
                predio.getAndares().getElemento(elevador.getAndarAtual()).adicionarPessoaAndar(p);
                elevador.getPessoasSaida().removePorElemento(p);
                atual = elevador.getPessoasSaida().getHead();
            }

            // Atualiza o andar atual das pessoas dentro do elevador
            Node<Pessoa> atual2 = elevador.getPessoasDentro().getHead();
            while (atual2 != null) {
                Pessoa p = atual2.getElemento();
                p.setAndarAtual(elevador.getAndarAtual());
                atual2 = atual2.getNext();
            }

        }
    }

    /**
     * Método que permite que o elevador receba pessoas do andar atual
     */
    private void receberPessoas(Elevador elevador) {

        if (elevador.getEstado() == Elevador.EstadoElevador.PARADO) {
            Andar andar = predio.getAndares().getElemento(elevador.getAndarAtual());
            //enquanto houver espaço no elevador, remove a pessoa da fila de espera do andar, e adiciona ao elevador
            while (elevador.getPessoasDentro().tamanho() < elevador.getCapacidadeMaxima() && !andar.isEmpty()) {
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

            // Remove o destino do elevador se não há mais pessoas no andar ou caso o elevador estiver lotado
            if (!andar.temChamada() || elevador.getPessoasDentro().tamanho() == elevador.getCapacidadeMaxima()) {
                elevador.removeDestino(andar.getNumero());
            }
        }

        elevador.atualizarDestino();
    }

    /**
     * Estratégia "Normal": envia o elevador com menos destinos ativos
     */
    public void verificarChamadasNormal(){

        ListaEstatica<Andar> andares = predio.getAndares();
        Elevador elevadorEnviado = null;
        int QuantiaDestinos = Integer.MAX_VALUE;

        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            andarAtual.verificaPessoas();
            if (andarAtual.temChamada()) {
                Elevador elevadorAtual;
                for (int e = 0; e < elevadores.getTamanho(); e++) {
                    elevadorAtual = elevadores.getElemento(e);
                    if(elevadorAtual.getPessoasDentro().tamanho() == elevadorAtual.getCapacidadeMaxima()){
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

    /**
     * Estratégia "Economia": escolhe o elevador que gastará menos energia
     */
    public void verificaChamadasEconomia() {
        ListaEstatica<Andar> andares = predio.getAndares();
        int menorEnergiaGasta = Integer.MAX_VALUE;
        Elevador elevadorEnviado = null;

        for (int i = 0; i < andares.getTamanho(); i++) {
            Andar andarAtual = andares.getElemento(i);
            andarAtual.verificaPessoas();
            if (andarAtual.temChamada()) {
                Elevador elevadorAtual;
                int energiaGastaAtual;
                for (int e = 0; e < elevadores.getTamanho(); e++) {
                    elevadorAtual = elevadores.getElemento(e);
                    energiaGastaAtual = (Math.abs(andarAtual.getNumero() - elevadorAtual.getAndarAtual()))*energiaDeslocamento;

                    if(elevadorAtual.getPessoasDentro().tamanho() == elevadorAtual.getCapacidadeMaxima()){
                        continue;
                    }

                    if(elevadorAtual.getAndarAtual() == andarAtual.getNumero()){
                        elevadorEnviado = null;
                        break;
                    }


                    // Calcula energia adicional com base nos destinos atuais do elevador
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

                    // Verifica o menor gasto de energia
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

                // Atribui o elevador com menor gasto a chamada
                if(elevadorEnviado != null){
                    elevadorEnviado.addDestino(andarAtual.getNumero());
                    elevadorEnviado.atualizarDestino();
                }
            }
        }
    }


    /**
     * Estratégia "Felicidade": escolhe o elevador que chegará mais rápido
     */
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

                    if(elevadorAtual.getPessoasDentro().tamanho() == elevadorAtual.getCapacidadeMaxima()){
                        continue;
                    }

                    if(elevadorAtual.getAndarAtual() == andarAtual.getNumero()){
                        elevadorEnviado = null;
                        break;
                    }

                    // Calcula tempo adicional com base nos destinos atuais do elevador
                    Node<Integer> andarDestinoAtual = elevadorAtual.getDestinos().getHead();


                    if(andarDestinoAtual != null && andarAtual.getNumero() > andarDestinoAtual.getElemento()){
                        while(andarDestinoAtual != null){
                            if(andarAtual.getNumero() > andarDestinoAtual.getElemento()){
                                tempo++;
                            }
                            andarDestinoAtual = andarDestinoAtual.getNext();
                        }
                    }else if(andarDestinoAtual != null && andarAtual.getNumero() < andarDestinoAtual.getElemento()){
                        while(andarDestinoAtual != null){
                            if(andarAtual.getNumero() < andarDestinoAtual.getElemento()){
                                tempo++;
                            }
                            andarDestinoAtual = andarDestinoAtual.getNext();
                        }
                    }

                    // Verifica o elevador com menor tempo de chegada ao destino
                    if (elevadorAtual.getEstado() == Elevador.EstadoElevador.PARADO) {
                        if (tempo < menorTempo) {
                            menorTempo = tempo;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if(elevadorAtual.getEstado() == Elevador.EstadoElevador.SUBINDO && elevadorAtual.getAndarAtual() < andarAtual.getNumero()){
                        if (tempo < menorTempo) {
                            menorTempo = tempo;
                            elevadorEnviado = elevadorAtual;
                        }
                    } else if (elevadorAtual.getEstado() == Elevador.EstadoElevador.DESCENDO && elevadorAtual.getAndarAtual() > andarAtual.getNumero()){
                        if (tempo < menorTempo) {
                            menorTempo = tempo;
                            elevadorEnviado = elevadorAtual;
                        }
                    }
                }

                // Atribui o elevador com menor tempo de chegada a chamada
                if(elevadorEnviado != null) {
                    elevadorEnviado.addDestino(andarAtual.getNumero());
                    elevadorEnviado.atualizarDestino();
                }

            }
        }
    }

    // Getters dos dados estatísticos
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

    public EstadoCentralDeControle getEstado() {
        return estado;
    }
}
