package br.ufc.comp.qalc.frontend;

import br.ufc.comp.qalc.frontend.token.*;

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

        while(source.getCurrentChar() == ' '){
            source.advance();
        }

        if (source.getCurrentChar() == '#') { // comentario
            do {
                source.advance();
            } while (source.getCurrentChar() != '\n' && source.getCurrentChar() != Source.EOF);
        }

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

            lexema.append(source.getCurrentChar());
            source.advance();

            if(Character.isAlphabetic(source.getCurrentChar())) { //caso variável
                do{
                    lexema.append(source.getCurrentChar());
                    source.advance();
                }while ( Character.isAlphabetic(source.getCurrentChar()));
                String stringValue = lexema.toString();
                return new VariableIdentifierToken(currentLine,lexemeStart,stringValue);

            }else { //caso seja um indenficador de resultado ????

                if(source.getCurrentChar() == '?'){
                    lexema.append(source.getCurrentChar());
                    source.advance();
                    String stringValue = lexema.toString();
                    return new VariableIdentifierToken(currentLine,lexemeStart,stringValue);
                }

                do{
                    lexema.append(source.getCurrentChar());
                    source.advance();
                }while ( Character.isDigit(source.getCurrentChar()));
                String stringValue = lexema.toString();
                return new VariableIdentifierToken(currentLine,lexemeStart,stringValue);

            }

            /////////////////////////////////////////////////////

        }else if (source.getCurrentChar() == '@') { // FunctionIdentifierToken
            StringBuilder lexema = new StringBuilder();

            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();

            do {
                lexema.append(source.getCurrentChar());
                source.advance();
            } while (Character.isAlphabetic(source.getCurrentChar()));

            String stringValue = lexema.toString();
            return new VariableIdentifierToken(currentLine, lexemeStart, stringValue);

        }else if (source.getCurrentChar() == '=') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new AtribToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '+') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new PlusToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '-') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new MinusToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '*') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new TimesToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '/') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new DivToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '%') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new ModToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '^') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new PowToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == '(') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new LParenToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == ')') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new LParenToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == ',') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new CommaToken(currentLine, lexemeStart);

        }else if (source.getCurrentChar() == ';') {
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new SemiToken(currentLine, lexemeStart);

        }else if(source.getCurrentChar() == '\n'){
            long currentLine = source.getCurrentLine();
            long lexemeStart = source.getCurrentColumn();
            source.advance();
            return new BreakLineToken(currentLine, lexemeStart);
        }


        System.out.println("pora");
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
