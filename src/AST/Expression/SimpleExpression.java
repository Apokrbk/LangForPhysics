package AST.Expression;

import AST.Factor.NumberValueFactor;

public class SimpleExpression extends Expression {
    private Expression leftOperand;
    private Operator operator;
    private Expression rightOperand;

    public SimpleExpression(Expression leftOperand, Operator operator, Expression rightOperand){
        this.leftOperand=leftOperand;
        this.operator=operator;
        this.rightOperand=rightOperand;
    }

    @Override
    public String toString() {
        return "("+leftOperand.toString()+operator.toString()+rightOperand.toString()+")";
    }

    @Override
    public NumberValueFactor calculate() throws Exception {
        NumberValueFactor leftFactor=leftOperand.calculate();
        NumberValueFactor rightFactor=rightOperand.calculate();
        NumberValueFactor result;
        switch (operator.getOptype()){
            case MINUS:
                if(leftFactor.isSameUnit(rightFactor)){
                    result=new NumberValueFactor(leftFactor.getValue()-rightFactor.getValue(), leftFactor.getUnit());
                    result.refactorUnit();
                    return result;
                }
            case PLUS:
                if(leftFactor.isSameUnit(rightFactor)){
                    result=new NumberValueFactor(leftFactor.getValue()+rightFactor.getValue(), leftFactor.getUnit());
                    result.refactorUnit();
                    return result;
                }
            case DIV:
                result=new NumberValueFactor(leftFactor.getValue()*rightFactor.getValue(), leftFactor.getUnit()+"*"+rightFactor.getUnit());
                result.refactorUnit();
                return result;
            case MLT:
                result=new NumberValueFactor(leftFactor.getValue()/rightFactor.getValue(), leftFactor.getUnit()+"*"+rightFactor.getUnit());
                result.refactorUnit();
                return result;
        }
        throw new Exception("ERROR");
    }
}
