
import classes.CentralDeControle;
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {


        CentralDeControle.EstadoCentralDeControle estado = CentralDeControle.EstadoCentralDeControle.Felicidade; // Normal, Economia, Felicidade
        int DURACAO_SIMULACAO = 7200; // em segundos
        int VELOCIDADE_SIMULACAO_MS = 1; // quanto maior o valor inserido, mais lento a simulação roda, a simulação não aceita uma entrada menor que 1
        int QUANTIDADE_ANDARES = 5; // quantidade de andares da simulação, a simulação não aceita uma quantia menor que 5
        int QUANTIDADE_ELEVADORES = 1; // quantidade de elevadores da simulação, a simulação não aceita uma quantia menor que 1
        int tempoMovimentacaoElevador = 1; // quantidade de tempo em segundos que o elevador leva de um andar a outro
        int energiaDeslocamento = 2; // energia gasta a cada deslocamento de elevador, a simulação não aceita uma entrada menor que 0
        int energiaParada = 1; // energia gasta a cada parada de elevador, a s simulação não aceita uma entrada menor que 0
        boolean horarioPico = true; // caso seja true: quantidade de pessoas geradas é dobrada
        boolean andaresAleatorios = true; // caso seja true: as pessoas são geradas em andares aleatórios


        Simulador simulador = new Simulador(estado, DURACAO_SIMULACAO, VELOCIDADE_SIMULACAO_MS, QUANTIDADE_ANDARES, QUANTIDADE_ELEVADORES, tempoMovimentacaoElevador, energiaDeslocamento, energiaParada, horarioPico, andaresAleatorios);

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
