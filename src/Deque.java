public class Deque<T> {
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
            System.out.println("\t" + cont + ". " + atual.elemento);
            atual = atual.next;
            cont++;
        }
    }

    public void pushLeft(T elemento){

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(isEmpty()) right = newNode;
        else{
            left.ant = newNode;
            newNode.next = left;
        }
        size++;
        left = newNode;
    }

    public void pushRight(T elemento){

        NodeDuplo<T> newNode = new NodeDuplo<>(elemento);

        if(isEmpty()) this.left = newNode;
        else{
            right.next = newNode;
            newNode.ant = right;
        }
        size++;
        this.right = newNode;
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
        size--;
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
        size--;
        return elemento;
    }

    public T firstLeft(){
        return isEmpty() ? null : left.elemento;
    }

    public T firstRight(){
        return isEmpty() ? null : right.elemento;
    }

    public void leftToRight(){
        if(isEmpty()) return;
        if(left == right) return;
        left.ant = right;
        right.next = left;
        left = left.next;
        left.ant = null;
        right = right.next;
        right.next = null;
    }

    public void rightToLeft(){
        if(isEmpty()) return;
        if(left == right) return;
        left.ant = right;
        right.next = left;
        right = right.ant;
        right.next = null;
        left = left.ant;
        left.ant = null;
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
