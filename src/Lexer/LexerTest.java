package Lexer;

import static org.junit.Assert.*;

public class LexerTest {

    @org.junit.Test
    public void shouldReturnEndWhenGivenEmptyInput(){
        Lexer lexer = new Lexer("");
        Token token = lexer.nextToken();
        assertEquals(Token.TokenType.END, token.getType());
    }

    @org.junit.Test
    public void shouldReturnIdentifierTokenWhenGivenIdentifier() {
        Lexer lexer = new Lexer("abc123");
        Token token = lexer.nextToken();
        assertEquals(Token.TokenType.IDENTIFIER, token.getType());
        assertEquals("abc123", token.getData());
    }

    @org.junit.Test
    public void shouldReturnIdentifierTokenWhenGivenIdentifierAfterEqualSign() {
        Lexer lexer = new Lexer("=abc123");
        Token token = lexer.nextToken();
        token = lexer.nextToken();
        assertEquals( Token.TokenType.IDENTIFIER, token.getType());
        assertEquals("abc123", token.getData());
    }

    @org.junit.Test
    public void shouldReturnNumberTokenWhenGivenNumber() {
        Lexer lexer = new Lexer("1234");
        Token token = lexer.nextToken();
        assertEquals(Token.TokenType.NUMBER,token.getType() );
        assertEquals( "1234",token.getData());
    }

    @org.junit.Test
    public void shouldReturnLogOpTokenWhenGivenLogOp() {
        Lexer lexer = new Lexer("<=");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.LOGOP, token.getType());
        assertEquals( "<=", token.getData());
    }

    @org.junit.Test
    public void shouldReturnIdentifierTokenWhenGivenLogOp() {
        Lexer lexer = new Lexer("==");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.LOGOP, token.getType());
        assertEquals( "==", token.getData());
    }

    @org.junit.Test
    public void shouldReturnErrorTokenWhenGivenNumberStartingWith0() {
        Lexer lexer = new Lexer("0123");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.ERROR, token.getType());
    }

    @org.junit.Test
    public void shouldReturnErrorTokenWhenGivenIdentifierStartingWith0() {
        Lexer lexer = new Lexer("00asbsad");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.ERROR, token.getType());
    }

    @org.junit.Test
    public void shouldReturnAssignStatementWhenGivenAssign() {
        Lexer lexer = new Lexer("a=5");
        Token token1 = lexer.nextToken();
        Token token2 = lexer.nextToken();
        Token token3 = lexer.nextToken();
        assertEquals( Token.TokenType.IDENTIFIER, token1.getType());
        assertEquals( "a", token1.getData());
        assertEquals( Token.TokenType.EQ, token2.getType());
        assertEquals( "=", token2.getData());
        assertEquals( Token.TokenType.NUMBER, token3.getType());
        assertEquals( "5", token3.getData());
    }

    @org.junit.Test
    public void shouldReturnIfTokenWhenGivenIf() {
        Lexer lexer = new Lexer("if(a123=2)");
        Token token0 = lexer.nextToken();
        Token token1 = lexer.nextToken();
        Token token2 = lexer.nextToken();
        Token token3 = lexer.nextToken();
        Token token4 = lexer.nextToken();
        Token token5 = lexer.nextToken();
        assertEquals( Token.TokenType.IFSTATEMENT, token0.getType());
        assertEquals( "if", token0.getData());
        assertEquals( Token.TokenType.LPAREN, token1.getType());
        assertEquals( "(", token1.getData());
        assertEquals( Token.TokenType.IDENTIFIER, token2.getType());
        assertEquals( "a123", token2.getData());
        assertEquals( Token.TokenType.EQ, token3.getType());
        assertEquals( "=", token3.getData());
        assertEquals( Token.TokenType.NUMBER, token4.getType());
        assertEquals( "2", token4.getData());
        assertEquals( Token.TokenType.RPAREN, token5.getType());
        assertEquals( ")", token5.getData());
    }

    @org.junit.Test
    public void shouldReturnLParenTokenWhenGivenLParen() {
        Lexer lexer = new Lexer("{");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.LBRACKET, token.getType());
        assertEquals( "{", token.getData());
    }

    @org.junit.Test
    public void shouldReturnUnitTokenWhenGivenUnit() {
        Lexer lexer = new Lexer("kg");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.UNIT, token.getType());
        assertEquals( "kg", token.getData());
    }

    @org.junit.Test
    public void shouldReturnReturnTokenWhenGivenReturn() {
        Lexer lexer = new Lexer("return");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.RETURNSTATEMENT, token.getType());
        assertEquals( "return", token.getData());
    }

    @org.junit.Test
    public void shouldReturnFuncTokenWhenGivenFunc() {
        Lexer lexer = new Lexer("func");
        Token token = lexer.nextToken();
        assertEquals( Token.TokenType.FUNCSTATEMENT, token.getType());
        assertEquals( "func", token.getData());
    }

    @org.junit.Test
    public void shouldReturnPrintStatementTokenWhenGivenPrint() {
        Lexer lexer = new Lexer("print(\"test\", a1)");
        Token token = lexer.nextToken();
        Token token1 = lexer.nextToken();
        Token token2 = lexer.nextToken();
        Token token3 = lexer.nextToken();
        Token token4 = lexer.nextToken();
        Token token5 = lexer.nextToken();
        assertEquals( Token.TokenType.PRINTSTATEMENT, token.getType());
        assertEquals( "print", token.getData());
        assertEquals( Token.TokenType.LPAREN, token1.getType());
        assertEquals( "(", token1.getData());
        assertEquals( Token.TokenType.STRING, token2.getType());
        assertEquals( "\"test\"", token2.getData());
        assertEquals( Token.TokenType.COMMA, token3.getType());
        assertEquals( ",", token3.getData());
        assertEquals( Token.TokenType.IDENTIFIER, token4.getType());
        assertEquals( "a1", token4.getData());
        assertEquals( Token.TokenType.RPAREN, token5.getType());
        assertEquals( ")", token5.getData());
    }
}