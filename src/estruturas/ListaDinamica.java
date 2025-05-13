package estruturas;

import java.io.Serializable;

public class ListaDinamica<T> implements Serializable {

    private Node<T> head;

    public ListaDinamica(){
        this.head = null;
    }

    public boolean add(T elemento, int pos){

        if(pos < 0){
            return false;
        }

        if(head != null && elemento.getClass() != this.head.getElemento().getClass()) return false;
        Node<T> newNode = new Node<>(elemento);


        if(pos == 0){
            newNode.setNext(head);
            head = newNode;
            return true;
        }

        Node<T> atual = head;
        int i = 0;

        while(atual !=null && i < pos-1){
            atual = atual.getNext();
            i++;
        }

        if(atual == null){
            return false;
        }

        newNode.setNext(atual.getNext());
        atual.setNext(newNode);

        return true;
    }


    public void removePorPosicao(int pos){
        if(pos < 0 || head == null){
            return;
        }

        if(pos == 0){
            Node<T> e = head;
            head = head.getNext();
            e.setNext(null);
            return;
        }

        Node<T> atual = head;

        int i = 0;
        while(atual != null && i < pos-1){
            atual = atual.getNext();
            i++;
        }

        if(atual == null){
            return;
        }

        Node<T> e = atual.getNext();
        atual.setNext(e.getNext());
        e.setNext(null);
    }


    public void removePorElemento(T elemento){
        if(head == null){
            return;
        }

        if(head.getElemento().equals(elemento)){
            head = head.getNext();
        }

        Node<T> atual = head;

        while(atual != null && atual.getNext() != null){
            if(atual.getNext().getElemento().equals(elemento)){
                atual.setNext(atual.getNext().getNext());
            }else {
                atual = atual.getNext();
            }
        }
    }


    public void set(T elemento, int pos){
        if(pos < 0 || head == null){
            return;
        }

        Node<T> atual = head;
        int i = 0;

        while(atual != null && i < pos){
            atual = atual.getNext();
            i++;
        }
        if(atual != null){
            atual.setElemento(elemento);
        }
    }


    public void print(){
        Node<T> atual = head;
        int i = 0;

        System.out.println();
        while(atual != null){
            System.out.println("\r\t" + i + ". " + atual.getElemento());
            atual = atual.getNext();
            i++;
        }
    }

    public boolean buscarElemento(T elemento){

        boolean retorno = false;

        Node<T> atual = head;
        int i = 0;

        System.out.println("\nBuscando elemento: " + elemento);

        while(atual !=null){
            if(atual.getElemento() == elemento){
                System.out.println("Elemento encontrado na posição " + i);
                retorno = true;
            }
            atual = atual.getNext();
            i++;
        }

        if(!retorno){
            System.out.println("Elemento não encontrado!");
        }

        return retorno;
    }


    public int tamanho(){
        int tamanho = 0;
        Node<T> atual = head;
        while(atual != null){
            tamanho++;
            atual = atual.getNext();
        }
        return tamanho;
    }


    public T getElemento(int pos){
        Node<T> atual = head;
        int i = 0;
        while(atual != null && i < pos){
            atual = atual.getNext();
            i++;
        }
        if(atual == null){
            return null;
        }
        return atual.getElemento();
    }


    public boolean contem(T elemento){
        Node<T> atual = head;
        while(atual != null){
            if(atual.getElemento() == elemento){
                return true;
            }
            atual = atual.getNext();
        }
        return false;
    }


    public Node<T> getHead() {
        return head;
    }

}