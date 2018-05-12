package Lexer;

public class Token {

    public enum TokenType {
        IDENTIFIER,
        UNIT,
        ERROR,
        IFSTATEMENT,
        WHILESTATEMENT,
        FUNCSTATEMENT,
        SUMOP,
        MLTPOP,
        NUMBER,
        EQ,
        LOGOP,
        LBRACKET,
        RBRACKET,
        LSQBRACKET,
        RSQBRACKET,
        LPAREN,
        RPAREN,
        SEMICOLON,
        END,
        STRING,
        COMMA,
        RETURNSTATEMENT,
        PRINTSTATEMENT,
        LOGANDOP,
        LOGOROP
    }

    private TokenType type;
    private String data;
    private int lineNumber;

    Token(TokenType type, String data, int lineNumber) {
        this.type = type;
        this.data = data;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return String.format(":: %s %s ::", type.name(), data);
    }

    public TokenType getType() {
        return type;
    }
    public String getData() {
        return data;
    }
    public int getLineNumber() {
        return lineNumber;
    }


}