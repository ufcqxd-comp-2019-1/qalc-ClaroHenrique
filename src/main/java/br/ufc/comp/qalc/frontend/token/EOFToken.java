package br.ufc.comp.qalc.frontend.token;














/**
 * Token representando o fim do fluxo de tokens, que deve coincidir
 * com o fim do fluxo de entrada.
 */
public class EOFToken extends Token {
    public EOFToken(long line, long start) {
        super(line, start, start);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getTokenIdentifier() {
        return "EOF";
    }
}
