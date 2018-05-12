package AST.Statement;

import AST.Expression.Expression;
import AST.Factor.VariableFactor;
import Program.Context;

public class AssignStatement extends Statement{
    VariableFactor toBeAssigned;
    Expression toAssign;

    public AssignStatement(VariableFactor toBeAssigned, Expression toAssign){
        this.toAssign=toAssign;
        this.toBeAssigned=toBeAssigned;
    }

    @Override
    public String toString() {
        return toBeAssigned.toString()+"="+toAssign.toString()+";";
    }

    public void execute(Context context) throws Exception {
        context.addVariable(toBeAssigned.toString(), toAssign.calculate(context));
    }
}
