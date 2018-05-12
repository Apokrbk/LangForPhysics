package AST.LogExpression;
import static AST.LogExpression.LogOperator.OpType.*;

public class LogOperator {
    enum OpType{
        EQ,
        GT,
        LT,
        GE,
        LE,
        AND,
        OR
    }
    private OpType opType;
    public LogOperator(String operator){
        switch(operator){
            case "==":
                this.opType=EQ;
                break;
            case "<":
                this.opType=LT;
                break;
            case ">":
                this.opType=GT;
                break;
            case "<=":
                this.opType=LE;
                break;
            case ">=":
                this.opType=GE;
                break;
            case "and":
                this.opType=AND;
                break;
            case "or":
                this.opType=OR;
        }
    }

    @Override
    public String toString() {
        switch(opType){
            case EQ:
                return "==";
            case LT:
                return "<";
            case GT:
                return ">";
            case LE:
                return "<=";
            case GE:
                return ">=";
            case AND:
                return " and ";
            case OR:
                return " or ";
        }
        return null;
    }
}
