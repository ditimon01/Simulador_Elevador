public class ListaEstatica {
        int tamanho;
        int[] valor;
        int i;
        int ultimo;

    // adicionar algoritmo de busca nessa e na outra

        public ListaEstatica(int n){
            this.tamanho = n;
            this.valor = new int[tamanho];
            this.ultimo = -1;
        }

        public boolean add(int elemento, int posicao){
            i = tamanho;
            if (posicao > ultimo + 1 || posicao < 0 || ultimo + 1 >= tamanho){
                return false;
            }
            for(i = ultimo;i >= posicao; i--){
                valor[i+1] = valor[i];
            }
            valor[posicao] = elemento;
            ultimo++;
            return true;
        }

        public boolean remove(int posicao){
            i = 0;
            if(posicao < 0 || posicao > ultimo){
                return false;
            }
            for(i = posicao; i < ultimo;i++){
                valor[i] = valor[i+1];
            }
            valor[ultimo] = 0;
            ultimo--;
            return true;
        }

        public void printarLista(){
            for(i = 0;i <= ultimo;i++){
                System.out.println(i + ". " + valor[i]);
            }
        }

        public boolean buscarElemento(int elemento){
            boolean retorno = false;

            System.out.println("Buscando o elemento " + elemento);

            for(i = 0; i <= ultimo; i++){
                if(elemento == valor[i]){
                    System.out.println("Elemento encontrado na posição : " + i);
                    retorno = true;
                }
            }

            if(!retorno){
                System.out.println("Elemento não encontrado!");
            }

            return retorno;

        }
}


