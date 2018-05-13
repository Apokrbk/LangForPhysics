import Lexer.*;
import Parser.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import Program.*;

public class Main {
    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) throws Exception {
        String input = null;
        try {
            input = readFile("src/sourcecode.txt", StandardCharsets.UTF_8);
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Program program = parser.parse();
        program.execute();
        program.getContext().printVariables();


    }
}
