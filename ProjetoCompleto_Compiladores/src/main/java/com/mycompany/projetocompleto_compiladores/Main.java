package com.mycompany.projetocompleto_compiladores;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
    public static void main(String[] args) 
    {
        List<Token> tokens = new ArrayList<>();
        String data = "if x1 == \'a\' then x2 = 2.2 else x3 = True";
        
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
