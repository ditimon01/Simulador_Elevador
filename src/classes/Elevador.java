package classes;

import estruturas.ListaDinamica;


public class Elevador extends Serializacao {

    private final int numerodoElevador;// id do elevador
    private int andarAtual;// andar onde o elevador está
    private int destino;// andar para onde o elevador irá
    private ListaDinamica<Pessoa> pessoasDentro;// lista de pessoas dentro do elevador
    private static final int CAPACIDADE_MAXIMA = 10;// capacidade máxima de pessoas dentro do elevador
    private EstadoElevador estado;// estado atual do elevador, se está subindo, descendo ou parado

    //definir possíveis estados do elevador
    public enum EstadoElevador {
        PARADO, SUBINDO, DESCENDO
    }


    public Elevador(int numero) {
        this.numerodoElevador = numero;
        this.andarAtual = 0;
        this.destino = 0;
        this.pessoasDentro = new ListaDinamica<>();
        this.estado = EstadoElevador.PARADO;
    }


    @Override
    // método para atualizar o elevador a cada minuto simulado
    public void atualizar(int minutosSimulados){
        System.out.println("Elevador " + numerodoElevador + " no andar " + andarAtual + " no minuto " + minutosSimulados + " - Estado: " + estado);

        if(andarAtual < destino){
            estado = EstadoElevador.SUBINDO;
            andarAtual++;
        }else if(andarAtual > destino){
            estado = EstadoElevador.DESCENDO;
            andarAtual--;
        }else{
            estado = EstadoElevador.PARADO;
            desembarquePessoas(minutosSimulados); //desembarca as pessoas caso seja o andar de destino delas
        }
    }


    private void desembarquePessoas(int minutosSimulados) {
        for(int i = 0; i < pessoasDentro.tamanho(); i++){
            Pessoa p = pessoasDentro.getElemento(i);
            if(p.getAndarDestino() == andarAtual){
                p.sairElevador();
                pessoasDentro.remove(i);
                System.out.println("Pessoa" + p.getId() + " desembarcou no andar " + andarAtual);
            }
        }
    }


    public void adicionarPessoa(Pessoa p){
        if(pessoasDentro.tamanho() < CAPACIDADE_MAXIMA){
            pessoasDentro.add(p, pessoasDentro.tamanho() + 1); //editar após adicionar fila de prioridade
            p.entrarElevador();
            System.out.println("Pessoa " + p.getId() + " entrou no elevador " + numerodoElevador);
        }
    }


    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public EstadoElevador getEstado() {
        return estado;
    }

    public ListaDinamica<Pessoa> getPessoasDentro() {
        return pessoasDentro;
    }

    public static int getCapacidadeMaxima() {
        return CAPACIDADE_MAXIMA;
    }
}
