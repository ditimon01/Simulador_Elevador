package classes;

import java.io.Serializable;

/**
 * Classe abstrata que define um contrato para qualquer objeto que precise ser serializável
 * e também atualizar seu estado ao longo do tempo simulado.
 */
public abstract class Serializacao implements Serializable{
    /**
     * Método abstrato que deve ser implementado pelas subclasses para atualizar
     * o estado do objeto, dado o tempo simulado em segundos
     */
    public abstract void atualizar(int segundoSimulado);

    }
