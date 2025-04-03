package estruturas;

public class FilaComum<T> {
    private Node<T> head;
    private Node<T> tail;
    private boolean empty;

    public FilaComum() {
        this.head = null;
        this.tail = null;
        this.empty = true;
    }

    public void enqueue(T elemento){

        Node<T> e = new Node<>(elemento);

        if(head == null && tail == null){
            head = e;
            tail = e;
        }
        else{
            e.setNext(head);
            head = e;
        }
        empty = false;
    }

    public Node<T> dequeue(){

        if(empty){
            return null;
        }

        Node<T> e;
        e = tail;

        if(head == tail){

            head = null;
            tail = null;
            empty = true;
            return e;
        }

        Node<T> atual = head;

        while(atual.getNext() != e){
            atual = atual.getNext();
        }

        tail = atual;
        atual.setNext(null);
        return e;

    }

}