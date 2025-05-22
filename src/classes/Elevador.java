    package classes;

    import estruturas.ListaDinamica;

    // Classe que representa um Elevador, herda de Serializacao para suportar salvamento de estado
    public class Elevador extends Serializacao {

        // Atributos principais do elevador
        private final int numeroElevador;// ID do elevador
        private int andarAtual;// andar onde o elevador está
        private int destino;// andar atual do destino

        // Listas dinâmicas para gerenciar pessoas e destinos
        private final ListaDinamica<Pessoa> pessoasSaida;// pessoas que já saíram do elevador
        private final ListaDinamica<Integer> destinos;// lista de andares de destino
        private final ListaDinamica<Pessoa> pessoasDentro;// lista de pessoas dentro do elevador

        // Constantes e estado
        private final int CAPACIDADE_MAXIMA;// capacidade máxima de pessoas dentro do elevador
        private EstadoElevador estado;// estado atual do elevador: subindo, descendo ou parado

        // Enumeração que define os possíveis estados do elevador
        public enum EstadoElevador {
            PARADO, SUBINDO, DESCENDO
        }

        // Construtor do elevador
        public Elevador(int numero, int capacidadeMaxima) {
            this.numeroElevador = numero;
            this.CAPACIDADE_MAXIMA = capacidadeMaxima;
            this.andarAtual = 0;
            this.destino = 0;
            this.pessoasSaida = new ListaDinamica<>();
            this.destinos = new ListaDinamica<>();
            this.pessoasDentro = new ListaDinamica<>();
            this.estado = EstadoElevador.PARADO;
        }

        // Adiciona um destino ao elevador, se não for o andar atual e não estiver na lista
        public void addDestino(int destino){
            if(destino != andarAtual && !destinos.contem(destino)){
                destinos.add(destino, destinos.tamanho());
                ordenarDestinos();
            }
        }

        // Ordena os destinos baseando-se na distância até o andar atual
        // Observação: essa lógica pode ser melhorada para considerar o sentido do elevador e reduzir a quantidade de processamento
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

        // Atualiza o destino atual para o primeiro da lista de destinos
        public void atualizarDestino() {

            if (destinos.tamanho() == 0) {
                this.destino = -1;// sem destinos
                return;
            }

            this.destino = destinos.getElemento(0); // pega o primeiro destino da fila
        }

        // Remove um destino específico da lista
        public void removeDestino(int destino){
            for(int i = 0; i < destinos.tamanho(); i++){
                if(destinos.getElemento(i) == destino){
                    destinos.removePorPosicao(i);
                    break;
                }
            }
            ordenarDestinos();// reordena após remoção
        }


        // Método chamado a cada atualização (simulação de passagem de tempo)
        @Override
        public void atualizar(int segundosSimulados){

            // Se não há destinos, o elevador para
            if(destinos.tamanho() == 0){
                estado = EstadoElevador.PARADO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " - Estado: " + estado);
                return;
            }

            // Se chegou no destino atual
            if(andarAtual == destino){
                estado = EstadoElevador.PARADO;
                System.out.println("Elevador " + numeroElevador + " no andar " + andarAtual + " - Estado: " + estado);
                atualizarDestino();// atualiza para o próximo destino
                return;
            }

            // Movimentação do elevador
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

        // Realiza o desembarque das pessoas cujo destino é o andar atual
        public void desembarquePessoas() {
            if(estado != EstadoElevador.PARADO) return;// só desembarca se estiver parado

            for(int i = 0; i < pessoasDentro.tamanho(); i++){ //percorre a lista de pessoas dentro do elevador
                Pessoa p = pessoasDentro.getElemento(i);
                if(p != null && p.getAndarDestino() == andarAtual){ //compara o andar de destino de cada pessoa dentro do elevador
                    p.sairElevador(); // atualiza estado da pessoa
                    pessoasSaida.add(p, pessoasSaida.tamanho()); // adiciona à lista de pessoas que saíram
                    pessoasDentro.removePorPosicao(i);// remove da lista de dentro do elevador
                    System.out.println("Pessoa " + p.getId() + " desembarcou no andar " + andarAtual);
                    i--;// decrementa porque a lista diminuiu
                }
            }
            removeDestino(andarAtual);// remove o andar atual da lista de destinos
        }

        // Método para adicionar uma pessoa ao elevador
        public void adicionarPessoa(Pessoa p){
            if(pessoasDentro.tamanho() < CAPACIDADE_MAXIMA){
                pessoasDentro.add(p, pessoasDentro.tamanho());// adiciona a pessoa
                p.entrarElevador();// atualiza estado da pessoa
                System.out.println("Pessoa " + p.getId() + " entrou no elevador " + numeroElevador);
                addDestino(p.getAndarDestino());// adiciona o destino da pessoa
            } else {
                System.out.println("Elevador " + numeroElevador + " está cheio. Pessoa " + p.getId() + " não entrou.");
            }
        }


        // Getters
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

        public int getCapacidadeMaxima() {
            return CAPACIDADE_MAXIMA;
        }

        public int getNumeroElevador() { return numeroElevador; }

        public ListaDinamica<Integer> getDestinos() { return destinos; }

        public ListaDinamica<Pessoa> getPessoasSaida() {
            return pessoasSaida;
        }
    }
