package AST.Statement;

import AST.Expression.Expression;
import AST.Factor.VariableFactor;

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
}
