package estruturas;

public class FilaPrioridade {

    NodePrior head;

    public FilaPrioridade(){
        this.head = null;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public boolean addPrioridade(int prioridade){

        NodePrior newNode = new NodePrior(prioridade);

        if(isEmpty()){
            head = newNode;
            return true;
        }

        if(prioridade > head.getPrioridade()){
            newNode.setNext(head);
            head = newNode;
            return true;
        }

        NodePrior temp = head;
        while(temp.getNext() != null && temp.getNext().getPrioridade() > prioridade){
            temp = temp.getNext();
        }
        if(temp.getNext() != null && prioridade == temp.getNext().getPrioridade()){
            return false;
        }
        newNode.setNext(temp.getNext());
        temp.setNext(newNode);
        return true;
    }

    public boolean removePrioridade(int prioridade){

        if(isEmpty()){
            return false;
        }

        if(prioridade > head.getPrioridade()){
            return false;
        }

        if(prioridade == head.getPrioridade()){
            head = head.getNext();
            return true;
        }

        NodePrior temp = head;
        while(temp.getNext() != null && temp.getNext().getPrioridade() != prioridade){
            temp = temp.getNext();
        }

        if(temp.getNext() == null){
            return false;
        }

        temp.setNext(temp.getNext().getNext());
        return true;
    }

    public <T> boolean addElemento(int prioridade, T elemento){
        NodePrior temp = head;

        while(temp != null && temp.getPrioridade() != prioridade){
            temp = temp.getNext();
        }

        if(temp == null){
            addPrioridade(prioridade);
            temp = head;
            while(temp.getPrioridade() != prioridade){
                temp = temp.getNext();
            }
        }
        temp.getFila().enqueue(elemento);
        return true;
    }

    public <T> T removeElemento(){
        if(isEmpty()){
            return null;
        }
        NodePrior temp = head;
        Node<T> NodeRemovido = null;

        while(temp != null){
            NodeRemovido = temp.getFila().dequeue();
            if(NodeRemovido != null){
                break;
            }
            temp = temp.getNext();
        }

        if(NodeRemovido == null){
            return null;
        }

        return NodeRemovido.getElemento();
    }

}
