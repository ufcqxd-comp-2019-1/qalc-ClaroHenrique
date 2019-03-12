package br.ufc.comp.qalc.report;

import br.ufc.comp.qalc.report.messages.Message;






/**
 * Interface que modela objetos que são capazes de consumir mensagens
 * enviadas através de {@link MessageCenter}.
 */



public interface MessageConsumer {

    /**
     * Processar a mensagem {@code message} que foi enviada ao {@link MessageCenter}.
     *
     * @param message Mensagem a processar.
     */
    public void consume(Message message);

}
