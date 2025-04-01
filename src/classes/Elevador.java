package classes;

public class Elevador {

    private int numero;
    private int andarAtual;
    private PainelInterno painelInterno;

    public Elevador(int numero) {
        this.numero = numero;
        this.andarAtual = 0;
        this.painelInterno = new PainelInterno();
    }

    public void moverPara(int andar) {
        System.out.println("Elevador " + numero + " indo para o andar " + andar);
        this.andarAtual = andar;
    }
}
