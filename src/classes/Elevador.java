    package classes;

    import estruturas.ListaDinamica;

    public class Elevador extends Serializacao {

        private final int numeroElevador;// id do elevador
        private int andarAtual;// andar onde o elevador está
        private int destino;// andar para onde o elevador irá (trocar para uma lista, para armazenar o destino de todas as pessoas dentro do elevador)
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
            this.destinos = new ListaDinamica<>();
            this.pessoasDentro = new ListaDinamica<>();
            this.estado = EstadoElevador.PARADO;
        }


        public void addDestino(int destino){
            if(!destinos.contem(destino)){
                destinos.add(destino, destinos.tamanho());
            }
        }


        public void atualizarDestino(){
            this.destino = calcularProximoDestino();
        }


        public int calcularProximoDestino(){

            if(destinos.tamanho() == 0) return andarAtual;

            int proximoDestino = destinos.getElemento(0);
            int menorDistancia = Math.abs(proximoDestino - andarAtual);

            for(int i = 0; i < destinos.tamanho(); i++){
                int destinoTemp = destinos.getElemento(i);
                int distancia = Math.abs(destinos.getElemento(i) - andarAtual);

                if((estado == EstadoElevador.SUBINDO && destinoTemp > andarAtual) || (estado == EstadoElevador.DESCENDO && destinoTemp < andarAtual)){
                    distancia = distancia/2;
                }
                if(distancia < menorDistancia ){
                    menorDistancia = distancia;
                    proximoDestino = destinoTemp;
                }
            }

            return proximoDestino;
        }


        public void removeDestino(int destino){
            this.destinos.removePorElemento(destino);
        }


        // método para atualizar o elevador a cada minuto simulado
        @Override
        public void atualizar(int minutosSimulados){

            if(destinos.tamanho() == 0){
                estado = EstadoElevador.PARADO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " no minuto " + minutosSimulados + " - Estado: " + estado);
                return;
            }

            this.destino = calcularProximoDestino();

            if(andarAtual < destino){
                estado = EstadoElevador.SUBINDO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " no minuto " + minutosSimulados + " - Estado: " + estado);
                andarAtual++;
            }else if(andarAtual > destino){
                estado = EstadoElevador.DESCENDO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " no minuto " + minutosSimulados + " - Estado: " + estado);
                andarAtual--;
            }else{
                estado = EstadoElevador.PARADO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " no minuto " + minutosSimulados + " - Estado: " + estado);
                desembarquePessoas(); //desembarca as pessoas caso seja o andar de destino delas
            }

        }


        private void desembarquePessoas() {

            for(int i = 0; i < pessoasDentro.tamanho(); i++){ //percorre a lista de pessoas dentro do elevador
                Pessoa p = pessoasDentro.getElemento(i);
                if(p != null && p.getAndarDestino() == andarAtual){ //compara o andar de destino de cada pessoa dentro do elevador
                    p.sairElevador();
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

            }

            removeDestino(andarAtual);
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
    }
