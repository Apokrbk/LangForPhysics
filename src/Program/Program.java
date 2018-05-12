package Program;

import AST.Statement.Statement;

import java.util.ArrayList;

public class Program {
    private ArrayList<Statement> statements;

    public Context getContext() {
        return context;
    }

    private Context context;


    public Program(){
        statements=new ArrayList<>();
        context=new Context();
    }
    public void addStatement(Statement statement){
        statements.add(statement);
    }
    @Override
    public String toString() {
        String result="";
        for (Statement statement: statements) {
            result+=statement.toString();
        }
        return result;
    }

    public void execute() throws Exception {
        for(Statement statement: statements) {
            statement.execute(context);
        }

    }
}
