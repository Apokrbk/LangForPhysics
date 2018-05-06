package Lexer;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;

public class Lexer {


    private char currentChar;
    private int currentPos;
    private String input;
    private String tokenData;
    private int currentLine;

    public Lexer(String input){
        this.input=input;
        currentPos=0;
        currentLine=1;
    }

    private void nextChar()
    {
        if(currentPos>=input.length()) {
            currentChar = '\0';
            currentPos++;
        }
        else{
            currentChar=input.charAt(currentPos);
            currentPos++;
        }
        if(currentChar=='\n'){
            currentLine++;
        }
    }
    public Token nextToken() {
        tokenData = "";
        nextChar();
        while (isWhitespace(currentChar)) {
                nextChar();
        }
        Token x = trySimpleSign();
        if (x != null) return x;
        if (isLetter(currentChar)) {
            return tryIdentifier();
        }
        else if (isDigit(currentChar) && currentChar!='0') {
            return tryDigit();
        }
        else if (currentChar=='0'){
            return tryZero();
        }
        else if (currentChar=='=' || currentChar=='<' || currentChar=='>'){
            return tryLogSign();
        }
        else if(currentChar=='"'){
            return tryString();
        }
        return new Token(Token.TokenType.ERROR, "Invalid sign: " + currentChar, currentLine);
    }

    private Token tryZero(){
            tokenData+=currentChar;
            nextChar();
            if(isLetter(currentChar) || isDigit(currentChar)){
                while(isLetter(currentChar) || isDigit(currentChar)){
                    tokenData+=currentChar;
                    nextChar();
                }
                currentPos--;
                return new Token(Token.TokenType.ERROR, "Identifier or number cannot start with zero: "+tokenData, currentLine);
            }
            else{
                currentPos--;
                return new Token(Token.TokenType.NUMBER, "0", currentLine);
            }
    }

    private Token tryString() {
        tokenData+=Character.toString(currentChar);
        nextChar();
        StringBuilder tokenDataBuilder = new StringBuilder(tokenData);
        while(currentChar != '"'){
            tokenDataBuilder.append(Character.toString(currentChar));
            if(currentChar=='\0'){
                return new Token(Token.TokenType.ERROR, "Odd number of quotation marks", currentLine);
            }
            nextChar();
        }
        tokenData = tokenDataBuilder.toString();
        tokenData+=Character.toString(currentChar);
        return new Token(Token.TokenType.STRING, tokenData, currentLine);
    }

    private Token tryLogSign() {
        tokenData += Character.toString(currentChar);
        nextChar();
        if (currentChar == '=') {
            tokenData += Character.toString(currentChar);
            return new Token(Token.TokenType.LOGOP, tokenData, currentLine);
        } else {
            currentPos--;
            switch(tokenData){
                case "=":
                    return new Token(Token.TokenType.EQ, tokenData , currentLine);
                case "<":
                    return new Token(Token.TokenType.LOGOP, tokenData, currentLine);
                case ">":
                    return new Token(Token.TokenType.LOGOP, tokenData, currentLine);
            }
        }
        return new Token(Token.TokenType.ERROR, "Unknown logical operator: " + tokenData+currentChar, currentLine);
    }

    private Token tryDigit() {
            while (isDigit(currentChar)) {
                tokenData+=currentChar;
                nextChar();
            }
            if(isLetter(currentChar)){
                while(isLetter(currentChar) || isDigit(currentChar)){
                    tokenData+=currentChar;
                    nextChar();
                }
                currentPos--;
                return new Token(Token.TokenType.ERROR, "Identifier cannot start with number: "+tokenData, currentLine);
            }
            else currentPos--;

            int value=Integer.parseInt(tokenData);
            if(value>9999){
                return new Token(Token.TokenType.ERROR, "Number out of range: "+tokenData, currentLine);
            }
            else{
                return new Token(Token.TokenType.NUMBER, tokenData, currentLine);
            }
        }

    private Token tryIdentifier() {
        StringBuilder tokenDataBuilder = new StringBuilder(tokenData);
        while (isLetter(currentChar) || isDigit(currentChar)) {
            tokenDataBuilder.append(Character.toString(currentChar));
            nextChar();
        }
        tokenData = tokenDataBuilder.toString();
        currentPos--;
        switch (tokenData) {
            case "if":
                return new Token(Token.TokenType.IFSTATEMENT, tokenData, currentLine);
            case "while":
                return new Token(Token.TokenType.WHILESTATEMENT, tokenData, currentLine);
            case "func":
                return new Token(Token.TokenType.FUNCSTATEMENT, tokenData, currentLine);
            case "return":
                return new Token(Token.TokenType.RETURNSTATEMENT, tokenData, currentLine);
            case "print":
                return new Token(Token.TokenType.PRINTSTATEMENT, tokenData, currentLine);
            case "kg":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "s":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "m":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "N":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "J":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "A":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "K":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "W":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "Hz":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "C":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            case "V":
                return new Token(Token.TokenType.UNIT, tokenData, currentLine);
            default:
                return new Token(Token.TokenType.IDENTIFIER, tokenData, currentLine);
        }
    }

    private Token trySimpleSign() {
        switch (currentChar) {
            case '+':
                return new Token(Token.TokenType.SUMOP, "+", currentLine);
            case '-':
                return new Token(Token.TokenType.SUMOP, "-", currentLine);
            case '*':
                return new Token(Token.TokenType.MLTPOP, "*", currentLine);
            case '/':
                return new Token(Token.TokenType.MLTPOP, "/", currentLine);
            case '(':
                return new Token(Token.TokenType.LPAREN, "(", currentLine);
            case ')':
                return new Token(Token.TokenType.RPAREN, ")", currentLine);
            case '{':
                return new Token(Token.TokenType.LBRACKET, "{", currentLine);
            case '}':
                return new Token(Token.TokenType.RBRACKET, "}", currentLine);
            case '[':
                return new Token(Token.TokenType.LSQBRACKET, "[", currentLine);
            case ']':
                return new Token(Token.TokenType.RSQBRACKET, "]", currentLine);
            case ';':
                return new Token(Token.TokenType.SEMICOLON, ";", currentLine);
            case ',':
                return new Token(Token.TokenType.COMMA, ",", currentLine);
            case '\0':
                return new Token(Token.TokenType.END, "", currentLine);
        }
        return null;
    }

}