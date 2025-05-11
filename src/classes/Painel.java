package classes;

import java.io.Serializable;

public class Painel implements Serializable {
    private boolean SubirAtivado;
    private boolean DescerAtivado;

    public Painel() {
        this.SubirAtivado = false;
        this.DescerAtivado = false;
    }

    public void pressionarSubir() {
        SubirAtivado = true;
    }

    public void pressionarDescer() {
        DescerAtivado = true;
    }

    public void reset() {
        this.SubirAtivado = false;
        this.DescerAtivado = false;
    }

    public boolean SubirEstaAtivado() {
        return SubirAtivado;
    }

    public boolean DescerEstaAtivado() {
        return DescerAtivado;
    }
}


