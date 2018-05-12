package Parser;

import Program.Program;
import Lexer.Lexer;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parseSimpleAssignmentIdPlusId() throws Exception {
        Lexer lexer = new Lexer("x=a+b;");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=(a+b);", statement);
    }
    @Test
    public void parseSimpleAssignmentId() throws Exception {
        Lexer lexer = new Lexer("x=a;");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=a;", statement);
    }
    @Test
    public void parseSimpleAssignmentIdPlusIdPludId() throws Exception {
        Lexer lexer = new Lexer("x=a+b+c;");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=((a+b)+c);", statement);
    }
    @Test
    public void parseSimpleAssignmentIdPlusIdPlusIdPlusIdPlusId() throws Exception {
        Lexer lexer = new Lexer("x=a+b+c+d+e;");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=((((a+b)+c)+d)+e);", statement);
    }
    @Test
    public void parseSimpleAssignmentIdMltIdPlusId() throws Exception {
        Lexer lexer = new Lexer("x=a*b+c");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=((a*b)+c);", statement);
    }
    @Test
    public void parseSimpleAssignmentIdss() throws Exception {
        Lexer lexer = new Lexer("x=a*b+c*d");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=((a*b)+(c*d));", statement);
    }
    @Test
    public void parseSimpleAssignmentIdPlusIdMltId() throws Exception {
        Lexer lexer = new Lexer("x=a+b*c");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=(a+(b*c));", statement);
    }
    @Test
    public void parseSimpleAssignmentIdMltIdPlusIdMltId() throws Exception {
        Lexer lexer = new Lexer("x=a*b+c*d");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=((a*b)+(c*d));", statement);
    }
    @Test
    public void parseSimpleAssignmentIdMltIdMltIdPlusId() throws Exception {
        Lexer lexer = new Lexer("x=a*b*c+d");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=((a*(b*c))+d);", statement);
    }
    @Test
    public void parseSimpleAssignmentIdMltIdMltIdMltId() throws Exception {
        Lexer lexer = new Lexer("x=a*b*c*d");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=(a*((b*c)*d));", statement);
    }
    @Test
    public void parseSimpleAssignmentIdMltIdMltIdMltIdMltId() throws Exception {
        Lexer lexer = new Lexer("x=a*b*c*d*e");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=(a*(((b*c)*d)*e));", statement);
    }

    @Test
    public void parseIfStatementAgtB() throws Exception {
        Lexer lexer = new Lexer("if(a>b){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("if((a>b)){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseIfStatementAgtBOrCltD() throws Exception {
        Lexer lexer = new Lexer("if(a>b or c<d){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("if(((a>b) or (c<d))){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseIfStatementAgtBgtC() throws Exception {
        Lexer lexer = new Lexer("if(a>b>c){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("if(((a>b)>c)){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseIfStatementAandCOrDandB() throws Exception {
        Lexer lexer = new Lexer("if(a and c or d and b){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("if(((a and c) or (d and b))){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseProgramIf() throws Exception {
        Lexer lexer = new Lexer("a=5[kg]; b=4[kg]; if(a>b or b>a){x=125[s];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=5[kg];b=4[kg];if(((a>b) or (b>a))){\n" +
                "x=125[s];}", statement);
    }

    @Test
    public void parseHugeIf() throws Exception {
        Lexer lexer = new Lexer("if(a>b or a<d and b>d){x=125[s];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("if(((a>b) or ((a<d) and (b>d)))){\n" +
                "x=125[s];}", statement);
    }

    @Test
    public void parseAssignmentWithFuncCallWithIds() throws Exception{
        Lexer lexer = new Lexer("a=_beta(a,b);");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=_beta(a,b);", statement);
    }

    @Test
    public void parseAssignmentWithFuncCallWithIdAndNumber() throws Exception{
        Lexer lexer = new Lexer("a=_beta(5[kg],a);");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=_beta(5[kg],a);", statement);
    }

    @Test
    public void parseAssignmentWithFuncCallWithIdsAndNumbers() throws Exception{
        Lexer lexer = new Lexer("a=_beta(5[kg],a, b, ola, 2[s]);");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=_beta(5[kg],a,b,ola,2[s]);", statement);
    }

    @Test
    public void parseAssignmentWithNumbers1() throws Exception{
        Lexer lexer = new Lexer("a=1[kg]+3[s]*2[m]");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=(1[kg]+(3[s]*2[m]));", statement);
    }

    @Test
    public void parseAssignmentWithNumbers2() throws Exception{
        Lexer lexer = new Lexer("a=1[kg]+3[s]*2[m]+3[kg]");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=((1[kg]+(3[s]*2[m]))+3[kg]);", statement);
    }

    @Test
    public void parseAssignmentWithNumbersAndIds() throws Exception{
        Lexer lexer = new Lexer("a=1[kg]+a*2[m]+b");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=((1[kg]+(a*2[m]))+b);", statement);
    }

    @Test
    public void parseFuncDeclaration() throws Exception{
        Lexer lexer = new Lexer("func _alpha(a,b){x=a+b; return x;}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("func _alpha(a,b){ \nx=(a+b);\nreturn x;}", statement);
    }

    @Test
    public void parseWhileStatementAgtB() throws Exception {
        Lexer lexer = new Lexer("while(a>b){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("while((a>b)){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseWhileStatementAgtBOrCltD() throws Exception {
        Lexer lexer = new Lexer("while(a>b or c<d){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("while(((a>b) or (c<d))){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseWhileStatementAgtBgtC() throws Exception {
        Lexer lexer = new Lexer("while(a>b>c){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("while(((a>b)>c)){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseWhileStatementAandCOrDandB() throws Exception {
        Lexer lexer = new Lexer("while(a and c or d and b){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("while(((a and c) or (d and b))){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }

    @Test
    public void parseProgramWhile() throws Exception {
        Lexer lexer = new Lexer("a=5[kg]; b=4[kg]; while(a>b or b>a){x=125[s];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("a=5[kg];b=4[kg];while(((a>b) or (b>a))){\n" +
                "x=125[s];}", statement);
    }

    @Test
    public void parseHugeWhile() throws Exception {
        Lexer lexer = new Lexer("while(a>b or a<d and b>d){x=125[s];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("while(((a>b) or ((a<d) and (b>d)))){\n" +
                "x=125[s];}", statement);
    }

    @Test
    public void parseUnitWithManyUnits() throws Exception {
        Lexer lexer = new Lexer("x = 2[kg*m/s/s]");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=2[N];", statement);
    }

    @Test(expected = Exception.class)
    public void parseUnitWithManyUnitsError() throws Exception {
        Lexer lexer = new Lexer("x = 2[kg+m-s/s]");
        Parser parser = new Parser(lexer);
        parser.parse();
    }

    @Test
    public void parseIfTwoConditionsWithAnd() throws Exception {
        Lexer lexer = new Lexer("if(a>b and b>a){a=2[];a=3[];}");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("if(((a>b) and (b>a))){" +"\n"+
                "a=2[];" + "\n"+
                "a=3[];}", statement);
    }
}