
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {


        int DURACAO_SIMULACAO = 60;
        int VELOCIDADE_SIMULACAO_MS = 1000;
        int QUANTIDADE_ANDARES = 2;
        int QUANTIDADE_ELEVADORES = 1;

        Simulador simulador = new Simulador(DURACAO_SIMULACAO,VELOCIDADE_SIMULACAO_MS,QUANTIDADE_ANDARES,QUANTIDADE_ELEVADORES);

        simulador.iniciar();


    }
}
