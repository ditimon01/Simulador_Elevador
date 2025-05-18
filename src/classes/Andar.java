package classes;

import estruturas.FilaPrioridade;
import estruturas.ListaDinamica;
import estruturas.NodeDuplo;

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


    public void adicionarPessoaFila(Pessoa x) {
        if(fila.contemElemento(x)) return;
        fila.addElemento(x.getPrioridade(), x);
        System.out.println("Pessoa " + x.getId() + " adicionada na fila do andar " + numero);

        if(x.getAndarDestino() > numero) {painel.pressionarSubir();}
        else {painel.pressionarDescer();}
    }


    public void adicionarPessoaAndar(Pessoa x) {
        if(!pessoas.contem(x)) {
            pessoas.add(x, pessoas.tamanho());
        }
    }

    public Pessoa removerPessoa() {

        if(!fila.isEmpty()) {
            return fila.removeElemento();
        }
        return null;
    }


    public void verificaPessoas(){

        this.painel.reset();

        boolean subir = false;
        boolean descer = false;

        NodeDuplo<Pessoa> atual = fila.getPrimeiroElemento();
        while(atual != null){

            if(atual.getElemento().getAndarDestino() > numero){
                subir = true;
            }
            else if(atual.getElemento().getAndarDestino() < numero){
                descer = true;
            }
            if(subir && descer) break;
            atual = atual.getNext();
        }
        if(subir) painel.pressionarSubir();
        if(descer) painel.pressionarDescer();
    }


    public boolean temChamada(){
        return painel.SubirEstaAtivado() || painel.DescerEstaAtivado();
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
}
