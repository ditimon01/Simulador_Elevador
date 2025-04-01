package classes;

class PainelExterno extends Painel {

    private boolean botaoPressionado;

    @Override
    void pressionarBotao(int andar) {
        botaoPressionado = true;
    }
}
