
import classes.CentralDeControle;
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {


        int DURACAO_SIMULACAO = 60;
        int VELOCIDADE_SIMULACAO_MS = 10;
        int QUANTIDADE_ANDARES = 5;
        int QUANTIDADE_ELEVADORES = 2;
        CentralDeControle.EstadoCentralDeControle estado = CentralDeControle.EstadoCentralDeControle.Economia;

        Simulador simulador = new Simulador(DURACAO_SIMULACAO,VELOCIDADE_SIMULACAO_MS,QUANTIDADE_ANDARES,QUANTIDADE_ELEVADORES,estado);

        simulador.iniciar();


    }
}
