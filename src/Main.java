

import classes.Simulador;
import estruturas.FilaPrioridade;

public class Main {
    public static void main(String[] args) {

//        int DURACAO_SIMULACAO = 60;
//        int VELOCIDADE_SIMULACAO_MS = 1;
//        int QUANTIDADE_ANDARES = 5;
//        int QUANTIDADE_ELEVADORES = 1;
//
//        Simulador simulador = new Simulador(DURACAO_SIMULACAO,VELOCIDADE_SIMULACAO_MS,QUANTIDADE_ANDARES,QUANTIDADE_ELEVADORES);
//
//        simulador.iniciarTemporizador();

        FilaPrioridade fila = new FilaPrioridade();

        fila.addElemento(1, 1);
        fila.addElemento(2, 2);
        fila.addElemento(1, 3);
        fila.addElemento(2, 4);

        System.out.println((Integer) fila.getPrimeiroElemento());

    }
}
