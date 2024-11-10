package com.mycompany.projetocompleto_compiladores;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main 
{
    public static void main(String[] args) 
    {
        JFrame janela = new JFrame("Compilador Jaguar");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1000, 400);
        janela.setLayout(new BorderLayout());
        janela.getContentPane().setBackground(Color.GRAY);

        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(new BorderLayout());
        painelEntrada.setBackground(Color.DARK_GRAY);
        
        JLabel legendaEntrada = new JLabel("Código Jaguar");
        legendaEntrada.setForeground(Color.WHITE);
        legendaEntrada.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea entrada = new JTextArea(10, 40);
        entrada.setBackground(Color.BLACK);
        entrada.setForeground(Color.GREEN);
        entrada.setCaretColor(Color.WHITE);
        entrada.setLineWrap(true);
        JScrollPane scrollEntrada = new JScrollPane(entrada);
        painelEntrada.add(legendaEntrada, BorderLayout.NORTH);
        painelEntrada.add(scrollEntrada, BorderLayout.CENTER);

        JPanel painelSaida = new JPanel();
        painelSaida.setLayout(new BorderLayout());
        painelSaida.setBackground(Color.DARK_GRAY);
        
        JLabel legendaSaida = new JLabel("Código C");
        legendaSaida.setForeground(Color.WHITE);
        legendaSaida.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea saida = new JTextArea(10, 40);
        saida.setBackground(Color.BLACK);
        saida.setForeground(Color.GREEN);
        saida.setCaretColor(Color.WHITE);
        saida.setLineWrap(true);
        
        JScrollPane scrollSaida = new JScrollPane(saida);
        painelSaida.add(legendaSaida, BorderLayout.NORTH);
        painelSaida.add(scrollSaida, BorderLayout.CENTER);

        JButton botao = new JButton("OK");
        botao.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String textoEntrada = entrada.getText();
                List<Token> tokens = new ArrayList<>();
                Lexer lexer = new Lexer(textoEntrada);
                boolean lexico;
                boolean sintatico;
                
                try 
                {
                    tokens = lexer.getTokens();
                    for (Token token : tokens) 
                    {
                        System.out.println(token);
                    }
                    lexico = true;
                } 
                catch (Exception ex) 
                {
                    lexico = false;
                    saida.setForeground(Color.RED);
                    saida.setText("Erro léxico: " + ex.getMessage());
                }
                
                System.out.println("\nLexicamente Correto");

                Parser parser = new Parser(tokens);
                Tree tree = parser.mainParse();
                tree.printTree();
                
                sintatico = parser.sintatico;
                System.out.println(sintatico);
                
                if(sintatico == false)
                {
                  saida.setForeground(Color.RED);
                  saida.setText(parser.erroSintatico);  
                }
                
                if(lexico && sintatico)
                {
                    try(BufferedReader leitor = new BufferedReader(new FileReader("src\\main\\java\\com\\mycompany\\projetocompleto_compiladores\\output.txt")))
                    {
                        String linha;
                        StringBuilder sb = new StringBuilder();

                        while ((linha = leitor.readLine()) != null) 
                        {
                            sb.append(linha).append("\n");
                        }
                        saida.setForeground(Color.GREEN);
                        saida.setText(sb.toString());
                    }
                    catch (IOException f) {}
                }
            }
        });

        janela.add(painelEntrada, BorderLayout.WEST);
        janela.add(painelSaida, BorderLayout.EAST);
        janela.add(botao, BorderLayout.SOUTH);

        janela.setVisible(true);       
    }
}
