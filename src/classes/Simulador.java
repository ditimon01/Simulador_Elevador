package classes;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Simulador implements Serializable {

    private int minutosSimulados;
    private final int velocidadeMs;
    private transient  Timer temporizador;
    private boolean emExecucao;
    private Predio predio;
    private final int duracaoSimulacao;

    public Simulador(int duracaoSimulacao, int velocidadeMs, int andares, int elevadores, CentralDeControle.EstadoCentralDeControle estado) {
        this.minutosSimulados = 0;
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
        if(temporizador !=null ) {
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
        if(temporizador != null)
            temporizador.cancel();
            emExecucao = false;
            System.out.println("---------------------------------------------------------");
            System.out.println("                 SIMULAÇÃO ENCERRADA");
    }

    public void iniciarTemporizador(){
          temporizador = new Timer();
          temporizador.scheduleAtFixedRate(new TimerTask() {
              public void run() {
                  if(minutosSimulados < duracaoSimulacao) {
                      System.out.println("---------------------------------------------------------");
                      System.out.println("Minutos simulados: " + minutosSimulados);
                      predio.atualizar(minutosSimulados++);
                  }else{
                      encerrar();
                      System.out.println("---------------------------------------------------------");
                      System.out.println("Energia Gasta: " + predio.getCentralDeControle().getEnergiaGasta() * 2 + "W");

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
