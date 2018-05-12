package AST.Factor;

public class VariableFactor extends Factor{
    private String name;

    public VariableFactor(String name){
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}
