package classes;

import java.io.Serializable;

public class Painel implements Serializable {
    private boolean botao;

    public Painel() {
        this.botao = false;
    }

    public void pressionar() {
        botao = true;
    }

    public void reset() {
        this.botao = false;
    }

    public boolean getBotao() {
        return botao;
    }
}


