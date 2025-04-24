package classes;

import java.io.Serializable;

public class Painel implements Serializable {
    private boolean botaoSubirAtivado;
    private boolean botaoDescerAtivado;

    public Painel() {
        this.botaoSubirAtivado = false;
        this.botaoDescerAtivado = false;
    }

    public void pressionarSubir() {
        botaoSubirAtivado = true;
    }

    public void pressionarDescer() {
        botaoDescerAtivado = true;
    }

    public void resetar() {
        botaoSubirAtivado = false;
        botaoDescerAtivado = false;
    }

    public boolean botaoSubirEstaAtivado() {
        return botaoSubirAtivado;
    }

    public boolean botaoDescerEstaAtivado() {
        return botaoDescerAtivado;
    }
}


