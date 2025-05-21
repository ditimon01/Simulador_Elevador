
import classes.CentralDeControle;
import classes.Simulador;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        int DURACAO_SIMULACAO = 7200;
        int VELOCIDADE_SIMULACAO_MS = 1;
        int QUANTIDADE_ANDARES = 50;
        int QUANTIDADE_ELEVADORES = 10;
        CentralDeControle.EstadoCentralDeControle estado = CentralDeControle.EstadoCentralDeControle.Felicidade;
        int tempoMovimentacaoElevador = 20;
        boolean horarioPico = true;
        boolean andaresAleatorios = true;

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

        simulador.gravar("teste-Felicidade-5-horarioPico");

//        Simulador.carregar("teste-Economia-1");
//        Simulador.carregar("teste-Economia-2");
//        Simulador.carregar("teste-Economia-3");
//        System.out.println("============================================================");
//        Simulador.carregar("teste-Felicidade-1");
//        Simulador.carregar("teste-Felicidade-2");
//        Simulador.carregar("teste-Felicidade-3");
//        System.out.println("============================================================");
//        Simulador.carregar("teste-Normal-1");
//        Simulador.carregar("teste-Normal-2");
//        Simulador.carregar("teste-Normal-3");

    }
}
