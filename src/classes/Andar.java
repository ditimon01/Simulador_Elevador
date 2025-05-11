package classes;

import estruturas.FilaPrioridade;
import estruturas.NodeDuplo;

import java.io.Serializable;

public class Andar implements Serializable {

    private int numero;
    private FilaPrioridade fila;
    private Painel painel;

    public Andar(int numero){
        this.numero = numero;
        fila = new FilaPrioridade();
        this.painel = new Painel();
    }


    public void adicionarPessoa(Pessoa x) {
        fila.addElemento(x.getPrioridade(), x);
        System.out.println("Pessoa " + x.getId() + " adicionada no andar " + numero);

        if(x.getAndarDestino() > numero) {painel.pressionarSubir();}
        else {painel.pressionarDescer();}
    }


    public Pessoa removerPessoa() {

        if(!fila.isEmpty()) {
            return fila.removeElemento();
        }
        return null;
    }


    public void verificaPessoas(){
        if(fila.isEmpty()){
            painel.resetar();
            return;
        }

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
        return painel.botaoSubirEstaAtivado() || painel.botaoDescerEstaAtivado();
    }

    public int getNumero() {
        return numero;
    }

    public Painel getPainel() {
        return painel;
    }
}
