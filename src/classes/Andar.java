package classes;

import estruturas.FilaComum;
import estruturas.ListaDinamica;

public class Andar {

    private int numero;
    private ListaDinamica<Pessoa> lista;

    public Andar(int numero){
        this.numero = numero;
        lista = new ListaDinamica<>(); //trocar por fila de prioridade
    }

    public int getNumero() {
        return numero;
    }

    public void adicionar(Pessoa p) {
        //adicionar baseado na prioridade da pessoa
    }

    //criar getFilaPrioridade
}
