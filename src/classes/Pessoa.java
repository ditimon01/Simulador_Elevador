package classes;

import estruturas.ListaEstatica;
import estruturas.Random;


public class Pessoa extends Serializacao {
    private final int id;
    private final int prioridade;
    private int contadorDeDestinos;
    private int andarOrigem;
    private int andarDestino;
    private final ListaEstatica<Integer> destinos;
    private int andarAtual;
    private boolean dentroElevador;
    private int tempoAndar;
    private int tempoEspera;

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

        if(!dentroElevador && andarDestino == andarAtual) {
            tempoAndar--;

            if(tempoAndar == 0) {
                contadorDeDestinos++;
                if(contadorDeDestinos >= destinos.getTamanho()){
                    return;
                }
                andarOrigem = andarAtual;
                andarDestino = destinos.getElemento(contadorDeDestinos);
            }
        }
    }

    public void addTempoEspera(int segundos){
        this.tempoEspera += segundos;
    }

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

    public boolean estaDentroDoElevador() {
        return dentroElevador;
    }

    public void entrarElevador() {
        tempoEspera = 0;
        this.dentroElevador = true;
    }

    public void sairElevador() {
        this.dentroElevador = false;
    }

    public int getTempoAndar(){
        return this.tempoAndar;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setAndarAtual(int andarAtual) {
        this.andarAtual = andarAtual;
    }
}
