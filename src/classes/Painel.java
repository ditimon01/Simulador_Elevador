package classes;

import java.io.Serializable;

public class Painel implements Serializable {
    private boolean botao;
/**
* painel dos elevadores, é apenas um boolean que indica se um elevador foi chamado a um andar
*é um painel de botão simples (unico)
*/

    public Painel() {
        this.botao = false;
    }

    public void pressionar() {
        botao = true;
    }

    public void reset() {
        this.botao = false;
    }


//getter do estado do botão

    public boolean getBotao() {
        return botao;
    }
}


