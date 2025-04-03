package classes;

import estruturas.ListaDinamica;
import estruturas.Node;

class PainelInterno extends Painel {

    private ListaDinamica<Integer> andaresApertados;

    //criar estrutura parecida com lista, mas não aceita valores repetidos
    //será utilizada para armazenar os andares pressionados no elevador

    @Override
    void pressionarBotao(int andar) {
        //lembrar de consertar
        Node<Integer> temp = andaresApertados.getHead();
        int i = 0;
        while (temp != null) {
            if(andar == temp.getElemento()) return;
            temp = temp.getNext();
            i++;
        }
        andaresApertados.add(andar, i+1);
    }

    public PainelInterno(){
     andaresApertados = new ListaDinamica<>();


    }

}
