
import classes.Simulador;
import estruturas.ListaDupla;
import estruturas.Random;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int DURACAO_SIMULACAO = 30;
        int VELOCIDADE_SIMULACAO_MS = 1;
        int QUANTIDADE_ANDARES = 5;
        int QUANTIDADE_ELEVADORES = 1;

        Simulador simulador = new Simulador(DURACAO_SIMULACAO,VELOCIDADE_SIMULACAO_MS,QUANTIDADE_ANDARES,QUANTIDADE_ELEVADORES);

        simulador.iniciarTemporizador();



    }
}
