package AST.Factor;

import Program.Context;

public class VariableFactor extends Factor{
    private String name;

    public VariableFactor(String name){
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public NumberValueFactor calculate(Context context) throws Exception {
        return context.getValueForVariable(name);
    }
}
