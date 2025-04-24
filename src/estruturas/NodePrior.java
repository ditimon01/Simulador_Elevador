package estruturas;

public class NodePrior {

    private NodePrior next;
    private int prioridade;
    private FilaComum fila;

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

    public FilaComum getFila() { return fila; }


}
