    package classes;

    import estruturas.ListaDinamica;

    public class Elevador extends Serializacao {

        private final int numeroElevador;// id do elevador
        private int andarAtual;// andar onde o elevador está
        private int destino;// andar para onde o elevador irá (trocar para uma lista, para armazenar o destino de todas as pessoas dentro do elevador)
        private ListaDinamica<Pessoa> pessoasSaida;
        private ListaDinamica<Integer> destinos;
        private ListaDinamica<Pessoa> pessoasDentro;// lista de pessoas dentro do elevador
        private static final int CAPACIDADE_MAXIMA = 10;// capacidade máxima de pessoas dentro do elevador
        private EstadoElevador estado;// estado atual do elevador, se está subindo, descendo ou parado

        //definir possíveis estados do elevador
        public enum EstadoElevador {
            PARADO, SUBINDO, DESCENDO
        }


        public Elevador(int numero) {
            this.numeroElevador = numero;
            this.andarAtual = 0;
            this.destino = 0;
            this.pessoasSaida = new ListaDinamica<>();
            this.destinos = new ListaDinamica<>();
            this.pessoasDentro = new ListaDinamica<>();
            this.estado = EstadoElevador.PARADO;
        }

        // adiciona 1 destino a lista de destinos
        public void addDestino(int destino){
            if(destino != andarAtual && !destinos.contem(destino)){
                destinos.add(destino, destinos.tamanho());
                ordenarDestinos();
            }
        }

        // ordena a lista de destinos baseado na distância de cada destino
        // considerar utilização de outro algoritmo de ordenação
        // considerar alteração para cada heurísticas
        private void ordenarDestinos(){
            for(int i = 0; i < destinos.tamanho() - 1; i++){
                for(int j = i + 1; j < destinos.tamanho(); j++){
                    int distancia1 = Math.abs(destinos.getElemento(i) - andarAtual);
                    int distancia2 = Math.abs(destinos.getElemento(j) - andarAtual);
                    if(distancia2 < distancia1){
                        int temp = destinos.getElemento(i);
                        destinos.set(destinos.getElemento(j), i);
                        destinos.set(temp, j);
                    }
                }
            }
        }

        public void atualizarDestino() {

            if (destinos.tamanho() == 0) {
                this.destino = -1;
                return;
            }

            this.destino = destinos.getElemento(0);

        }

        public void removeDestino(int destino){
            for(int i = 0; i < destinos.tamanho(); i++){
                if(destinos.getElemento(i) == destino){
                    destinos.removePorPosicao(i);
                    break;
                }
            }
            ordenarDestinos();
        }


        // método para atualizar o elevador a cada minuto simulado
        @Override
        public void atualizar(int segundosSimulados){

            if(destinos.tamanho() == 0){
                estado = EstadoElevador.PARADO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " - Estado: " + estado);
                return;
            }

            if(andarAtual == destino){
                estado = EstadoElevador.PARADO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " - Estado: " + estado);
                atualizarDestino();
                return;
            }

            if(andarAtual < destino){
                estado = EstadoElevador.SUBINDO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " - Estado: " + estado);
                andarAtual++;
            }else{
                estado = EstadoElevador.DESCENDO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " - Estado: " + estado);
                andarAtual--;
            }

        }


        public void desembarquePessoas() {
            if(estado != EstadoElevador.PARADO) return;
            for(int i = 0; i < pessoasDentro.tamanho(); i++){ //percorre a lista de pessoas dentro do elevador
                Pessoa p = pessoasDentro.getElemento(i);
                if(p != null && p.getAndarDestino() == andarAtual){ //compara o andar de destino de cada pessoa dentro do elevador
                    p.sairElevador();
                    pessoasSaida.add(p, pessoasSaida.tamanho());
                    pessoasDentro.removePorPosicao(i);
                    System.out.println("Pessoa " + p.getId() + " desembarcou no andar " + andarAtual);
                    i--;
                }
            }
            removeDestino(andarAtual);
        }


        public void adicionarPessoa(Pessoa p){
            if(pessoasDentro.tamanho() < CAPACIDADE_MAXIMA){
                pessoasDentro.add(p, pessoasDentro.tamanho());
                p.entrarElevador();
                System.out.println("Pessoa " + p.getId() + " entrou no elevador " + numeroElevador);
                addDestino(p.getAndarDestino());
            } else {
                System.out.println("Elevador " + numeroElevador + " está cheio. Pessoa " + p.getId() + " não entrou.");
            }
        }



        public int getDestino() { return destino; }

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

        public int getNumeroElevador() { return numeroElevador; }

        public ListaDinamica<Integer> getDestinos() { return destinos; }

        public ListaDinamica<Pessoa> getPessoasSaida() {
            return pessoasSaida;
        }
    }
