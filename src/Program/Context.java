package Program;

import AST.Factor.NumberValueFactor;
import AST.Func.FuncStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Context {

    private HashMap<String, NumberValueFactor> variables;
    private HashMap<String, FuncStatement> functions;

    public Context(){
        variables = new HashMap<>();
        functions = new HashMap<>();
    }
    public void addVariable(String name, NumberValueFactor value){
        variables.put(name,value);
    }
    public void addFunction(String name, FuncStatement function){
        functions.put(name, function);
    }
    public NumberValueFactor getValueForVariable(String var){
        return variables.get(var);
    }
    public FuncStatement getFunctionWithName(String fname){
        return functions.get(fname);
    }
    public void printVariables(){
        System.out.println(Arrays.asList(variables));
    }
}
