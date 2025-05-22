package classes;

import estruturas.ListaEstatica;
import estruturas.Random;


public class Pessoa extends Serializacao {
    private final int id; //"nome" da pessoa
    private final int prioridade; //indica se é prioridade ou não
    private int contadorDeDestinos; //uma pessoa quando criada pode ter 2 ou até 4 destinos
    private int andarOrigem; //andar que a pessoa foi gerada (atualiza a ser desembarcada)
    private int andarDestino; // andar de desembarque
    private final ListaEstatica<Integer> destinos; //lista de destinos
    private int andarAtual; //andar atual que a pessoa esta
    private boolean dentroElevador; //variável para saber se a pessoa está dentro do elevador
    private int tempoAndar; // quando uma pessoa desembarca em um andar ela passa um tempo dentro do andar e depois volta para a fila de elevadores
    private int tempoEspera; //tempo de espera pelos elevadores

    //construtor da pessoa
    public Pessoa(int id, int prioridade, int origem, ListaEstatica<Integer> destino) {
        Random random = new Random();
        this.id = id;
        this.prioridade = prioridade;
        this.contadorDeDestinos = 0;
        this.andarOrigem = origem;
        this.destinos = destino;
        this.andarDestino = destinos.getElemento(contadorDeDestinos);
        this.andarAtual = origem;
        this.dentroElevador = false;
        do{this.tempoAndar = random.GeradorDeNumeroAleatorio(10);}while(tempoAndar < 5);
        this.tempoEspera = 0;
    }



    @Override
    public void atualizar(int segundosSimulados) {
        // Verifica se o passageiro está fora do elevador e se ele já chegou ao seu destino atual
        if(!dentroElevador && andarDestino == andarAtual) {
            // Decrementa o tempo de permanência no andar
            tempoAndar--;

            if(tempoAndar == 0) {
                // Incrementa o contador de destinos visitados quando o tempoAndar chega a 0
                contadorDeDestinos++;
                /**
                 *  Verificação se todos os destinos foram visitados, se sim para de atualizar aquela pessoa
                 *  considerando que o último destino de todas as pessoas sempre é o térreo (andar 0)
                */
                if(contadorDeDestinos >= destinos.getTamanho()){
                    return;
                }

                //se não for o último destino pega o próximo na lista de destino das pessoas
                andarOrigem = andarAtual;
                andarDestino = destinos.getElemento(contadorDeDestinos);
            }
        }
    }

    //função para aumentar o tempo de espera quando esperando numa fila
    public void addTempoEspera(int segundos){
        this.tempoEspera += segundos;
    }

    //getters dos dados das pessoas

    public int getId() {
        return id;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean estaDentroDoElevador() {return dentroElevador;}

    public int getTempoAndar(){
        return this.tempoAndar;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    //setter dos dados das pessoas

    public void entrarElevador() {
        tempoEspera = 0;
        this.dentroElevador = true;
    }

    public void sairElevador() {
        this.dentroElevador = false;
    }

    public void setAndarAtual(int andarAtual) {
        this.andarAtual = andarAtual;
    }
}
