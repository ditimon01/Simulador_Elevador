package estruturas;

import java.io.Serializable;

public class Deque<T> implements Serializable {
    private NodeDuplo<T> left;
    private NodeDuplo<T> right;
    private int size;

    public Deque(){
        this.left = null;
        this.right = null;
        this.size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void printDeque(){
        NodeDuplo<T> atual = left;
        int cont = 0;

        System.out.println();
        while(atual != null){
            System.out.println("\t" + cont + ". " + atual.getElemento());
            atual = atual.getNext();
            cont++;
        }
    }

    public void pushLeft(T elemento){

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(isEmpty()) right = newNode;
        else{
            left.setAnt(newNode);
            newNode.setNext(left);
        }
        size++;
        left = newNode;
    }

    public void pushRight(T elemento){

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(isEmpty()) this.left = newNode;
        else{
            right.setNext(newNode);
            newNode.setAnt(right);

        }
        size++;
        this.right = newNode;
    }

    public T popLeft(){
        if(isEmpty()) return null;

        T elemento = left.getElemento();

        if(left == right){
            left = null;
            right = null;
            return elemento;
        }
        left = left.getNext();
        left.setAnt(null);
        size--;
        return elemento;
    }

    public T popRight(){
        if(isEmpty()) return null;

        T elemento = right.getElemento();

        if(left == right){
            left = null;
            right = null;
        }else {
            right = right.getAnt();
            right.setNext(null);
        }
        size--;
        return elemento;
    }

    public T firstLeft(){
        return isEmpty() ? null : left.getElemento();
    }

    public T firstRight(){
        return isEmpty() ? null : right.getElemento();
    }


















    public void leftToRight(){
        if(isEmpty()) return;
        if(left == right) return;
        left.setAnt(right);
        right.setNext(left);
        left = left.getNext();
        left.setAnt(null);
        right = right.getNext();
        right.setNext(null);
    }



    public void rightToLeft(){
        if(isEmpty()) return;
        if(left == right) return;
        left.setAnt(right);
        right.setNext(left);
        right = right.getAnt();
        right.setNext(null);
        left = left.getAnt();
        left.setAnt(null);
    }

    public void rotationRightToLeft(int quantidade){
        if(isEmpty() || quantidade <= 0) return;
        for(int i = 0; i < quantidade%size; i++){
            rightToLeft();
        }
    }

    public void rotationLeftToRight(int quantidade){
        if(isEmpty() || quantidade <= 0) return;
        for(int i = 0; i < quantidade%size; i++){
            leftToRight();
        }
    }

}
