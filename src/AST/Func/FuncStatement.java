package AST.Func;

import AST.Factor.VariableFactor;
import AST.Statement.Statement;

import java.util.ArrayList;

public class FuncStatement extends Statement {
    String name;
    ArrayList<VariableFactor> arguments;
    ArrayList<Statement> statements;

    public FuncStatement(String name){
        this.name=name;
        arguments=new ArrayList<>();
        statements=new ArrayList<>();
    }

    public void addArgument(VariableFactor arg){
        arguments.add(arg);
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
        String argsString="";
        for (VariableFactor arg: arguments) {
            argsString+=arg.toString()+",";
        }

        return "func "+name+"("+argsString.substring(0, argsString.length() - 1)+"){ "+statementsString+"}";
    }
}
