package AST.LogExpression;

import AST.Expression.Expression;

public class LogSimpleExpression extends LogExpression{
    private Expression leftOperand;
    private LogOperator operator;
    private Expression rightOperand;

    public LogSimpleExpression(Expression leftOperand, LogOperator logOperator, Expression rightOperand){
        this.leftOperand=leftOperand;
        this.operator=logOperator;
        this.rightOperand=rightOperand;
    }

    @Override
    public String toString() {
        return "("+leftOperand.toString()+operator.toString()+rightOperand.toString()+")";
    }
}
