package classes;

import estruturas.FilaPrioridade;
import estruturas.ListaDinamica;

import java.io.Serializable;

public class Andar implements Serializable {

    private int numero;
    private FilaPrioridade fila;
    private ListaDinamica<Pessoa> pessoas;
    private Painel painel;

    public Andar(int numero){
        this.numero = numero;
        fila = new FilaPrioridade();
        pessoas = new ListaDinamica<>();
        this.painel = new Painel();
    }


    //adiciona pessoa na fila de espera do elevador
    public void adicionarPessoaFila(Pessoa x) {
        if(fila.contemElemento(x)) return;
        fila.addElemento(x.getPrioridade(), x);
        System.out.println("Pessoa " + x.getId() + " adicionada na fila do andar " + numero);

        painel.pressionar();
    }

    //adiciona a pessoa no andar, ao sair do elevador
    public void adicionarPessoaAndar(Pessoa x) {
        if(!pessoas.contem(x)) {
            pessoas.add(x, pessoas.tamanho());
        }
    }

    //remove a pessoa da fila de espera
    public Pessoa removerPessoa() {

        if(!fila.isEmpty()) {
            return fila.removeElemento();
        }
        return null;
    }

    //faz a verificação se há ou não pessoas na fila de espera, e configura o painel
    public void verificaPessoas(){

        if(fila.isEmpty()) {
            painel.reset();
        }else{
            painel.pressionar();
        }

    }


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
