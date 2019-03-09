package br.ufc.comp.qalc.frontend;

import br.ufc.comp.qalc.frontend.token.EOFToken;
import br.ufc.comp.qalc.frontend.token.VariableIdentifierToken;
import br.ufc.comp.qalc.frontend.token.NumberToken;
import br.ufc.comp.qalc.frontend.token.Token;

import java.io.IOException;

/**
 * Analisador Léxico da linguagem.
 * <p>
 * Funciona como uma fonte de tokens, de onde o Analisador Sintático
 * deverá ler.
 *
 * @see Source
 */
public class Scanner {

    /**
     * Último token reconhecido.
     */
    protected Token currentToken;
    /**
     * Fonte de caracteres de onde extrair os tokens.
     */
    protected Source source;

    /**
     * Constrói um Analisador Léxico a partir de uma fonte de caracteres.
     *
     * @param sourceStream Fonte a ser utilizada.
     */
    public Scanner(Source sourceStream) {
        // FIXME Lidar corretamente com as exceções.
        this.source = sourceStream;
    }

    /**
     * Consome caracteres da fonte até que eles componham um lexema de
     * um dos tokens da linguagem, coinstrói um objeto representando esse
     * token associado ao lexema lido e o retorna.
     *
     * @return Token reconhecido.
     * @throws IOException Caso haja problema na leitura da fonte.
     */
    public Token getNextToken() throws IOException {

        if (source.getCurrentChar() == Source.EOF) {
            return new EOFToken(source.getCurrentLine(), source.getCurrentColumn());
            /////////////////////////////////////////////////////
        }else if (Character.isDigit(source.getCurrentChar())) { // NumberToken
            StringBuilder lexema = new StringBuilder();

            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();

            while (Character.isDigit(source.getCurrentChar())){
                lexema.append(source.getCurrentChar());
                source.advance();
            }
            if(source.getCurrentChar() == '.'){
                lexema.append(source.getCurrentChar());
                source.advance();
                while (Character.isDigit(source.getCurrentChar())){
                    lexema.append(source.getCurrentChar());
                    source.advance();
                } //tratar erro caso nunca entre aqui
            }

            String stringValue = lexema.toString();
            return new NumberToken(currentLine, lexemeStart, stringValue);
            /////////////////////////////////////////////////////
        }else if (source.getCurrentChar() == '$'){ // VariableIdentifierToken
            StringBuilder lexema = new StringBuilder();

            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();

            do{
                lexema.append(source.getCurrentChar());
                source.advance();
            }while (Character.isAlphabetic(source.getCurrentChar()) );

            String stringValue = lexema.toString();
            return new VariableIdentifierToken(currentLine,lexemeStart,stringValue);
            /////////////////////////////////////////////////////
        }else if (source.getCurrentChar() == '@'){ // FunctionIdentifierToken
            StringBuilder lexema = new StringBuilder();

            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();

            do{
                lexema.append(source.getCurrentChar());
                source.advance();
            }while (Character.isAlphabetic(source.getCurrentChar()) );

            String stringValue = lexema.toString();
            return new VariableIdentifierToken(currentLine,lexemeStart,stringValue);
        }


        // TODO Recuperação de erros.

        return null;
    }

    /**
     * Obtém o último token reconhecido.
     *
     * @return O último token reconhecido.
     */
    public Token getCurrentToken() {
        return currentToken;
    }
}
