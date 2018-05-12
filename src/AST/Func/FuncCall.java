package AST.Func;

import AST.Expression.Expression;
import AST.Factor.Factor;
import AST.Factor.VariableFactor;

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
}
