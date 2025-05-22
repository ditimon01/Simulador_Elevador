package classes;

import estruturas.FilaPrioridade;
import estruturas.ListaDinamica;

import java.io.Serializable;

/**
 * Classe que representa um andar do prédio no simulador de elevadores.
 * Armazena informações sobre pessoas no andar, a fila de espera do elevador
 * e o estado do painel (chamado do elevador).
 */

public class Andar implements Serializable {
    // Número do andar
    private int numero;

    // Fila de prioridade de pessoas esperando o elevador
    private FilaPrioridade fila;

    // Lista de pessoas presentes no andar (fora da fila, ou seja, que não estão esperando o elevador)
    private ListaDinamica<Pessoa> pessoas;

    // Painel do andar que sinaliza se há chamadas para o elevador
    private Painel painel;


    /**
     * Construtor do andar.
     * @param numero Número do andar.
     */
    public Andar(int numero){
        this.numero = numero;
        fila = new FilaPrioridade();
        pessoas = new ListaDinamica<>();
        this.painel = new Painel();
    }


    /**
     * Adiciona uma pessoa na fila de espera do elevador.
     * Pressiona o painel para sinalizar chamada do elevador.
     * @param x Pessoa a ser adicionada.
     */
    public void adicionarPessoaFila(Pessoa x) {

        // Verifica se a pessoa já está na fila
        if(fila.contemElemento(x)) return;

        // Adiciona a pessoa na fila conforme sua prioridade
        fila.addElemento(x.getPrioridade(), x);
        System.out.println("Pessoa " + x.getId() + " adicionada na fila do andar " + numero);

        // Pressiona o botão do painel (sinaliza chamada)
        painel.pressionar();
    }

    /**
     * Adiciona uma pessoa no andar (quando sai do elevador).
     * @param x Pessoa a ser adicionada no andar.
     */
    public void adicionarPessoaAndar(Pessoa x) {
        if(x.getAndarDestino() == 0) return;
            // Verifica se a pessoa já está no andar
            if (!pessoas.contem(x)) {
                pessoas.add(x, pessoas.tamanho());
            }
    }

    /**
     * Remove e retorna a próxima pessoa da fila de espera (seguindo a prioridade).
     * @return Pessoa removida da fila, ou null se a fila estiver vazia.
     */
    public Pessoa removerPessoa() {

        if(!fila.isEmpty()) {
            return fila.removeElemento();
        }
        return null;
    }

    /**
     * Verifica se há pessoas na fila e atualiza o estado do painel.
     * Se não há pessoas, reseta o painel (desliga a chamada).
     */
    public void verificaPessoas(){

        if(fila.isEmpty()) {
            painel.reset(); // Desliga o botão (sem chamadas)
        }else{
            painel.pressionar(); // Mantém ligado (ainda há pessoas)
        }

    }

    // Getters dos dados estatísticos
    public boolean temChamada(){
        return painel.getBotao();
    }

    public boolean isEmpty() {
        return fila.isEmpty();
    }

    public int getNumero() {
        return numero;
    }

    public Painel getPainel() {
        return painel;
    }

    public ListaDinamica<Pessoa> getPessoas() {
        return pessoas;
    }

    public FilaPrioridade getFila() {
        return fila;
    }
}
