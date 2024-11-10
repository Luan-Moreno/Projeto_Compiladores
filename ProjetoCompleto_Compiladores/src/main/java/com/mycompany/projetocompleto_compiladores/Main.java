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
        
        try 
        {
            File arquivo = new File("src\\main\\java\\com\\mycompany\\projetocompleto_compiladores\\input.txt");
            Scanner scanner = new Scanner(arquivo); 
            while (scanner.hasNextLine()) 
            {
                data += scanner.nextLine() + "\n";
                //System.out.println(data);
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
        
        System.out.println("\nLexicamente Correto");

        Parser parser = new Parser(tokens);
        Tree tree = parser.mainParse();
        tree.printTree();
    }
}
