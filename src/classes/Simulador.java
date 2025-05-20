package classes;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Simulador implements Serializable {

    private int segundosSimulados;
    private final int velocidadeMs;
    private transient  Timer temporizador;
    private boolean emExecucao;
    private Predio predio;
    private final int duracaoSimulacao;

    public Simulador(int duracaoSimulacao, int velocidadeMs, int andares, int elevadores, CentralDeControle.EstadoCentralDeControle estado) {
        this.segundosSimulados = 0;
        this.velocidadeMs = velocidadeMs;
        this.predio = new Predio(andares, elevadores, estado);
        this.duracaoSimulacao = duracaoSimulacao;
    }

    public void iniciar(){
        if(emExecucao) return;
        emExecucao = true;
        iniciarTemporizador();
        System.out.println("---------------------------------------------------------");
        System.out.println("                 SIMULAÇÃO INICIADA");
    }


    public void pausar(){
        if(temporizador != null ) {
            temporizador.cancel();
            emExecucao = false;
            System.out.println("---------------------------------------------------------");
            System.out.println("                 SIMULAÇÃO PAUSADA");
        }
    }

    public void continuar(){
        if(!emExecucao){
             iniciarTemporizador();
             emExecucao = true;
            System.out.println("---------------------------------------------------------");
             System.out.println("                 SIMULAÇÃO RETOMADA");
        }
    }

    //verificar se é o mesmo que pausar
    public void encerrar(){
        if(temporizador != null){
            temporizador.cancel();
        }
        emExecucao = false;

        int energiaGasta = predio.getCentralDeControle().getEnergiaGasta();
        int maiorTempo = predio.getCentralDeControle().getMaiorTempoEspera();
        float tempoMedio = (float) predio.getCentralDeControle().getTempoEsperaTotal() / predio.getNumeroDePessoas();
        int tempoMedioTemp = (int) tempoMedio;
        float energiaPorChamada =  predio.getCentralDeControle().getEnergiaGasta() / (float) predio.getCentralDeControle().getChamadasAtendidas();
        int chamadasAtendidas = predio.getCentralDeControle().getChamadasAtendidas();

        System.out.println("---------------------------------------------------------");
        System.out.println("                 SIMULAÇÃO ENCERRADA");
        System.out.println("---------------------------------------------------------");
        if(energiaGasta < 1000){
            System.out.println("Energia Gasta: " + energiaGasta + "W");
        }else{
            System.out.printf("Energia Gasta: %.2fkW %n", energiaGasta / 1000.0);
        }
        System.out.printf("Energia por Chamada: %.2fW %n",energiaPorChamada);
        System.out.printf("Tempo Médio de Espera: %d minutos e %.2f segundos %n" , tempoMedioTemp/60, (tempoMedio - ((tempoMedioTemp/60)*60)));
        System.out.println("Maior Tempo de Espera: " + maiorTempo/60 + " minutos e " + maiorTempo%60 + " segundos");
        System.out.println("Número de Chamadas Atendidas: " + chamadasAtendidas);
        System.out.println("---------------------------------------------------------");

    }

    public void iniciarTemporizador(){
          temporizador = new Timer();
          temporizador.scheduleAtFixedRate(new TimerTask() {
              public void run() {
                  if(segundosSimulados < duracaoSimulacao) {
                      if(segundosSimulados%20 == 0){
                          System.out.println("---------------------------------------------------------");
                          if(segundosSimulados%60 == 0){
                              System.out.println("Tempo de Simulação: " + segundosSimulados/3600 + ":" + (segundosSimulados%3600)/60 + ":00");
                          }else{
                              System.out.println("Tempo de Simulação: " + segundosSimulados/3600 + ":" + (segundosSimulados%3600)/60 + ":" + segundosSimulados%60);
                          }
                          predio.atualizar(segundosSimulados);
                      }
                      segundosSimulados++;
                  }else{
                      encerrar();
                  }
              }
          }, 0, velocidadeMs);
    }


    public void gravar(String nomeArquivo) {
        File arquivo = new File("saves", nomeArquivo);

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(this);
            System.out.println("---------------------------------------------------------");
            System.out.println("Simulação gravada com sucesso em : " + nomeArquivo);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Simulador carregar(String nomeArquivo) {
        File arquivo = new File("saves", nomeArquivo);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Simulador sim = (Simulador) in.readObject();
            sim.continuar();
            return sim;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }












}
