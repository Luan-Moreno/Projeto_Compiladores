package com.mycompany.projetocompleto_compiladores;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
    public static void main(String[] args) 
    {
        List<Token> tokens = new ArrayList<>();
        String data;
        data = "";
        // Testes
        //data = "if x1 == 1 then if x2 == 2 then if x3 == 3 then x4 = 4";
        //data = "if x1 == \'a\' then x2 = 1.1 else if x1 == \'b\' then x2 = True else if x1 == \'c\' then x2 = 3";
        //data = "while x1 == 2: x = 1.23456";
        //data = "for (int x = 3; x < 10; x++): while x1 == 2: x = 3";
        //data = "int x = 10";
        //data = "float x = 10";
        //data = "bool a = False";
        //data = "int a = False";
        //data = "char a = \"a\"";
        
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
