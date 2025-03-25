public class Main {
    public static void main(String[] args) {

        Deque lista = new Deque<>();

        lista.pushRight(1);
        lista.pushRight(2);
        lista.pushLeft(3);
        lista.pushLeft(4);

        lista.printarDeque();

        lista.RotationRightToLeft(3);
        lista.RotationLeftToRight(3);

        lista.printarDeque();


    }
}
