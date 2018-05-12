package AST.Func;

import AST.Expression.Expression;
import AST.Factor.Factor;
import AST.Factor.NumberValueFactor;
import AST.Factor.VariableFactor;
import AST.Statement.ReturnStatement;
import AST.Statement.Statement;
import Program.Context;

import java.util.ArrayList;

public class FuncCall extends Factor{
    String name;
    ArrayList<Factor> arguments;
    public FuncCall(String name, ArrayList<Factor> arguments){
        this.name=name;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        String argsString="";
        for (Factor arg: arguments) {
            argsString+=arg.toString()+",";
        }
        return name+"("+argsString.substring(0, argsString.length() - 1)+")";
    }

    @Override
    public NumberValueFactor calculate(Context context) throws Exception {
        Context funcContext = new Context();
        FuncStatement funcStatement = context.getFunctionWithName(name);
        for(int i=0; i<arguments.size(); i++){
            funcContext.addVariable(funcStatement.arguments.get(i).toString(), arguments.get(i).calculate(context));
        }
        Statement returnStatement = funcStatement.statements.get(funcStatement.statements.size()-1);
        if(returnStatement instanceof ReturnStatement){
            String varToReturn = ((ReturnStatement) returnStatement).getVarToReturn();
            funcStatement.callFunc(funcContext);
            return funcContext.getValueForVariable(varToReturn);
        }
        else{
            throw new Exception("Missing return statement in function "+name);
        }

    }
}
