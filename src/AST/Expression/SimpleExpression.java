package AST.Expression;

import AST.Factor.NumberValueFactor;
import Program.Context;

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
    public NumberValueFactor calculate(Context context) throws Exception {
        NumberValueFactor leftFactor=leftOperand.calculate(context);
        NumberValueFactor rightFactor=rightOperand.calculate(context);
        NumberValueFactor result;
        switch (operator.getOptype()){
            case MINUS:
                if(leftFactor.isSameUnit(rightFactor)){
                    result=new NumberValueFactor(leftFactor.getValue()-rightFactor.getValue(), leftFactor.getUnit());
                    return result;
                }
                throw new Exception("Trying to sum numbers with different unit");
            case PLUS:
                if(leftFactor.isSameUnit(rightFactor)){
                    result=new NumberValueFactor(leftFactor.getValue()+rightFactor.getValue(), leftFactor.getUnit());
                    return result;
                }
                throw new Exception("Trying to sum numbers with different unit");
            case DIV:
                if(leftFactor.getUnit().equals("") && rightFactor.getUnit().equals("")){
                    result=new NumberValueFactor(leftFactor.getValue()/rightFactor.getValue(), leftFactor.getUnit());
                }
                else if(rightFactor.getUnit().equals("")){
                    result=new NumberValueFactor(leftFactor.getValue()/rightFactor.getValue(), leftFactor.getUnit());
                }
                else if(leftFactor.getUnit().equals("")){
                    result=new NumberValueFactor(leftFactor.getValue()/rightFactor.getValue(), rightFactor.getUnit());
                }
                else{
                    rightFactor.reverseUnit();
                    result=new NumberValueFactor(leftFactor.getValue()/rightFactor.getValue(), leftFactor.getUnit()+"/"+rightFactor.getUnit());
                }
                return result;
            case MLT:
                if(leftFactor.getUnit().equals("") && rightFactor.getUnit().equals("")){
                    result=new NumberValueFactor(leftFactor.getValue()*rightFactor.getValue(), leftFactor.getUnit());
                }
                else if(rightFactor.getUnit().equals("")){
                    result=new NumberValueFactor(leftFactor.getValue()*rightFactor.getValue(), leftFactor.getUnit());
                }
                else if(leftFactor.getUnit().equals("")){
                    result=new NumberValueFactor(leftFactor.getValue()*rightFactor.getValue(), rightFactor.getUnit());
                }
                else{
                    result=new NumberValueFactor(leftFactor.getValue()*rightFactor.getValue(), leftFactor.getUnit()+"*"+rightFactor.getUnit());
                }
                return result;
        }
        throw new Exception("ERROR");
    }
}
