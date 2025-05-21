
import classes.CentralDeControle;
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        int DURACAO_SIMULACAO = 7200; // em segundos
        int VELOCIDADE_SIMULACAO_MS = 1; // quanto maior o valor inserido, mais lento a simulação roda
        int QUANTIDADE_ANDARES = 5;
        int QUANTIDADE_ELEVADORES = 1;
        CentralDeControle.EstadoCentralDeControle estado = CentralDeControle.EstadoCentralDeControle.Felicidade;
        int tempoMovimentacaoElevador = 20; // quantidade de tempo em segundos que o elevador leva de um andar a outro
        boolean horarioPico = true; // caso seja true: quantidade de pessoas geradas é dobrada
        boolean andaresAleatorios = true; // caso seja true: as pessoas são geradas em andares aleatórios

        Simulador simulador = new Simulador(DURACAO_SIMULACAO, VELOCIDADE_SIMULACAO_MS, QUANTIDADE_ANDARES, QUANTIDADE_ELEVADORES, estado, tempoMovimentacaoElevador, horarioPico, andaresAleatorios);

        simulador.iniciar();


        // Esperar até o fim da simulação
        while (simulador.estaExecutando()) {
            try {
                Thread.sleep(100); // espera 100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        simulador.gravar("MinhaSimulação");


    }
}
