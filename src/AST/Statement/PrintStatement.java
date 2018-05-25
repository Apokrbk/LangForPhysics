package AST.Statement;

import Program.Context;

public class PrintStatement extends Statement{
    public String getVarToPrint() {
        return varToPrint;
    }

    String varToPrint;

    public PrintStatement(String varToPrint){
        this.varToPrint=varToPrint;
    }

    public void execute(Context context) throws Exception {
        System.out.println(varToPrint+" = "+context.getValueForVariable(varToPrint));
    }

    @Override
    public String toString() {
        return "print("+varToPrint+");";
    }
}
