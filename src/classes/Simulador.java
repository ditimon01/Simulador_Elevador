package classes;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Simulador implements Serializable {

    private int minutosSimulados;
    private int velocidadeMs;
    private transient  Timer temporizador;
    private boolean emExecucao;
    private Predio predio;

    public Simulador(int minutosSimulados, int velocidadeMs, int temporizador, int andares, int elevadores) {
        this.minutosSimulados = 0;
        this.velocidadeMs = velocidadeMs;
        this.predio = new Predio(andares, elevadores);
    }

    public void iniciar(){
        if(emExecucao) return;
        emExecucao = true;
        iniciarTemporizador();
        System.out.println("Simulação iniciada.");
    }


    public void pausar(){
        if(temporizador !=null ) {
            temporizador.cancel();
            emExecucao = false;
            System.out.println("Simulação pausada.");
        }
    }

    public void continuar(){
        if(!emExecucao){
             iniciarTemporizador();
             emExecucao = true;
             System.out.println("Simulação retomada.");
        }
    }

    //verificar se é a mesma coisa que pausar
    public void encerrar(){
        if(temporizador != null)
            temporizador.cancel();
            emExecucao = false;
            System.out.println("Simulação encerrada");
    }

    private void iniciarTemporizador(){
          temporizador = new Timer();
          temporizador.scheduleAtFixedRate(new TimerTask() {
              public void run() {
                  predio.atualizar(minutosSimulados++);
              }
          }, 0, velocidadeMs);
    }


    public void gravar(String nomeArquivo) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
            System.out.println("Simulação gravada com sucesso em : " + nomeArquivo);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Simulador carregar(String nomeArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Simulador sim = (Simulador) in.readObject();
            sim.continuar();
            return sim;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }












}
