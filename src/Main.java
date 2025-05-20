
import classes.CentralDeControle;
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        int DURACAO_SIMULACAO = 3600;
        int VELOCIDADE_SIMULACAO_MS = 10;
        int QUANTIDADE_ANDARES = 15;
        int QUANTIDADE_ELEVADORES = 5;
        CentralDeControle.EstadoCentralDeControle estado = CentralDeControle.EstadoCentralDeControle.Felicidade;
        int tempoMovimentacaoElevador = 20;
        boolean horarioPico = true;

        Simulador simulador = new Simulador(DURACAO_SIMULACAO, VELOCIDADE_SIMULACAO_MS, QUANTIDADE_ANDARES, QUANTIDADE_ELEVADORES, estado, tempoMovimentacaoElevador, horarioPico);

        simulador.iniciar();


        // Esperar até o fim da simulação
        while (simulador.estaExecutando()) {
            try {
                Thread.sleep(100); // espera 100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        simulador.gravar("euSEKEFFO");


    }
}
