//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


//    ListaEstatica lista = new ListaEstatica(10);
//
//    lista.add(5,0);
//    lista.add(10,1);
//    lista.add(15,2);
//    lista.add(15,3);
//
//    lista.printarLista();
//
//    lista.buscarElemento(15);


        ListaDinamica lista2 = new ListaDinamica();

        System.out.println(lista2.add("bingolo",0));
        System.out.println(lista2.add(10,1));
        lista2.add(15,2);
        lista2.add(15,3);

        lista2.printarLista();

        lista2.buscarElemento("bingolo");

    }
}
