public class FilaComum {
    private Node head;
    private Node tail;
    private boolean empty;

    public FilaComum() {
        this.head = null;
        this.tail = null;
        this.empty = true;
    }

    public void enqueue(int elemento){

        Node e = new Node(elemento);

        if(head == null && tail == null){
            head = e;
            tail = e;
        }
        else{
            e.next = head;
            head = e;
        }
        empty = false;
    }

    public Node dequeue(){



        if(empty){
            return null;
        }

        Node e = new Node(0);
        e = tail;

        if(head == tail){

            head = null;
            tail = null;
            empty = true;
            return e;
        }

        Node atual = head;

        while(atual.next != e){
            atual = atual.next;
        }

        tail = atual;
        atual.next = null;
        return e;

    }

}