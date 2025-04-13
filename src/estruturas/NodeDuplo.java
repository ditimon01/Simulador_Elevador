package estruturas;

public class NodeDuplo<T> {

    NodeDuplo<T> ant;
    T elemento;
    NodeDuplo<T> next;

    public NodeDuplo(T elemento) {
        this.elemento = elemento;
        this.ant = null;
        this.next = null;
    }

}
