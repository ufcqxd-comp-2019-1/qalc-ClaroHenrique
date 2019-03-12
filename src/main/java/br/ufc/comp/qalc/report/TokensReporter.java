package br.ufc.comp.qalc.report;

import br.ufc.comp.qalc.OutputVerbosity;
import br.ufc.comp.qalc.report.messages.Message;
import br.ufc.comp.qalc.report.messages.NewTokenMessage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Classe especializada no relato de mensagens de reconhecimento de tokens.
 *
 * @see NewTokenMessage
 */
public class TokensReporter extends BasicReporter {

    /**
     * @see BasicReporter#BasicReporter(OutputStream)
     */
    public TokensReporter(OutputStream stream) {
        super(stream);
    }

    /**
     * @see BasicReporter#BasicReporter(OutputStream, OutputVerbosity)
     */
    public TokensReporter(OutputStream stream, OutputVerbosity verbosity) {
        super(stream, verbosity);
    }

    /**
     * Caso a mensagem recebida seja do tipo {@link NewTokenMessage},
     * reporta o reconhecimento do token associado.
     * <p>
     * A depender do nível de verbosidade solicitado pelo usuário, a saída
     * possui diferentes formatos:
     *
     * <dl>
     * <dt>{@link OutputVerbosity#ESSENTIAL}</dt>
     * <dd>- {@code (ID)}</dd>
     * <dt>{@link OutputVerbosity#ADDITIONAL_INFO}</dt>
     * <dd>- {@code (ID, "lexema")}</dd>
     * <dt>{@link OutputVerbosity#EVERYTHING}</dt>
     * <dd>- {@code (ID, "lexema", L: 1, C: 1-2)}</dd>
     * </dl>
     *
     * @param message A mensagem a consumir.
     */
    @Override
    public void consume(Message message) {
        if (message instanceof NewTokenMessage) {
            try {
                // TODO verbosity;

                output.write(String.format("(%s", ((NewTokenMessage) message).getToken().getTokenIdentifier()) );

                if(super.verbosity == OutputVerbosity.ADDITIONAL_INFO || super.verbosity == OutputVerbosity.EVERYTHING){
                    output.write(String.format(",%s", ((NewTokenMessage) message).getToken().toString()));
                }
                if(super.verbosity == OutputVerbosity.EVERYTHING) {
                    output.write(String.format(",L: %d, C: %d-%d",
                            ((NewTokenMessage) message).getToken().getLineNumber(),
                            ((NewTokenMessage) message).getToken().getColumnStart(),
                            ((NewTokenMessage) message).getToken().getColumnEnd()     ));
                }
                output.write(String.format(")\n"));

            } catch (IOException e) {
                reportFailure(e);
            }
        }
    }
}
