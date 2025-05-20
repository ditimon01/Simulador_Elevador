
import classes.CentralDeControle;
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {


        int DURACAO_SIMULACAO = 3600;
        int VELOCIDADE_SIMULACAO_MS = 1;
        int QUANTIDADE_ANDARES = 15;
        int QUANTIDADE_ELEVADORES = 5;
        CentralDeControle.EstadoCentralDeControle estado = CentralDeControle.EstadoCentralDeControle.Felicidade;
        int tempoMovimentacaoElevador = 20;
        boolean horarioPico = true;

        Simulador simulador = new Simulador(DURACAO_SIMULACAO, VELOCIDADE_SIMULACAO_MS, QUANTIDADE_ANDARES, QUANTIDADE_ELEVADORES, estado, tempoMovimentacaoElevador, horarioPico);

        simulador.iniciar();


    }
}
