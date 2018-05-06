package Parser;
import Lexer.Lexer;

public class Parser {
    private Lexer lexer;
    public Parser(String input){
        lexer = new Lexer(input);
    }
}
