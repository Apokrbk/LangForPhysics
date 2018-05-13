package Tests;

import Lexer.Lexer;
import Parser.Parser;
import Program.Program;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTests {
    @Test
    public void parseSimpleUnitAssignment() throws Exception {
        Lexer lexer = new Lexer("x=5[kg];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=5[kg];", statement);
    }
    @Test
    public void parseSimpleUnitAssignmentWithNewton() throws Exception {
        Lexer lexer = new Lexer("x=5[kg*m/s/s];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=5[N];", statement);
    }
    @Test
    public void parseSimpleUnitAssignmentWithJ() throws Exception {
        Lexer lexer = new Lexer("x=5[kg*m/s/s*m];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=5[J];", statement);
    }
    @Test
    public void parseSimpleUnitAssignmentWithW() throws Exception {
        Lexer lexer = new Lexer("x=5[kg*m/s/s*m/s];");
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        String statement=program.toString();
        assertEquals("x=5[W];", statement);
    }
}
