package AST.Expression;

import static AST.Expression.Operator.opType.*;

public class Operator {
    public enum opType{
        MLT,
        DIV,
        PLUS,
        MINUS
    }

    public opType getOptype() {
        return optype;
    }

    private opType optype;

    public Operator(String data){
        switch(data){
            case "*":
                this.optype=MLT;
                break;
            case "/":
                this.optype=DIV;
                break;
            case "+":
                this.optype=PLUS;
                break;
            case "-":
                this.optype=MINUS;
                break;
        }
    }

    @Override
    public String toString() {
        switch(optype){
            case MLT:
                return "*";
            case DIV:
                return "/";
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            default:
                return null;
        }
    }
}
