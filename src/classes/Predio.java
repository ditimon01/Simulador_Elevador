package classes;

import estruturas.ListaEstatica;

public class Predio {

    private ListaEstatica<Andar> andares;
    private CentralDeControle centralDeControle;


 public Predio(int numeroAndares, int numeroElevadores){
     this.andares = new ListaEstatica(numeroAndares);
     for (int i = 0; i < numeroAndares; i++){
         andares.add(new Andar(i),i);
     }
     this.centralDeControle = new CentralDeControle(numeroElevadores);

 }
}
