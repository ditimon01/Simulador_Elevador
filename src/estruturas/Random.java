package estruturas;

public class Random {

    private long NovaSeed = System.currentTimeMillis();;

    public int GeradorDeNumeroAleatorio(int escopo) {

        NovaSeed =(NovaSeed * 6364589395839590L + 1) % Long.MAX_VALUE;
        return (int)(NovaSeed % escopo);
    }

}
