package AST.Factor;

import Program.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        refactorUnit();
    }

    @Override
    public String toString() {
        return value+"["+unit+"]";
    }

    public boolean isSameUnit(NumberValueFactor arg2){
        return this.unit.equals(arg2.unit);
    }

    public void refactorUnit(){
        String a[] = unit.split("\\*");
        ArrayList<String> denominator=new ArrayList<>();
        ArrayList<String> nominator=new ArrayList<>();
        divideUnitIntoNominatorAndDenominator(a, denominator, nominator);
        unit="";
        denominator.removeAll(Arrays.asList(null,""));
        nominator.removeAll(Arrays.asList(null,""));
        shortenFraction(denominator, nominator);
        checkN(denominator, nominator);
        checkJ(nominator);
        checkC(nominator);
        checkW(denominator, nominator);
        if(nominator.isEmpty() && denominator.contains("s") && denominator.size()==1){
            nominator.add("Hz");
            denominator.remove("s");
        }
        Collections.sort(nominator);
        Collections.sort(denominator);
        if(nominator.size()!=0)
            unit=nominator.get(0);
        else
            unit="";
        for(int i=1; i<nominator.size(); i++)
            unit+="*"+nominator.get(i);
        for (String aDenominator : denominator) unit += "/" + aDenominator;

    }

    private void checkW(ArrayList<String> denominator, ArrayList<String> nominator) {
        if(nominator.contains("J") && denominator.contains("s")){
            nominator.remove("J");
            denominator.remove("s");
            nominator.add("W");
        }
    }

    private void checkJ(ArrayList<String> nominator) {
        if(nominator.contains("N") && nominator.contains("m")){
            nominator.remove("N");
            nominator.remove("m");
            nominator.add("J");
        }
    }

    private void checkC(ArrayList<String> nominator) {
        if(nominator.contains("A") && nominator.contains("s")){
            nominator.remove("A");
            nominator.remove("s");
            nominator.add("C");
        }
    }

    public void reverseUnit(){
        unit = unit.replaceAll("\\*", "?");
        unit = unit.replaceAll("/", "*");
        unit = unit.replaceAll("\\?", "/");
    }

    private void divideUnitIntoNominatorAndDenominator(String[] a, ArrayList<String> denominator, ArrayList<String> nominator) {
        for(int i=0; i<a.length; i++){
            String c[] = a[i].split("/");
            for(int j=1; j<c.length; j++)
                denominator.add(c[j]);
            nominator.add(c[0]);
        }
    }

    private void shortenFraction(ArrayList<String> denominator, ArrayList<String> nominator) {
        Collections.sort(nominator);
        Collections.sort(denominator);
        ArrayList<String> licznikKopia = new ArrayList<>(nominator);
        ArrayList<String> mianownikKopia = new ArrayList<>(denominator);
        for(int i=0; i<licznikKopia.size(); i++){
            if (mianownikKopia.contains(licznikKopia.get(i))){
                nominator.remove(licznikKopia.get(i));
                denominator.remove(licznikKopia.get(i));
            }
        }
    }

    private void checkN(ArrayList<String> denominator, ArrayList<String> nominator) {
        if(nominator.contains("kg") && countStringOccurance(denominator,"s")>=2 && nominator.contains("m")){
            nominator.remove("kg");
            nominator.remove("m");
            denominator.remove("s");
            denominator.remove("s");
            nominator.add("N");
        }
    }

    int countStringOccurance(ArrayList<String> arr, String str){
        int count = 0;
        for (int i=0; i<arr.size(); i++) {
            if (str.equals(arr.get(i))) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public NumberValueFactor calculate(Context context) throws Exception {
        return this;
    }
}
