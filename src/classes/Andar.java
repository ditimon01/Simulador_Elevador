package classes;

import estruturas.FilaPrioridade;

public class Andar {

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
