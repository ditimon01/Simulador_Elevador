package estruturas;

import java.io.Serializable;

public class Random implements Serializable {

    private long NovaSeed;

    public Random() {
        this.NovaSeed = System.currentTimeMillis(); // atribui o valor do milissegundo atual em forma de número
    }

    public int GeradorDeNumeroAleatorio(int escopo) {

        if(escopo <= 0){
            throw new IllegalArgumentException("Escopo deve ser maior que zero");
        }
        NovaSeed =(NovaSeed * 6364136223846793005L + 1) & Long.MAX_VALUE; //usa a operação AND com o maior "Long" possível, para garantir que o número seja positivo
        return (int)(NovaSeed % escopo); //utiliza o resto da divisão pelo escopo para garantir que o número seja menor que o escopo
    }

}
