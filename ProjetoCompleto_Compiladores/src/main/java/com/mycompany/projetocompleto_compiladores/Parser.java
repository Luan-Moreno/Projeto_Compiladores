package com.mycompany.projetocompleto_compiladores;
import java.util.List;

public class Parser 
{
    List<Token> tokens;
    Token token;

    public Parser(List<Token> tokens) 
    {
        this.tokens = tokens;
    }
    
    public Token getNextToken()
    {
        if(!tokens.isEmpty())
        {
            return tokens.remove(0);
        }
        return null;
    }
    
    private void erro(String regra)
    {
        System.out.println("Sintaticamente Incorreto");
        System.out.println("Regra: " + regra);
        System.out.println("Token Invalido: " + token.lexema);
        System.exit(0);
    }
    
    public Tree mainParse() 
    {
       token = getNextToken();
       Node root = new Node("programa");
       
       if(ifelse(root))
       {
          if(token.tipo.equals("EOF"))
          {
              System.out.println("Sintaticamente Correto");
              root.addNode("fim do programa");
              return new Tree(root);
          }
       }
       else
       {
           erro("EOF");
       }
       return null;
    }
    
    public boolean matchL(String lexema)
    {
        if(token.lexema.equals(lexema))
        {
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    public boolean matchL(String lexema, Node node)
    {
        if(token.lexema.equals(lexema))
        {
            node.addNode(token.lexema);
            token = getNextToken();
            return true;
        }
        node.addNode("ERROR: Expected " + lexema + " Received: " + token.lexema);
        return false;
    }
    
    public boolean matchT(String tipo)
    {
        if(token.tipo.equals(tipo))
        {
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    public boolean matchT(String tipo, Node node)
    {
        if(token.tipo.equals(tipo))
        {
            node.addNode(token.lexema);
            token = getNextToken();
            return true;
        }
        node.addNode("ERROR: Expected " + tipo + " Received: " + token.tipo);
        return false;
    }
    
    public boolean id(Node node)
    {
        Node id = node.addNode("id");
        return matchT("id", id);
    }
    
    public boolean num(Node node)
    {
        Node num = node.addNode("num");
        return matchT("num", num);
    }
    
    public boolean operador(Node node)
    {
        Node operador = node.addNode("operador");
        return matchL(">", operador) || matchL("<", operador) || 
               matchL("==", operador) || matchL(">=", operador) || matchL("<=", operador);
    }
    
    public boolean condicao(Node node)
    {
        Node condicao = node.addNode("condicao");
        return id(condicao) && operador(condicao) && num(condicao);
    }
    
    
    public boolean atribuicao(Node node)
    {
        Node atribuicao = node.addNode("atribuicao");
        return matchT("reservada_tipo_inteiro", atribuicao) && matchT("id", atribuicao) && matchL("=", atribuicao) && matchT("num", atribuicao) ||
               matchT("reservada_tipo_decimal", atribuicao) && matchT("id", atribuicao) && matchL("=", atribuicao) && matchT("decimal", atribuicao) ||
               matchT("reservada_tipo_texto", atribuicao) && matchT("id", atribuicao) && matchL("=", atribuicao) && matchT("string", atribuicao) ||
               matchT("reservada_tipo_caracter", atribuicao) && matchT("id", atribuicao) && matchL("=", atribuicao) && matchT("char", atribuicao) ||
               matchT("reservada_tipo_booleano", atribuicao) && matchT("id", atribuicao) && matchL("=", atribuicao) && matchT("bool", atribuicao);
    }
    
    
    public boolean expressao(Node node)
    {
        Node expressao = node.addNode("expressao");
        return matchT("id", expressao) && matchL("=", expressao) && (matchT("num", expressao) || 
               matchT("tipo_booleano", expressao) || matchT("string", expressao) || matchT("char", expressao));
    }
    
    public boolean somatorio(Node node)
    {
        Node somatorio = node.addNode("somatorio");
        return matchT("id", somatorio) && (matchL("+", somatorio) || matchL("-", somatorio)) && (matchL("+", somatorio) || matchL("-", somatorio));
    }
    
    public boolean ifelse(Node node)
    {
        Node ifelse = node.addNode("ifelse");
        return matchL("if", ifelse) && condicao(ifelse) && matchL("then", ifelse);
        /*{
            if(expressao(ifelse) || ifelse(ifelse) || enquanto(ifelse) || para(ifelse))
            {
                if(matchL("else")) 
                {
                    if(expressao(ifelse) || ifelse(ifelse) || enquanto(ifelse) || para(ifelse)) 
                    {
                        return true;
                    } 
                else return expressao(ifelse);
                }
                return true;
            }
        }
        return false;*/
    }
    
    public boolean enquanto(Node node)
    {
       Node enquanto = node.addNode("enquanto");
       if(matchL("while", enquanto) && condicao(enquanto) && matchL(":", enquanto))
       {
           if(expressao(enquanto) || ifelse(enquanto) || enquanto(enquanto) || para(enquanto))
           {
               return true;
           }
       }
       return false;
    }
    
    public boolean para(Node node)
    {
       Node para = node.addNode("para");
       if(matchL("for", para) && matchL("(", para) && atribuicao(para) && matchL(";", para) && condicao(para) && 
         matchL(";", para) && somatorio(para) && matchL(")", para)  && matchL(":", para))
       {
           if(expressao(para) || ifelse(para) || enquanto(para) || para(para))
           {
               return true;
           }
       }
       return false;
    }
    
}
