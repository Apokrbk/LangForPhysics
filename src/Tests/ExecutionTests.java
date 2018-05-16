package Tests;

import Lexer.Lexer;
import Parser.Parser;
import Program.Program;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionTests {

    @Test
    public void simpleSumOfTwoFactorsWithUnits() throws Exception {
        Lexer lexer = new Lexer("x=5[kg]+3[kg];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(8, program.getContext().getValueForVariable("x").getValue());
        assertEquals("kg", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void simpleSumOfTwoFactorsWithoutUnits() throws Exception {
        Lexer lexer = new Lexer("x=3[]+9[];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(12, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test(expected = Exception.class)
    public void sumOfTwoFactorsWithDifferentUnitsThrowsException() throws Exception {
        Lexer lexer = new Lexer("x=3[kg]+9[s];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
    }

    @Test
    public void simpleMultiplicationOfTwoFactorsWithUnits() throws Exception {
        Lexer lexer = new Lexer("x=5[kg]*3[kg];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(15, program.getContext().getValueForVariable("x").getValue());
        assertEquals("kg*kg", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void simpleMultiplicationOfTwoFactorsWithDifferentUnits() throws Exception {
        Lexer lexer = new Lexer("x=5[kg]*3[s];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(15, program.getContext().getValueForVariable("x").getValue());
        assertEquals("kg*s", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void simpleMultiplicationOfTwoFactorsWithoutUnits() throws Exception {
        Lexer lexer = new Lexer("x=3[]*9[];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(27, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void expressionMultiplicationBeforeSum() throws Exception {
        Lexer lexer = new Lexer("x=2[]+3[]*9[];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(29, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void expressionMultiplicationBeforeSum2() throws Exception {
        Lexer lexer = new Lexer("x=2[]*3[]+9[];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(15, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void expressionMultiplicationBeforeSum3() throws Exception {
        Lexer lexer = new Lexer("x=2[]*3[]+9[]+10[]*2[];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(35, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void kgMultMDivSDivS() throws Exception {
        Lexer lexer = new Lexer("x=2[kg];y=10[m/s];z=4[s];res=x*y/z;");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(5, program.getContext().getValueForVariable("res").getValue());
        assertEquals("N", program.getContext().getValueForVariable("res").getUnit());
    }

    @Test
    public void ifStatementTrue() throws Exception {
        Lexer lexer = new Lexer("x=10[]; if(2[]>1[]){x=15[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(15, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void ifStatementTrue2() throws Exception {
        Lexer lexer = new Lexer("x=10[]; if(2[]>1[] and 10[kg]>8[kg]){x=15[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(15, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void ifStatementTrue3() throws Exception {
        Lexer lexer = new Lexer("x=10[]; if(2[]<1[] and 10[kg]>8[kg] or 1[]==1[]){x=15[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(15, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void ifStatementFalse() throws Exception {
        Lexer lexer = new Lexer("x=10[]; if(2[]<1[]){x=15[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(10, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void ifStatementFalse2() throws Exception {
        Lexer lexer = new Lexer("x=10[]; if(2[]>1[] and 1[kg]>10[kg]){x=15[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(10, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void ifStatementFalse3() throws Exception {
        Lexer lexer = new Lexer("x=10[]; if(2[]<1[] or 3[kg*m]<0[kg*m]){x=15[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(10, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void whileIterate3Times() throws Exception {
        Lexer lexer = new Lexer("x=10[]; i=0[];while(i<3[]){x=x+1[]; i=i+1[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(13, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void whileIterate30Times() throws Exception {
        Lexer lexer = new Lexer("x=10[]; i=0[];while(i<30[]){x=x+1[]; i=i+1[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(40, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void whileIterate0Times() throws Exception {
        Lexer lexer = new Lexer("x=10[]; i=0[];while(i>30[]){x=x+1[]; i=i+1[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(10, program.getContext().getValueForVariable("x").getValue());
        assertEquals("", program.getContext().getValueForVariable("x").getUnit());
    }

    @Test
    public void funcCallTest1() throws Exception {
        Lexer lexer = new Lexer("func _a(a,b){x=a+b; return x;} a1=10[kg]; a2=15[kg]; t=_a(a1,a2);");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(25, program.getContext().getValueForVariable("t").getValue());
        assertEquals("kg", program.getContext().getValueForVariable("t").getUnit());
    }

    @Test
    public void funcCallAsAnArgumentInFuncCallTest1() throws Exception {
        Lexer lexer = new Lexer("func _a(a,b){x=a+b; return x;} a1=10[kg]; a2=15[kg]; t=_a(_a(a1,3[kg]),a2);");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(28, program.getContext().getValueForVariable("t").getValue());
        assertEquals("kg", program.getContext().getValueForVariable("t").getUnit());
    }

    @Test
    public void funcCallInsideFuncCallTest1() throws Exception {
        Lexer lexer = new Lexer("func _a(a,b){" +
                "x=a+b; " +
                "return x;" +
                "} " +
                "func _b(a1,a2){" +
                "x=_a(a1,a2); " +
                "return x;" +
                "} " +
                "a1=10[kg]; " +
                "a2=15[kg]; " +
                "t=_a(_a(a1,3[kg]),a2);");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        assertEquals(28, program.getContext().getValueForVariable("t").getValue());
        assertEquals("kg", program.getContext().getValueForVariable("t").getUnit());
    }

}
