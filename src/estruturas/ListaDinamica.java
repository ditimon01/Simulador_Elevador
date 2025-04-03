package estruturas;

public class ListaDinamica<T> {

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


    public boolean remove(int pos){
        if(pos < 0 || head == null){
            return false;
        }

        if(pos == 0){
            Node<T> e = head;
            head = head.getNext();
            e.setNext(null);
            return true;
        }

        Node<T> atual = head;

        int i = 0;
        while(atual != null && i < pos-1){
            atual = atual.getNext();
            i++;
        }

        if(atual == null){
            return false;
        }

        Node<T> e = atual.getNext();
        atual.setNext(e.getNext());
        e.setNext(null);
        return true;
    }


    public void printLista(){
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


    public Node<T> getHead() {
        return head;
    }

}