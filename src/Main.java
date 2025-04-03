
import estruturas.ListaEstatica;

public class Main {
    public static void main(String[] args) {

        ListaEstatica lista = new ListaEstatica(5);

        System.out.println(lista.add("cachorro",0));
        System.out.println(lista.add("gato",1));

        lista.printarLista();




    }
}
