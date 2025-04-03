package classes;

import estruturas.ListaEstatica;

public class CentralDeControle {
    private ListaEstatica<Elevador> elevadores;

    public CentralDeControle(int numeroElevadores) {
        this.elevadores = new ListaEstatica<Elevador>(numeroElevadores);
        for (int i = 0; i < numeroElevadores; i++) {
            elevadores.add(new Elevador(i), i);
        }
    }


}
