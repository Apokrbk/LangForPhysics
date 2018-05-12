package AST.Expression;

import AST.Factor.NumberValueFactor;

public abstract class Expression {
    public NumberValueFactor calculate() throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
