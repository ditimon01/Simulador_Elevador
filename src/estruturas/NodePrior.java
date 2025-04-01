package estruturas;

import classes.Pessoa;

public class NodePrior {

    private NodePrior next;
    private int prioridade;
    private FilaComum<Pessoa> fila;

    public NodePrior(int prioridade){
        this.prioridade = prioridade;
        this.next = null;
        this.fila = new FilaComum();
    }

    public NodePrior getNext() {
        return next;
    }

    public void setNext(NodePrior next) {
        this.next = next;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public FilaComum<Pessoa> getFila() {
        return fila;
    }

    public void setFila(FilaComum<Pessoa> fila) {
        this.fila = fila;
    }

}
