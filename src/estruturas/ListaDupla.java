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

        if(head != null && elemento.getClass() != this.head.getElemento().getClass()) return false;

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(head == null && tail == null) {
            this.head = newNode;
            this.tail = newNode;
            return true;
        }

        if(pos == 0){
            newNode.setNext(this.head);
            head.setAnt(newNode);
            head = newNode;
            return true;
        }

        NodeDuplo<T> atual = head;
        int i = 0;

        while(atual != null && i < pos-1){
            atual = atual.getNext();
            i++;
        }
        if(atual == null) return false;

        if(atual.getNext() == null){
            atual.setNext(newNode);
            newNode.setAnt(atual);
            tail = newNode;
            return true;
        }

        newNode.setAnt(atual);
        newNode.setNext(atual.getNext());
        atual.getNext().setAnt(newNode);
        atual.setNext(newNode);
        return true;

    }


    public T remove(int pos) {
        if(pos < 0) return null;

        if(head == null) return null;

        NodeDuplo<T> e;

        if(pos == 0){
            if(head == tail){
                e = head;
                head = null;
                tail = null;
                return e.getElemento();
            }
            e = head;
            head = head.getNext();
            head.setAnt(null);
            e.setNext(null);
            return e.getElemento();
        }

        NodeDuplo<T> atual = head;
        int i = 0;

        while(atual != null && i < pos){
            atual = atual.getNext();
            i++;
        }
        if(atual == null) return null;

        if(atual == tail){
            e = atual;
            tail = tail.getAnt();
            tail.setNext(null);
            e.setAnt(null);
            return e.getElemento();
        }

        atual.getAnt().setNext(atual.getNext());
        atual.getNext().setAnt(atual.getAnt());
        atual.setNext(null);
        atual.setAnt(null);
        return atual.getElemento();

    }


    public void printarLista(){
        NodeDuplo<T> atual = head;
        int i = 0;

        System.out.println();
        while(atual != null){
            System.out.println("\t" + i + ". " + atual.getElemento());
            atual = atual.getNext();
            i++;
        }
    }
}
