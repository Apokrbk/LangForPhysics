import Lexer.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) {
        String input = null;
        try {
            input = readFile("src/test.txt", StandardCharsets.UTF_8);
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Lexer lexer = new Lexer(input);
        ArrayList<Token> tokens = new ArrayList<>();
        int errors=0;
       Token token=lexer.nextToken();
        if(token.getType() == Token.TokenType.ERROR){
            System.out.println("LINE: " + token.getLineNumber() + " ERROR: " + token.getData());
            errors++;
        }
        while(token.getType() != Token.TokenType.END){
            tokens.add(token);
            token=lexer.nextToken();
            if(token.getType() == Token.TokenType.ERROR){
                System.out.println("LINE: " + token.getLineNumber() + " ERROR: " + token.getData());
                errors++;
            }
        }
        for (Token token2 : tokens)
            System.out.println(token2.getLineNumber()+"  "+token2);
    }
}
