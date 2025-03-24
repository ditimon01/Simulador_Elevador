public class HeapBinario {
    private int[] prioridade;
    private int ultimo;
    private int tamanho;


    public HeapBinario(int n){
        this.prioridade = new int[n];
        this.ultimo = 0;
        this.tamanho = n;
    }

    public void Inserir(int valor){
        if(ultimo >= tamanho) throw new IllegalStateException("Fila cheia");

        prioridade[ultimo] = valor;
        SiftUp(ultimo);
        ultimo++;

    }

    private void SiftUp(int i){
        int pai = (i-1)/2;

        while(i > 0 && prioridade[i] > prioridade[pai]){
            trocar(i,pai);
            i = pai;
            pai = (i-1)/2;
        }
    }

    private void trocar(int i, int j){
        int temp =  prioridade[i];
        prioridade[i] = prioridade[j];
        prioridade[j] = temp;
    }


}