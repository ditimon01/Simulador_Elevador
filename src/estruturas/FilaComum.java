package estruturas;

import java.io.Serializable;

public class FilaComum<T> implements Serializable {
    private NodeDuplo<T> head;
    private NodeDuplo<T> tail;


    public FilaComum() {
        this.head = null;
        this.tail = null;
    }

    public void enqueue(T elemento){

        NodeDuplo<T> e = new NodeDuplo<>(elemento);

        if(head != null && elemento.getClass() != this.head.getElemento().getClass()) return;

        if(head == null && tail == null){
            head = e;
            tail = e;
        }
        else{
            tail.setNext(e);
            e.setAnt(tail);
            tail = e;
        }
    }

    public T dequeue(){

        if(isEmpty()){
            return null;
        }

        NodeDuplo<T> e = head;

        if(head == tail){
            head = null;
            tail = null;
        }else{
            head = head.getNext();
            head.setAnt(null);
        }

        return e.getElemento();

    }

    public boolean contem(T elemento){
        NodeDuplo<T> atual = head;
        while(atual != null){
            if(atual.getElemento() == elemento){
                return true;
            }
            atual = atual.getNext();
        }
        return false;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public NodeDuplo<T> getTail() {
        return tail;
    }

    public NodeDuplo<T> getHead() {
        return head;
    }
}