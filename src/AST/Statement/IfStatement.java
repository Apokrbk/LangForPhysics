package AST.Statement;

import AST.Expression.Expression;
import AST.LogExpression.LogSimpleExpression;
import Program.Context;

import java.util.ArrayList;

public class IfStatement extends Statement{
    Expression logExpression;
    ArrayList<Statement> statements;

    public IfStatement(Expression logExpression){
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
        return "if("+logExpression.toString()+"){"+statementsString+"}";
    }

    @Override
    public void execute(Context context) throws Exception {
        if(logExpression.calculate(context).getValue()==1){
            for(Statement statement: statements) {
                statement.execute(context);
            }
        }
    }
}
