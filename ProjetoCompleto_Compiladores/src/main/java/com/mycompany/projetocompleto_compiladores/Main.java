package com.mycompany.projetocompleto_compiladores;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
    public static void main(String[] args) 
    {
        List<Token> tokens = new ArrayList<>();
        //String data = "if x1 == \'a\' then x2 = 1.1 else if x1 == \'b\' then x2 = True else if x1 == \'c\' then x2 = 3";
        String data = "while x1 == 2:";
        
        Lexer lexer = new Lexer(data);
        tokens = lexer.getTokens();
        
        for(Token token : tokens)
        {
            System.out.println(token);
        }
        
        System.out.println("Lexicamente Correto");

        Parser parser = new Parser(tokens);
        parser.main();
    }
}
