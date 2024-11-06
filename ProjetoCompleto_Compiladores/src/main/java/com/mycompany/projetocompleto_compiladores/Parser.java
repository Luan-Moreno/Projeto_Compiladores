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
    }
    
    private void erroT(String tipo, Node node)
    {
        node.addNode("ERROR: Expected " + tipo + "| Received: " + token.tipo);
    }
    
    private void erroL(String lexema, Node node)
    {
        node.addNode("ERROR: Expected " + lexema + "| Received: " + token.lexema);
    }
    
    public Tree mainParse() 
    {
       token = getNextToken();
       Node root = new Node("início do programa");
       
       if(atribuicao(root) || expressao(root) || conta(root) || ifelse(root))
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
           root.addNode("fim do programa");
           return new Tree(root);
       }
       return null;
    }
    
    
    public boolean matchL(String lexema, Node node)
    {
        if(token.lexema.equals(lexema))
        {
            node.addNode(token.lexema);
            token = getNextToken();
            return true;
        }
        erroL(lexema, node);
        return false;
    }
    
    public boolean NmatchL(String lexema, Node node)
    {
        if(token.lexema.equals(lexema))
        {
            node.addNode(token.lexema);
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
        erroT(tipo, node);
        return false;
    }
    
    public boolean NmatchT(String tipo, Node node)
    {
        if(token.tipo.equals(tipo))
        {
            node.addNode(token.lexema);
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    public boolean id(Node node)
    {
        Node id = node.addNode("id");
        if(NmatchT("id", id))
        {
            return true;
        }
        erroT("id", id);
        return false;
    }
    
    public boolean num(Node node)
    {
        Node num = node.addNode("num");
        if(NmatchT("num", num))
        {
            return true;
        }
        //erroT("num", num);
        return false;
    }
    
    public boolean operador(Node node)
    {
        Node operador = node.addNode("operador");
        if (NmatchL(">", operador) || NmatchL("<", operador) 
                || NmatchL("==", operador) || NmatchL(">=", operador) 
                || NmatchL("<=", operador) || NmatchL("!=", operador))
        {
            return true;
        }
        erroL("Operator: >, <, ==, !=, >=, <=", operador);
        return false;
    }
    

    public boolean conta(Node node)
    {
        Node conta = node.addNode("conta");

        if (matchT("num", conta))
        {
            return contaOperacoes(conta);
        }

        return false;
    }

    private boolean contaOperacoes(Node node)
    {
        while (true)
        {
            if (NmatchL("+", node) || NmatchL("-", node) || NmatchL("*", node) 
                || NmatchL("/", node) || NmatchL("**", node))
            {
                if (matchT("num", node))
                {
                    continue;
                }
                return false;
            }
            break;
        }

        return true;
    }

    
    public boolean condicao(Node node)
    {
        Node condicao = node.addNode("condicao");
        return id(condicao) && operador(condicao) && (NmatchT("tipo_booleano", condicao) || num(condicao));
    }
    
    
    public boolean atribuicao(Node node)
    {
        Node atribuicao = node.addNode("atribuicao");
        if(NmatchT("reservada_tipo_inteiro", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("num", atribuicao) ||
           NmatchT("reservada_tipo_decimal", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("decimal", atribuicao) ||
           NmatchT("reservada_tipo_texto", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("string", atribuicao) ||
           NmatchT("reservada_tipo_caracter", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("char", atribuicao) ||
           NmatchT("reservada_tipo_booleano", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("bool", atribuicao))
        {
            return true;
        }
        //erroT("Definition with correct types: int, float, string, char or bool", atribuicao);
        return false;
    }
    
    
    public boolean expressao(Node node)
    {
        Node expressao = new Node("expressao");
        if (NmatchT("id", expressao) && NmatchL("=", expressao) && 
            (NmatchT("num", expressao) || NmatchT("tipo_booleano", expressao) || 
            NmatchT("string", expressao) || NmatchT("char", expressao)))
        {
            node.addNode(expressao);
            return true;
        }
        erroT("Expression of type: id = num/bool/string/char", expressao);
        //node.addNode("expressao");
        return false;
    }
    
    public boolean somatorio(Node node)
    {
        Node somatorio = node.addNode("somatorio");
        if (NmatchT("id", somatorio) && (NmatchL("+", somatorio) || 
            NmatchL("-", somatorio)) && (NmatchL("+", somatorio) || NmatchL("-", somatorio)))
        {
            return true;
        }
        erroL("Sum of type: id++ / id--", somatorio);
        return false;
    }
    
    public boolean ifelse(Node node)
    {
        //Node ifelse = node.addNode("ifelse");
        Node ifelse = new Node("ifelse");
        Node temp = ifelse;
        if (NmatchL("if", ifelse) && condicao(ifelse) && NmatchL("then", ifelse))
        {
            if(expressao(ifelse) || ifelse(ifelse) || enquanto(ifelse) || para(ifelse))
            {
                if(NmatchL("else", ifelse)) 
                {
                    if(expressao(ifelse) || ifelse(ifelse) || enquanto(ifelse) || para(ifelse)) 
                    {
                        node.addNode(ifelse);
                        return true;
                    } 
                    else
                    {
                       node.addNode(ifelse);
                       return expressao(ifelse);
                    }
                }
                node.addNode(ifelse);
                return true;
            }
        }
        //erroL("ifelse() format", ifelse);
        node.addNode(ifelse);
        return false;
    }
    
    public boolean enquanto(Node node)
    {
       //Node enquanto = node.addNode("enquanto");
       Node enquanto = new Node("enquanto");
       if(NmatchL("while", enquanto) && condicao(enquanto) && NmatchL(":", enquanto))
       {
           if(expressao(enquanto) || ifelse(enquanto) || enquanto(enquanto) || para(enquanto))
           {
               node.addNode(enquanto);
               return true;
           }
       }
       //erroL("while() format", enquanto);
       node.addNode(enquanto);
       return false;
    }
    
    public boolean para(Node node)
    {
       //Node para = node.addNode("para");
       Node para = new Node("para");
       if(NmatchL("for", para) && NmatchL("(", para) && atribuicao(para) && NmatchL(";", para) && condicao(para) && 
         NmatchL(";", para) && somatorio(para) && NmatchL(")", para)  && NmatchL(":", para))
       {
           if(expressao(para) || ifelse(para) || enquanto(para) || para(para))
           {
               node.addNode(para);
               return true;
           }
       }
       //erroL("for() format", para);
       node.addNode(para);
       return false;
    }
    
}
