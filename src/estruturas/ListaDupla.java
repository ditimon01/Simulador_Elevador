package estruturas;

public class ListaDupla<T> {
    private NodeDuplo<T> head;
    private NodeDuplo<T> tail;

    public ListaDupla() {
        this.head = null;
        this.tail = null;
    }

    public boolean add(T elemento, int pos) {
        if(pos < 0) return false;

        if(head != null && elemento.getClass() != this.head.elemento.getClass()) return false;

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(head == null && tail == null) {
            this.head = newNode;
            this.tail = newNode;
            return true;
        }

        if(pos == 0){
            newNode.next = head;
            this.head.ant = newNode;
            this.head = newNode;
            return true;
        }

        NodeDuplo<T> atual = head;
        int i = 0;

        while(atual != null && i < pos-1){
            atual = atual.next;
            i++;
        }
        if(atual == null) return false;

        if(atual.next == null){
            atual.next = newNode;
            newNode.ant = atual;
            this.tail = newNode;
            return true;
        }

        newNode.ant = atual;
        newNode.next = atual.next;
        atual.next.ant = newNode;
        atual.next = newNode;
        return true;

    }

    public void printLista(){
        NodeDuplo<T> atual = head;
        int i = 0;

        System.out.println();
        while(atual != null){
            System.out.println("\t" + i + ". " + atual.elemento);
            atual = atual.next;
            i++;
        }
    }
}
