public class Node<T> {
    T elemento;
    Node<T> next;

    public Node(T elemento) {
        this.elemento = elemento;
        this.next = null;
    }

}