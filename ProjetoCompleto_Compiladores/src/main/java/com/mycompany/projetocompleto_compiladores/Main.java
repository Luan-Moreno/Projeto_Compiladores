package com.mycompany.projetocompleto_compiladores;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
    {
        List<Token> tokens = new ArrayList<>();
        String data = "";
        
        // Testes
        //if x1 == 1 then if x2 == 2 then if x3 == 3 then x4 = 4
        //if x1 == \'a\' then x2 = 1.1 else if x1 == \'b\' then x2 = True else if x1 == \'c\' then x2 = 3
        //while x1 == 2: x = 1.23456
        //for (int x = 3; x < 10; x++): while x1 == 2: x = 3
        //int x = 10
        //float x = 10
        //bool a = False
        //int a = False
        //char a = \"a\"
        //1 + 2 * 3 / 5 * 2 + 2 - 1
        //impressao(x)
        //impressao(True)
        
        try 
        {
            File arquivo = new File("src\\main\\java\\com\\mycompany\\projetocompleto_compiladores\\input.txt");
            Scanner scanner = new Scanner(arquivo);
            while (scanner.hasNextLine()) 
            {
                data += scanner.nextLine() + "\n";
                System.out.println(data);
            }
            scanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        Lexer lexer = new Lexer(data);
        tokens = lexer.getTokens();
        
        for (Token token : tokens) 
        {
            System.out.println(token);
        }
        
        System.out.println("\nLexicamente Correto\n");

        Parser parser = new Parser(tokens);
        Tree tree = parser.mainParse();
        tree.printTree();
    }
}
