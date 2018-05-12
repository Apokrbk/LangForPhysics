package AST.Factor;

public class NumberValueFactor extends Factor{

    public int getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    private int value;
    private String unit;



    public NumberValueFactor(int value, String unit){
        this.value=value;
        this.unit=unit;
    }

    @Override
    public String toString() {
        return value+"["+unit+"]";
    }

    public boolean isSameUnit(NumberValueFactor arg2){
        return this.unit.equals(arg2.unit);
    }

    public void refactorUnit(){

    }

}
