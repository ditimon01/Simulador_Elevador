package estruturas;

public class NodeDuplo<T> {

    private NodeDuplo<T> ant;
    private T elemento;
    private NodeDuplo<T> next;

    public NodeDuplo(T elemento) {
        this.elemento = elemento;
        this.ant = null;
        this.next = null;
    }


    public NodeDuplo<T> getNext() {
        return next;
    }

    public void setNext(NodeDuplo<T> next) {
        this.next = next;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public NodeDuplo<T> getAnt() {
        return ant;
    }

    public void setAnt(NodeDuplo<T> ant) {
        this.ant = ant;
    }

}
