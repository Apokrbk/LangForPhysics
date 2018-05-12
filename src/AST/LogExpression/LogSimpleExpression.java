package AST.LogExpression;

import AST.Expression.Expression;
import AST.Factor.NumberValueFactor;
import Program.Context;

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

    @Override
    public NumberValueFactor calculate(Context context) throws Exception {
        NumberValueFactor result;
        NumberValueFactor leftFactor = leftOperand.calculate(context);
        NumberValueFactor rightFactor = rightOperand.calculate(context);
        if(leftFactor.getUnit().equals(rightFactor.getUnit())) {
            switch (operator.getOpType()) {
                case EQ:
                    if (leftFactor.getValue()==rightFactor.getValue())
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
                case GE:
                    if (leftFactor.getValue()>=rightFactor.getValue())
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
                case LE:
                    if (leftFactor.getValue()<=rightFactor.getValue())
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
                case GT:
                    if (leftFactor.getValue()>rightFactor.getValue())
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
                case LT:
                    if (leftFactor.getValue()<rightFactor.getValue())
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
                case OR:
                    if (leftFactor.getUnit().equals("") && (leftFactor.getValue()==1 || rightFactor.getValue()==1))
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
                case AND:
                    if (leftFactor.getUnit().equals("") && (leftFactor.getValue()==1 && rightFactor.getValue()==1))
                        result=new NumberValueFactor(1,"");
                    else
                        result=new NumberValueFactor(0,"");
                    return result;
            }
        }
            throw new Exception("Trying to compare numbers with different unit");
    }
}
