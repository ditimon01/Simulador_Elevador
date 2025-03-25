public class Main {
    public static void main(String[] args) {

        Deque<String> deque = new Deque<>();

        deque.pushLeft("A");
        deque.pushRight("B");
        deque.pushLeft("C");

        deque.printDeque(); // [C, A, B]

        deque.rotationRightToLeft(2);

        deque.printDeque(); // [A, B, C]


    }
}
