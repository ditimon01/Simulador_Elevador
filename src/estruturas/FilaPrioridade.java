package estruturas;

import classes.Pessoa;

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
        if(prioridade == temp.getNext().getPrioridade()){
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
            if(head.getNext() == null){
                head = null;
                return true;
            }
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

    public boolean addElemento(int prioridade){
        if(isEmpty()){
            addPrioridade(prioridade);
            head.getFila().enqueue(prioridade);
            return true;
        }

    }

    public boolean addPessoa(int prioridade){
        Pessoa pessoa = new Pessoa(prioridade);
        if(isEmpty()){
            addPrioridade(pessoa.getPrioridade());
            head.getFila().enqueue(pessoa);
            return true;
        }else{
            NodePrior Temp = head;
           while(Temp.getNext() != null && Temp.getNext().getPrioridade() != pessoa.getPrioridade()){
                 Temp = Temp.getNext();
           }

           if(Temp.getNext() == null){
               addPrioridade(pessoa.getPrioridade);
               Temp.getFila().enqueue(pessoa);

           }else{




           }

        }

    }

    public Pessoa removePessoa(Pessoa pessoa){

    }
}
