public class ListaEstatica {

        int tamanho;
        int[] vetor;
        int ultimo;

        int i;

        public ListaEstatica(int n){
            this.tamanho = n;
            this.vetor = new int[tamanho];
            this.ultimo = -1;
        }

        public void printarLista(){
            for(i = 0;i <= ultimo;i++){
                System.out.println(i + ". " + vetor[i]);
            }
        }

        public boolean add(int elemento, int pos){
            i = tamanho;

            if (pos > ultimo + 1 || pos < 0 || ultimo + 1 >= tamanho){
                return false;
            }

            for(i = ultimo; i >= pos ; i--){
                vetor[i+1] = vetor[i];
            }

            vetor[pos] = elemento;
            ultimo++;
            return true;
        }

        public boolean remove(int pos){
            i = 0;
            if(pos < 0 || pos > ultimo){
                return false;
            }

            for(i = pos; i < ultimo; i++){
                vetor[i] = vetor[i+1];
            }

            vetor[ultimo] = 0;
            ultimo--;
            return true;
        }

        public boolean buscarElemento(int elemento){
            boolean retorno = false;

            System.out.println("Buscando o elemento " + elemento);

            for(i = 0; i <= ultimo; i++){
                if(elemento == vetor[i]){
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

