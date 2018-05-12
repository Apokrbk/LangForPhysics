package AST.Statement;

import AST.Expression.Expression;
import AST.LogExpression.LogSimpleExpression;

import java.util.ArrayList;

public class WhileStatement extends Statement{
    Expression logExpression;
    ArrayList<Statement> statements;

    public WhileStatement(Expression logExpression){
        this.logExpression=logExpression;
        statements=new ArrayList<>();
    }
    public void addStatement(Statement statement){
        statements.add(statement);
    }

    @Override
    public String toString() {
        String statementsString="";
        for (Statement statement: statements) {
            statementsString+="\n"+statement.toString();
        }
        return "while("+logExpression.toString()+"){"+statementsString+"}";
    }
}
