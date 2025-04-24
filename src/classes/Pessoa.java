package classes;

import java.io.Serializable;

public class Pessoa implements Serializable {
    private int id;
    private int prioridade;
    private int andarOrigem;
    private int andarDestino;
    private boolean dentroElevador;

    public Pessoa(int id, int prioridade, int origem, int destino) {
        this.id = id;
        this.prioridade = prioridade;
        this.andarOrigem = origem;
        this.andarDestino = destino;
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
        this.dentroElevador = true;
    }

    public void sairElevador() {
        this.dentroElevador = false;
    }
}
