package AST.Factor;

import AST.Expression.Expression;
import Program.Context;

public abstract class Factor extends Expression{
    @Override
    public NumberValueFactor calculate(Context context) throws Exception {
        return super.calculate(context);
    }
}
