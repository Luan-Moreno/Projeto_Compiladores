package com.mycompany.projetocompleto_compiladores;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
    public static void main(String[] args) 
    {
        List<Token> tokens = new ArrayList<>();
        //String data = "if x1 == 1 then if x2 == 2 then if x3 == 3 then x4 = 4";
        String data = "if x1 == \'a\' then x2 = 1.1 else if x1 == \'b\' then x2 = True else if x1 == \'c\' then x2 = 3";
        //String data = "while x1 == 2:";
        
        Lexer lexer = new Lexer(data);
        tokens = lexer.getTokens();
        
        for(Token token : tokens)
        {
            System.out.println(token);
        }
        
        System.out.println("\nLexicamente Correto\n");

        Parser parser = new Parser(tokens);
        Tree tree = parser.mainParse();
        tree.printTree();
    }
}
