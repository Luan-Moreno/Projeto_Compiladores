package com.mycompany.projetocompleto_compiladores;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
    public static void main(String[] args) 
    {
        List<Token> tokens = new ArrayList<>();
        String data = "if x > 7.99 then x = .51 else x = .72";
        
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
