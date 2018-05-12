package AST.Expression;

import AST.Factor.NumberValueFactor;
import Program.Context;

public abstract class Expression {
    public NumberValueFactor calculate(Context context) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
