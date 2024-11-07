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
       
       if(blocoInicial(root))
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
    
    public boolean Nverify(String aspecto, Node node, boolean tipo)
    {
        if(tipo == true && token.tipo.equals(aspecto))
        {
            return true;
        }
        
        if(token.lexema.equals(aspecto))
        {
            return true;
        }
        
        return false;
    }
    
    
    public boolean bloco(Node node) 
    {
        Node bloco = node.addNode("bloco");

        if (Nverify("id", bloco, true)) 
        {
            return expressao(bloco);  
        }

        if (Nverify("if", bloco, false)) 
        {
            return ifelse(bloco);
        }

        if (Nverify("while", bloco, false)) 
        {
            return enquanto(bloco); 
        }

        if (Nverify("for", bloco, false)) 
        {
            return para(bloco); 
        }

        return false;
    }

    
    public boolean blocoInicial(Node node) 
    {
        Node blocoinicial = node.addNode("main");

        if (Nverify("reservada_tipo_inteiro", blocoinicial, true) || 
            Nverify("reservada_tipo_decimal", blocoinicial, true) ||
            Nverify("reservada_tipo_texto", blocoinicial, true) ||
            Nverify("reservada_tipo_caracter", blocoinicial, true) ||
            Nverify("reservada_tipo_booleano", blocoinicial, true)) 
        {    
            return atribuicao(blocoinicial);  
        }

        if (Nverify("id", blocoinicial, true)) 
        {
            return expressao(blocoinicial);
        }

        if (Nverify("num", blocoinicial, true)) 
        {
            return conta(blocoinicial);
        }

        if (Nverify("if", blocoinicial, false)) 
        {
            return ifelse(blocoinicial);
        }

        if (Nverify("while", blocoinicial, false)) 
        {
            return enquanto(blocoinicial); 
        }

        if (Nverify("for", blocoinicial, false)) 
        {
            return para(blocoinicial); 
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

        while (true) 
        {
            if (!contaTermo(conta)) 
            {
                return false;
            }

            while (true) 
            {
                if (NmatchL("*", conta) || NmatchL("/", conta)) 
                {
                    if (!contaTermo(conta)) 
                    {
                        return false;
                    }
                } 
                else 
                {
                    break;
                }
            }

            while (true) 
            {
                if (NmatchL("+", conta) || NmatchL("-", conta)) 
                {
                    if (!contaTermo(conta)) 
                    {
                        return false;
                    }
                } 
                else 
                {
                    break;
                }
            }

            if (!NmatchL("+", conta) && !NmatchL("-", conta) && !NmatchL("*", conta) && !NmatchL("/", conta)) 
            {
                break;
            }
    }

    return true;
    }

    private boolean contaTermo(Node node) 
    {
        if (matchT("num", node)) 
        {
            return true;
        }

        if (NmatchT("id", node)) 
        {
            return true;
        }

        if (NmatchL("(", node)) 
        {
            if (!conta(node)) 
            {
                return false;
            }

            return NmatchL(")", node);
        }
        return false;
    }




    /*public boolean conta(Node node)
    {
        Node conta = node.addNode("conta");

        if (NmatchT("num", conta))
        {
            return contaOperacoes(conta);
        }
        
        if (NmatchT("id", conta)) 
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
    }*/

    
    public boolean condicao(Node node) 
    {
        Node condicao = node.addNode("condicao");
        return id(condicao)  && operador(condicao) &&
               (NmatchT("bool", condicao) || 
                NmatchT("num", condicao)  ||
                NmatchT("char", condicao) || 
                NmatchT("string", condicao)|| 
                conta(condicao));
    }


    
    
    public boolean atribuicao(Node node)
    {
        Node atribuicao = node.addNode("atribuicao");
        if(NmatchT("reservada_tipo_inteiro", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("num", atribuicao) ||
           NmatchT("reservada_tipo_decimal", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("num", atribuicao) ||
           NmatchT("reservada_tipo_texto", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("string", atribuicao) ||
           NmatchT("reservada_tipo_caracter", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("char", atribuicao) ||
           NmatchT("reservada_tipo_booleano", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && NmatchT("tipo_booleano", atribuicao)||
           NmatchT("reservada_tipo_caracter", atribuicao) && NmatchT("id", atribuicao) && NmatchL("=", atribuicao) && conta(atribuicao))
        {
            return true;
        }
        erroT("Definition with correct types: int, float, string, char or bool", atribuicao);
        return false;
    }
    
    
    public boolean expressao(Node node)
    {
        Node expressao = node.addNode("expressao");
        if (NmatchT("id", expressao) && NmatchL("=", expressao) && 
            (NmatchT("num", expressao) || NmatchT("tipo_booleano", expressao) || 
            NmatchT("string", expressao) || NmatchT("char", expressao)))
        {
            return true;
        }
        erroT("Expression of type: id = num/bool/string/char", expressao);
        return false;
    }
    
    public boolean somatorio(Node node)
    {
        Node somatorio = node.addNode("somatorio");
        if (NmatchT("id", somatorio) && ((NmatchL("+", somatorio) && (NmatchL("+", somatorio))) || 
            NmatchL("-", somatorio) && NmatchL("-", somatorio)))
        {
            return true;
        }
        erroL("Sum of type: id++ / id--", somatorio);
        return false;
    }
    
    public boolean ifelse(Node node)
    {
        Node ifelse = node.addNode("condicional");
        if (NmatchL("if", ifelse) && condicao(ifelse) && NmatchL("then", ifelse))
        {
            if(bloco(ifelse))
            {
                if(NmatchL("else", ifelse)) 
                {
                    if(bloco(ifelse)) 
                    {
                        return true;
                    } 
                    else
                    {
                       return expressao(ifelse);
                    }
                }
                return true;
            }
        }
        erroL("ifelse() format", ifelse);
        return false;
    }
    
    public boolean enquanto(Node node)
    {
       Node enquanto = node.addNode("enquanto");
       if(NmatchL("while", enquanto) && condicao(enquanto) && NmatchL(":", enquanto))
       {
           if(bloco(enquanto))
           {
               return true;
           }
       }
       erroL("while() format", enquanto);
       return false;
    }
    
    public boolean para(Node node)
    {
       Node para = node.addNode("para");
       if(NmatchL("for", para) && NmatchL("(", para) && atribuicao(para) && NmatchL(";", para) && condicao(para) && 
         NmatchL(";", para) && somatorio(para) && NmatchL(")", para)  && NmatchL(":", para))
       {
           if(bloco(para))
           {
               return true;
           }
       }
       erroL("for() format", para);
       return false;
    }
    
    public boolean leitura(Node node)
    {
       Node leitura = node.addNode("leitura");
       if(NmatchL("leitura", leitura))
       {
           return true;
       }
       erroL("leitura() format", leitura);
       return false;
    }
    
    public boolean escrita(Node node)
    {
       Node escrita = node.addNode("escrita");
       if(NmatchL("escrita", escrita))
       {
           return true;
       }
       erroL("escrita() format", escrita);
       return false;
    }
    
}
