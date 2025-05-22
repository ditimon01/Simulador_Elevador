package classes;


import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe responsável por controlar a simulação do prédio com elevadores.
 * Implementa Serializable para permitir salvar e carregar o estado da simulação.
 */
public class Simulador implements Serializable {
    // Tempo simulado em segundos.
    private int segundosSimulados;

    // Velocidade da simulação em milissegundos (intervalo entre ticks).
    private final int velocidadeMs;

    // Temporizador usado para controlar a execução (não serializável).
    private transient  Timer temporizador;

    // Controle se a simulação está em execução.
    private boolean emExecucao;

    // Instância do prédio que está sendo simulado.
    private Predio predio;

    // Duração total da simulação em segundos.
    private final int duracaoSimulacao;

    // Tempo que leva para um elevador se movimentar entre andares (em segundos).
    private int tempoMovimentoElevador;

    /**
     * Construtor do Simulador.
     */
    public Simulador(CentralDeControle.EstadoCentralDeControle estado, int duracaoSimulacao, int velocidadeMs, int andares, int elevadores, int capacidadeElevador, int tempoMovimentoElevador, int energiaDeslocamento, int energiaParada, boolean horarioPico, boolean andaresAleatorios) {
        this.segundosSimulados = 0;
        this.velocidadeMs = velocidadeMs;
        this.predio = new Predio(andares, elevadores, capacidadeElevador, estado, tempoMovimentoElevador, horarioPico, andaresAleatorios, energiaDeslocamento, energiaParada);
        this.duracaoSimulacao = duracaoSimulacao;
        this.tempoMovimentoElevador = tempoMovimentoElevador;
    }

    /**
     * Inicia a simulação.
     */
    public void iniciar(){
        if(emExecucao) return;
        emExecucao = true;
        iniciarTemporizador();
        System.out.println("---------------------------------------------------------");
        System.out.println("                 SIMULAÇÃO INICIADA");
    }


    /**
     * Pausa a simulação.
     */
    public void pausar(){
        if(temporizador != null ) {
            temporizador.cancel();
            emExecucao = false;
            System.out.println("---------------------------------------------------------");
            System.out.println("                 SIMULAÇÃO PAUSADA");
        }
    }

    /**
     * Continua a simulação pausada.
     */
    public void continuar(){
        if(!emExecucao){
             iniciarTemporizador();
             emExecucao = true;
            System.out.println("---------------------------------------------------------");
             System.out.println("                 SIMULAÇÃO RETOMADA");
        }
    }

    /**
     * Encerra a simulação e imprime os resultados.
     */
    public void encerrar(){
        if(temporizador != null){
            temporizador.cancel();
        }
        emExecucao = false;
        // Coleta estatísticas da simulação
        int energiaGasta = predio.getCentralDeControle().getEnergiaGasta();
        int maiorTempo = predio.getCentralDeControle().getMaiorTempoEspera();
        float tempoMedio = (float) predio.getCentralDeControle().getTempoEsperaTotal() / predio.getCentralDeControle().getChamadasAtendidas();
        int tempoMedioTemp = (int) tempoMedio;
        float energiaPorChamada =  predio.getCentralDeControle().getEnergiaGasta() / (float) predio.getCentralDeControle().getChamadasAtendidas();
        int chamadasAtendidas = predio.getCentralDeControle().getChamadasAtendidas();

        // Impressão dos resultados
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

    /**
     * Inicializa o temporizador que controla a execução da simulação.
     */
    public void iniciarTemporizador(){
          temporizador = new Timer();
          temporizador.scheduleAtFixedRate(new TimerTask() {
              public void run() {
                  if(segundosSimulados < duracaoSimulacao) {

                      // Executa atualização no tempo configurado de movimento do elevador
                      if(segundosSimulados%tempoMovimentoElevador == 0){
                          System.out.println("---------------------------------------------------------");
                          if(segundosSimulados%60 == 0){
                              // Exibe o tempo simulado no formato HH:MM:SS
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

    /**
     * Grava o estado atual da simulação em um arquivo.
     */
    public void gravar() {

        // Monta o nome do arquivo com informações da simulação
        String nomeArquivo = "teste";
        nomeArquivo += "-a" + predio.getAndares().getTamanho();
        nomeArquivo += "-e" + predio.getCentralDeControle().getElevadores().getTamanho();
        nomeArquivo += "-pe" + predio.getCentralDeControle().getElevadores().getElemento(0).getCapacidadeMaxima();

        if(predio.getCentralDeControle().getEstado() == CentralDeControle.EstadoCentralDeControle.Normal){
            nomeArquivo += "-Normal";
        } else if (predio.getCentralDeControle().getEstado() == CentralDeControle.EstadoCentralDeControle.Economia) {
            nomeArquivo += "-Economia";
        }else{
            nomeArquivo += "-Felicidade";
        }

        if(predio.isHorarioPico()){
            nomeArquivo += "-HorarioPico";
        }else{
            nomeArquivo += "-HorarioNormal";
        }


        File arquivo = new File("saves", nomeArquivo + ".dat");

        // Evita sobrescrever arquivos existentes
        String nomeCompleto = nomeArquivo + ".dat";
        int cont = 1;
        while(arquivo.exists()){
            nomeCompleto = nomeArquivo + "-" + cont + ".dat";
            arquivo = new File("saves", nomeCompleto);
            cont++;
        }

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            System.out.println("---------------------------------------------------------");
            System.out.println("Simulação gravada com sucesso em : " + nomeCompleto);
            out.writeObject(this);
        }catch (IOException e){
            System.out.println("Erro ao gravar simulação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega uma simulação de um arquivo.
     */
    public static Simulador carregar(String nomeArquivo) {
        File arquivo = new File("saves", nomeArquivo + ".dat");

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Simulador sim = (Simulador) in.readObject();

            // Impressão dos dados da simulação carregada
            int energiaGasta = sim.predio.getCentralDeControle().getEnergiaGasta();
            int maiorTempo = sim.predio.getCentralDeControle().getMaiorTempoEspera();
            float tempoMedio = (float) sim.predio.getCentralDeControle().getTempoEsperaTotal() / sim.predio.getCentralDeControle().getChamadasAtendidas();
            int tempoMedioTemp = (int) tempoMedio;
            float energiaPorChamada =  sim.predio.getCentralDeControle().getEnergiaGasta() / (float) sim.predio.getCentralDeControle().getChamadasAtendidas();
            int chamadasAtendidas = sim.predio.getCentralDeControle().getChamadasAtendidas();

            System.out.println("---------------------------------------------------------");
            System.out.println("                 SIMULAÇÃO CARREGADA");
            System.out.println("---------------------------------------------------------");
            System.out.println("Nome da Simulação: " + nomeArquivo);
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
            return sim;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retorna se a simulação está executando.
     */
    public boolean estaExecutando() {
        return emExecucao;
    }

}
