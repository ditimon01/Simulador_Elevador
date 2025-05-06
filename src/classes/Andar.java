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


    public void verificaPessoas(){
        if(fila.isEmpty()){
            painel.resetar();
        }
        Pessoa atual = fila.getPrimeiroElemento();

        while(atual != null){
            if(painel.botaoSubirEstaAtivado() && painel.botaoDescerEstaAtivado()) break;
            if(atual.getAndarDestino() < numero){
                painel.pressionarDescer();
            }
            else {
                painel.pressionarSubir();
            }
            atual = fila.getPrimeiroElemento();// consertar para incrementar após colocar nós duplos na fila comum
        }
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
