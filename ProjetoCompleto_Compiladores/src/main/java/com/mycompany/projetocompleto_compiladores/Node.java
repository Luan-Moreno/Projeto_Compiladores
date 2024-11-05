package com.mycompany.projetocompleto_compiladores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node 
{

    String nome;
    List<Node> nodes;
    String enter;
    String exit;

    public Node(String nome) 
    {
        this.nome = nome;
        nodes = new ArrayList<>();
        this.enter = "";
        this.exit = "";
    }

    public void addNode(Node newNode) 
    {
        nodes.add(newNode);
    }

    public Node addNode(String nodeName) 
    {
        Node newNode = new Node(nodeName);
        nodes.add(newNode);
        return newNode;
    }

    public Node addNode(String enter, String nodeName, String exit) 
    {
        Node newNode = new Node(nodeName);
        newNode.enter = enter;
        newNode.exit = exit;
        nodes.add(newNode);
        return newNode;
    }

    @Override
    public String toString() 
    {
        return this.enter + " " + this.nome + " " + this.exit;
    }


    public String getTree() 
    {
        System.out.println("AST");
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) 
    {
        buffer.append(prefix);
        buffer.append(nome);
        buffer.append('\n');
        for (Iterator<Node> it = nodes.iterator(); it.hasNext();) 
        {
            Node next = it.next();
            if (it.hasNext()) 
            {
                next.print(buffer, childrenPrefix + "+-- ", childrenPrefix + "|   ");
            } 
            else 
            {
                next.print(buffer, childrenPrefix + "`-- ", childrenPrefix + "    ");
            }
        }
    }
}
