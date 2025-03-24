public class ListaDinamica<T> {

    private Node<T> head;

    public ListaDinamica(){

        this.head = null;
    }

    public boolean add(T elemento, int pos){

        if(pos < 0){
            return false;
        }

        if(head != null && elemento.getClass() != this.head.elemento.getClass()) return false;
        Node<T> newNode = new Node<>(elemento);


        if(pos == 0){
            newNode.next = head;
            head = newNode;
            return true;
        }

        Node<T> atual = head;
        int i = 0;

        while(atual !=null && i < pos-1){
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


    public boolean remove(int pos){
        if(pos < 0 || head == null){
            return false;
        }

        Node<T> atual = head;

        int i = 0;
        while(atual != null && i < pos-1){
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


    //Ordena inteiros, de forma que os pares fiquem no final da lista, mantendo a ordem relativa dos ímpares e dos pares
    public boolean OrdenarPares(){

        if(head == null || head.elemento.getClass() != Integer.class || head.next == null) return false;

        Node ParHead = null, ParTail = null;
        Node ImparHead = null, ImparTail = null;

        Node atual = head;

        while(atual != null){
            if((Integer) atual.elemento % 2 == 0){
                if(ParHead == null){
                    ParHead = atual;
                    ParTail = atual;
                }else{
                    ParTail.next = atual;
                    ParTail = atual;
                }
            }else{
                if(ImparHead == null){
                    ImparHead = atual;
                    ImparTail = atual;
                }else{
                    ImparTail.next = atual;
                    ImparTail = atual;
                }
            }
        atual = atual.next;
        }

        if (ImparHead == null) {
            return false;
        }

        if(ParHead == null){
            return false;
        }


        this.head = ImparHead;
        ImparTail.next = ParHead;
        ParTail.next = null;


        return true;
    }
}