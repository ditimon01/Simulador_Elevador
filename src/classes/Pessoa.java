package classes;

public class Pessoa {

    private boolean prioridade;

    public Pessoa(boolean prioridade) {
        this.prioridade = prioridade;
    }

    public boolean temPrioridade() {
        return prioridade;
    }
}
