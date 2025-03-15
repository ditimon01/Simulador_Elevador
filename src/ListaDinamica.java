public class ListaDinamica {
    private Node head;

    public ListaDinamica(){
        this.head = null;
    }

    public boolean add(int elemento, int posicao){

        if(posicao < 0){
            return false;
        }

        Node newNode = new Node(elemento);

        if(posicao == 0){
            newNode.next = head;
            head = newNode;
            return true;
        }

        Node atual = head;
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

        Node atual = head;

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
        Node atual = head;
        int i = 0;

        while(atual != null){
            System.out.println("\t" + i + ". " + atual.elemento);
            atual = atual.next;
            i++;
        }
    }

    public boolean buscarElemento(int elemento){
        boolean retorno = false;
        Node atual = head;
        int i = 0;

        System.out.println("\nBuscando elemento "+elemento);

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
