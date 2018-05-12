package AST.Statement;

public class ReturnStatement extends Statement{
    String varToReturn;

    public ReturnStatement(String varToReturn){
        this.varToReturn=varToReturn;
    }

    @Override
    public String toString() {
        return "return "+varToReturn+";";
    }
}
