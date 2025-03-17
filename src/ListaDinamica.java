public class ListaDinamica<T> {
    private Node<T> head;

    public ListaDinamica(){
        this.head = null;
    }

    public boolean add(T elemento, int posicao){

        if(posicao < 0){
            return false;
        }

        if(head != null && elemento.getClass() != this.head.elemento.getClass()) return false;
        Node<T> newNode = new Node<>(elemento);



        if(posicao == 0){
            newNode.next = head;
            head = newNode;
            return true;
        }

        Node<T> atual = head;
        int i = 0;

        while(atual !=null && i < posicao-1){
            atual = atual.next;
            i++;
        }

        if(atual == null){
            return false;
        }

        newNode.next = atual.next;
        atual.next = newNode;

        return true;
    }


    public boolean remove(int posicao){
        if(posicao < 0 || head == null){
            return false;
        }

        Node<T> atual = head;

        int i = 0;
        while(atual != null && i < posicao-1){
            atual = atual.next;
            i++;
        }

        if(atual == null){
            return false;
        }

        atual.next = atual.next.next;
        return true;
    }


    public void printarLista(){
        Node<T> atual = head;
        int i = 0;

        System.out.println();
        while(atual != null){
            System.out.println("\t" + i + ". " + atual.elemento);
            atual = atual.next;
            i++;
        }
    }

    public boolean buscarElemento(T elemento){

        boolean retorno = false;

        Node<T> atual = head;
        int i = 0;

        System.out.println("\nBuscando elemento: "+elemento);

        while(atual !=null){
            if(atual.elemento == elemento){
                System.out.println("Elemento encontrado na posição " + i);
                retorno = true;
            }
            atual = atual.next;
            i++;
        }

        if(!retorno){
            System.out.println("Elemento não encontrado!");
        }

        return retorno;
    }
}
