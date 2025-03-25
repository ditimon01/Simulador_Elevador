public class Deque<T> {
    private NodeDuplo<T> left;
    private NodeDuplo<T> right;

    public Deque(){
        this.left = null;
        this.right = null;
    }

    public boolean isEmpty(){
        return left == null;
    }

    public void printarDeque(){
        NodeDuplo<T> atual = left;
        int cont = 0;

        System.out.println();
        while(atual != null){
            System.out.println("\t" + cont + ". " + atual.elemento);
            atual = atual.next;
            cont++;
        }
    }

    public boolean pushLeft(T elemento){

        if(elemento == null) return false;

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(isEmpty()) right = newNode;
        else{
            left.ant = newNode;
            newNode.next = left;
        }


        left = newNode;
        return true;
    }

    public boolean pushRight(T elemento){

        if(elemento == null) return false;

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(isEmpty()) this.left = newNode;
        else{
            right.next = newNode;
            newNode.ant = right;
        }
        this.right = newNode;
        return true;
    }

    public T popLeft(){
        if(isEmpty()) return null;

        T elemento = left.elemento;

        if(left == right){
            left = null;
            right = null;
            return elemento;
        }
        left = left.next;
        left.ant = null;
        return elemento;
    }

    public T popRight(){
        if(isEmpty()) return null;

        T elemento = right.elemento;

        if(left == right){
            left = null;
            right = null;
        }else {
            right = right.ant;
            right.next = null;
        }
        return elemento;
    }

    public T firstLeft(){
        return isEmpty() ? null : left.elemento;
    }

    public T firstRight(){
        return isEmpty() ? null : right.elemento;
    }

}
