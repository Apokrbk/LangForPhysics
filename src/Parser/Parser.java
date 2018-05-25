package Parser;
import AST.Expression.Expression;
import AST.Expression.Operator;
import AST.Expression.SimpleExpression;
import AST.Factor.Factor;
import AST.Factor.NumberValueFactor;
import AST.Factor.VariableFactor;
import AST.Func.FuncCall;
import AST.Func.FuncStatement;
import AST.LogExpression.LogOperator;
import AST.LogExpression.LogSimpleExpression;
import Program.Program;
import AST.Statement.*;
import Lexer.Lexer;
import Lexer.Token;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Parser {
    private Lexer lexer;
    private Token token;
    private int tok_prec;
    private Program program;

    public Parser(Lexer lexer){
        this.lexer = lexer;
        program=new Program();
    }

    public Program parse() throws Exception {
        nextToken();
        parseProgram();
        if (token.getType() != Token.TokenType.END){
            throw new Exception("Program not parsed because of errors");
        }
        return program;
    }

    private void nextToken() throws Exception {
        token=lexer.nextToken();
        tok_prec=getTok_prec();
    }

    private void parseProgram() throws Exception{
        while(token.getType() != Token.TokenType.END){
            program.addStatement(parseStatement());
        }

    }

    private Statement parseStatement() throws Exception{
        Statement statement;
        switch (token.getType()){
            case IFSTATEMENT:
                statement=parseIfStatement();
                break;
            case RETURNSTATEMENT:
                statement=parseReturnStatement();
                break;
            case WHILESTATEMENT:
                statement=parseWhileStatement();
                break;
            case PRINTSTATEMENT:
                statement=parsePrintStatement();
                break;
            case FUNCSTATEMENT:
                statement=parseFuncStatement();
                break;
            default:
                statement=parseAssignStatement();
                break;
        }
        nextToken();
        return statement;
    }

    private ReturnStatement parseReturnStatement() throws Exception {
        requireToken(Token.TokenType.IDENTIFIER);
        String varToReturn=token.getData();
        requireToken(Token.TokenType.SEMICOLON);
        return new ReturnStatement(varToReturn);
    }

    private AssignStatement parseAssignStatement() throws Exception{
        AssignStatement assignStatement;
        if(token.getType() == Token.TokenType.IDENTIFIER){
            String name=token.getData();
            requireToken(Token.TokenType.EQ);
            assignStatement=new AssignStatement(new VariableFactor(name), parseExpression());
        }
        else{
            throw new Exception("Syntax error in line " + token.getLineNumber()+": expected IDENTIFIER, actual "+token.getType());
        }
        return assignStatement;
    }

    private Expression parseExpression() throws Exception {
        if(token.getType()!= Token.TokenType.IDENTIFIER && token.getType()!= Token.TokenType.NUMBER)
            requireToken(Token.TokenType.IDENTIFIER, Token.TokenType.NUMBER);
        return parseSumExpression(parseFactor(),0);
    }

    private Expression parseSumExpression(Expression lhs, int min_prec) throws Exception {
        Token tok2=token;
        nextToken();
        while(isOperatorWithGreaterOrEqualPrecThanMinPrec(min_prec)){
            int tok_prec_prev=tok_prec;
            Operator operator=new Operator(token.getData());
            nextToken();
            Expression rhs = parseFactor();
            nextToken();
            while(isOperatorWithGreaterPrecThanMinPrec(min_prec, tok_prec_prev)){
                rhs = parseSumExpression(rhs, tok_prec);
                nextToken();
            }
            lhs = new SimpleExpression(lhs, operator, rhs);
        }
        if(token.getType()== Token.TokenType.IDENTIFIER || token.getType()== Token.TokenType.NUMBER){
            Operator operator=new Operator(tok2.getData());
            Expression rhs = parseFactor();
            lhs = new SimpleExpression(lhs, operator, rhs);
        }
        return lhs;
    }

    private boolean isOperatorWithGreaterPrecThanMinPrec(int min_prec, int tok_prec_prev) {
        return tok_prec > min_prec && tok_prec_prev!=1 && (tok_prec==1 || tok_prec==0);
    }

    private boolean isOperatorWithGreaterOrEqualPrecThanMinPrec(int min_prec) {
        return tok_prec >= min_prec && (tok_prec==1 || tok_prec==0);
    }

    private int getTok_prec() {
        int tok_prec;
        if(token.getType()== Token.TokenType.SUMOP)
            tok_prec=0;
        else if(token.getType() == Token.TokenType.MLTPOP)
            tok_prec=1;
        else if(token.getType() == Token.TokenType.LOGANDOP)
            tok_prec=4;
        else if(token.getType() == Token.TokenType.LOGOROP)
            tok_prec=3;
        else if(token.getType() == Token.TokenType.LOGOP)
            tok_prec=5;
        else
            tok_prec=-1;
        return tok_prec;
    }

    private String parseUnit() throws Exception {
        StringBuilder unit;
        requireToken(Token.TokenType.LSQBRACKET);
        requireToken(Token.TokenType.UNIT, Token.TokenType.RSQBRACKET, Token.TokenType.MLTPOP);
        if(token.getType()== Token.TokenType.UNIT || token.getType()== Token.TokenType.MLTPOP){
            unit = new StringBuilder(token.getData());
            if(token.getType()== Token.TokenType.UNIT) {
                requireToken(Token.TokenType.MLTPOP, Token.TokenType.RSQBRACKET);
            }
            while(token.getType()!= Token.TokenType.RSQBRACKET)
            {
                unit.append(token.getData());
                requireToken(Token.TokenType.UNIT);
                unit.append(token.getData());
                requireToken(Token.TokenType.MLTPOP, Token.TokenType.RSQBRACKET);
            }
        }
        else{
            unit = new StringBuilder();
        }
        return unit.toString();
    }

    private FuncStatement parseFuncStatement() throws Exception {
        requireToken(Token.TokenType.IDENTIFIER);
        if(!token.getData().substring(0,1).equals("_")){
            throw new Exception("Func name should start with _");
        }
        FuncStatement funcStatement=new FuncStatement(token.getData());
        requireToken(Token.TokenType.LPAREN);
        requireToken(Token.TokenType.RPAREN, Token.TokenType.IDENTIFIER);
        if(token.getType()== Token.TokenType.IDENTIFIER){
            funcStatement.addArgument(new VariableFactor(token.getData()));
            requireToken(Token.TokenType.COMMA, Token.TokenType.RPAREN);
            while(token.getType()!= Token.TokenType.RPAREN){
                requireToken(Token.TokenType.IDENTIFIER);
                funcStatement.addArgument(new VariableFactor(token.getData()));
                requireToken(Token.TokenType.COMMA, Token.TokenType.RPAREN);
            }
        }
        requireToken(Token.TokenType.LBRACKET);
        nextToken();
        while(token.getType() != Token.TokenType.RBRACKET)
            funcStatement.addStatement(parseStatement());

        return funcStatement;
    }

    private Statement parsePrintStatement() throws Exception {
        requireToken(Token.TokenType.LPAREN);
        requireToken(Token.TokenType.IDENTIFIER);
        String var = token.getData();
        requireToken(Token.TokenType.RPAREN);
        requireToken(Token.TokenType.SEMICOLON);
        return new PrintStatement(var);
    }

    private IfStatement parseIfStatement() throws Exception {
        requireToken(Token.TokenType.LPAREN);
        IfStatement ifStatement= new IfStatement(parseLogExpression());
        if(token.getType()!= Token.TokenType.RPAREN)
            requireToken(Token.TokenType.RPAREN);
        requireToken(Token.TokenType.LBRACKET);
        nextToken();
        while(token.getType() != Token.TokenType.RBRACKET){
            ifStatement.addStatement(parseStatement());
        }
        return ifStatement;

    }

    private WhileStatement parseWhileStatement() throws Exception {
        requireToken(Token.TokenType.LPAREN);
        WhileStatement whileStatement= new WhileStatement(parseLogExpression());
        if(token.getType()!= Token.TokenType.RPAREN)
            requireToken(Token.TokenType.RPAREN);
        requireToken(Token.TokenType.LBRACKET);
        nextToken();
        while(token.getType() != Token.TokenType.RBRACKET){
            whileStatement.addStatement(parseStatement());
        }
        return whileStatement;


    }

    private Factor parseFactor() throws Exception {
        if(token.getType()== Token.TokenType.NUMBER){
            int value=Integer.parseInt(token.getData());
            String unit = parseUnit();
            return new NumberValueFactor(value,unit);
        }
        else{
            if(token.getData().substring(0, 1).equals("_")){
                return parseFuncCall();
            }
            else{
                return new VariableFactor(token.getData());
            }
        }
    }

    private Factor parseFuncCall() throws Exception {
        String funcName=token.getData();
        requireToken(Token.TokenType.LPAREN);
        ArrayList<Factor> arguments= new ArrayList<>();
        while(token.getType()!= Token.TokenType.RPAREN){
            requireToken(Token.TokenType.IDENTIFIER, Token.TokenType.NUMBER);
            arguments.add(parseFactor());
            requireToken(Token.TokenType.COMMA, Token.TokenType.RPAREN);
        }
        return new FuncCall(funcName, arguments);
    }

    private Expression parseLogExpression() throws Exception {
        return parseLogSimpleExpression(parseExpression(),3);
    }

    private Expression parseLogSimpleExpression(Expression lhs, int min_prec) throws Exception {
        while(tok_prec >= min_prec){
            int tok_prec_prev=tok_prec;
            LogOperator operator=new LogOperator(token.getData());
            Expression rhs = parseExpression();
            while(tok_prec > min_prec && tok_prec_prev!=5){
                rhs = parseLogSimpleExpression(rhs, tok_prec);
            }
            lhs = new LogSimpleExpression(lhs, operator, rhs);
        }
        return lhs;
    }

    private void requireToken(Token.TokenType... tokenTypes) throws Exception {
        nextToken();
        boolean goodToken = false;
        for (Token.TokenType tokenType : tokenTypes) {
            if (token.getType() == tokenType)
                goodToken = true;
        }
        if (!goodToken)
            throw new Exception("Syntax error in line " + token.getLineNumber() + ": expected " + Arrays.toString(tokenTypes) + ", actual " + token.getType());
    }
}
