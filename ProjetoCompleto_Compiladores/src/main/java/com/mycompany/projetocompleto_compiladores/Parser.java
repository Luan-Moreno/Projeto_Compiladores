package com.mycompany.projetocompleto_compiladores;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;


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
    
    public void erro(String regra)
    {
        System.out.println("Sintaticamente Incorreto\n");
        System.out.println("Regra: " + regra);
        System.out.println("Token Invalido: " + token.lexema);
    }
    
    public void erroT(String tipo, Node node)
    {
        node.addNode("ERROR: Expected " + tipo + "| Received: " + token.tipo);
    }
    
    public void erroL(String lexema, Node node)
    {
        node.addNode("ERROR: Expected " + lexema + "| Received: " + token.lexema);
    }
    
    public Tree mainParse() 
    {
       token = getNextToken();
       Node root = new Node("início do programa");
       traduz("\n#include <stdio.h>\n\n");
       traduz("int main()\n");
       traduz("{\n");
       
       if(blocoInicial(root))
       {
          if(token.tipo.equals("EOF"))
          {
              traduz("return 0;\n");
              traduz("}");
              System.out.println("Sintaticamente Correto\n");
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
    
    public boolean matchL(String lexema, Node node, String newcode)
    {
        if(token.lexema.equals(lexema))
        {
            node.addNode(token.lexema);
            traduz(newcode);
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
    
    public boolean NmatchL(String lexema, Node node, String newcode)
    {
        if(token.lexema.equals(lexema))
        {
            node.addNode(token.lexema);
            traduz(newcode);
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    public boolean matchT(String tipo, Node node, String newcode)
    {
        if(token.tipo.equals(tipo))
        {
            node.addNode(token.lexema);
            traduz(newcode);
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
    
    public boolean NmatchT(String tipo, Node node, String newcode)
    {
        if(token.tipo.equals(tipo))
        {
            node.addNode(token.lexema);
            traduz(newcode);
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
            traduz("\n");
            return expressao(bloco);  
        }

        if (Nverify("if", bloco, false)) 
        {
            traduz("\n");
            return ifelse(bloco);
        }

        if (Nverify("enquanto", bloco, false)) 
        {
            traduz("\n");
            return enquanto(bloco); 
        }

        if (Nverify("para", bloco, false)) 
        {
            traduz("\n");
            return para(bloco); 
        }
        
        if(Nverify("impressao", bloco, false)) 
        {
            traduz("\n");
            return impressao(bloco);
        }
        
        if(Nverify("leitura", bloco, false)) 
        {
            traduz("\n");
            return leitura(bloco);
        }

        return false;
    }
  
    

    public boolean blocoInicial(Node node) 
    {
        Node blocoinicial = node.addNode("main");

        while (!Nverify("EOF", blocoinicial, true)) 
        {
            if (verificaTipo(blocoinicial)) 
            {    
                if (!atribuicao(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            if (Nverify("id", blocoinicial, true)) 
            {
                if (!expressao(blocoinicial)) return false; 
                traduz("\n");
                continue;
            }

            if (Nverify("num", blocoinicial, true)) 
            {
                if (!conta(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            if (Nverify("if", blocoinicial, false)) 
            {
                if (!ifelse(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            if (Nverify("enquanto", blocoinicial, false)) 
            {
                if (!enquanto(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            if (Nverify("para", blocoinicial, false)) 
            {
                if (!para(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            if (Nverify("impressao", blocoinicial, false)) 
            {
                if (!impressao(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            if (Nverify("leitura", blocoinicial, false)) 
            {
                if (!leitura(blocoinicial)) return false;
                traduz("\n");
                continue;
            }

            return false;
        }

        return true;
    }

    
    public boolean id(Node node)
    {
        Node id = node.addNode("id");
        if(NmatchT("id", id, token.lexema))
        {
            return true;
        }
        erroT("id", id);
        return false;
    }
    
    public boolean traduzId(Node node)
    {
        Node id = node.addNode("id");
        if(NmatchT("id", id, "&" + token.lexema))
        {
            return true;
        }
        erroT("id", id);
        return false;
    }
    
    public boolean tipo(Node node)
    {
        Node tipo = node.addNode("tipo");
        return (NmatchT("tipo_booleano", tipo, token.lexema) || 
                NmatchT("num", tipo, token.lexema)  ||
                NmatchT("char", tipo, token.lexema) || 
                NmatchT("string", tipo, token.lexema) || id(tipo));
    }
    
    public boolean traduzVar(Node node)
    {
        Node tipo = node.addNode("tipo");
        return (NmatchT("tipo_booleano", tipo, "&" + token.lexema) || 
                NmatchT("num", tipo, "&" + token.lexema)  ||
                NmatchT("char", tipo, "&" + token.lexema) || 
                NmatchT("string", tipo, "&" + token.lexema) || traduzId(tipo));
    }
    
    public boolean reservadaTipo(Node node)
    {
        Node reservadaTipo = node.addNode("reservadaTipo");
        return NmatchT("reservada_tipo_inteiro", reservadaTipo, token.lexema + " ") || 
               NmatchT("reservada_tipo_decimal", reservadaTipo, token.lexema+ " ") || 
               NmatchT("reservada_tipo_texto", reservadaTipo, token.lexema+ " ") || 
               NmatchT("reservada_tipo_caracter", reservadaTipo, token.lexema+ " ") || 
               NmatchT("reservada_tipo_booleano", reservadaTipo, token.lexema+ " "); 
    }
    
    public boolean verificaTipo(Node node)
    {
        Node verificaTipo = new Node("verificaTipo");
        return Nverify("reservada_tipo_inteiro", verificaTipo, true) || 
               Nverify("reservada_tipo_decimal", verificaTipo, true) || 
               Nverify("reservada_tipo_texto", verificaTipo, true) || 
               Nverify("reservada_tipo_caracter", verificaTipo, true) || 
               Nverify("reservada_tipo_booleano", verificaTipo, true); 
    }
    
    public boolean traduzTipo(Node node)
    {
        Node reservadaTipo = node.addNode("reservadaTipo");
        if(Nverify("reservada_tipo_inteiro", reservadaTipo, true))
        {
            traduz("\"");
            traduz("%d");
            traduz("\"");
            return NmatchT("reservada_tipo_inteiro", reservadaTipo);
        }
        if(Nverify("reservada_tipo_decimal", reservadaTipo, true))
        {
            traduz("\"");
            traduz("%f");
            traduz("\"");
            return NmatchT("reservada_tipo_decimal", reservadaTipo);
        }
        if(Nverify("reservada_tipo_texto", reservadaTipo, true))
        {
            traduz("\"");
            traduz("%c");
            traduz("\"");
            return NmatchT("reservada_tipo_texto", reservadaTipo);
        }
        if(Nverify("reservada_tipo_caracter", reservadaTipo, true))
        {
            traduz("\"");
            traduz("%c");
            traduz("\"");
            return NmatchT("reservada_tipo_caracter", reservadaTipo);
        }
        if(Nverify("reservada_tipo_booleano", reservadaTipo, true))
        {
            traduz("\"");
            traduz("%d");
            traduz("\"");
            return NmatchT("reservada_tipo_booleano", reservadaTipo);
        }
        return false;
    }
       
    public boolean operador(Node node)
    {
        Node operador = node.addNode("operador");
        if (NmatchL(">", operador, token.lexema) || NmatchL("<", operador, token.lexema) 
                || NmatchL("==", operador, token.lexema) || NmatchL(">=", operador, token.lexema) 
                || NmatchL("<=", operador, token.lexema) || NmatchL("!=", operador, token.lexema))
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
                if (NmatchL("*", conta, token.lexema) || NmatchL("/", conta, token.lexema)) 
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
                if (NmatchL("+", conta, token.lexema) || NmatchL("-", conta, token.lexema)) 
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

            if (!NmatchL("+", conta, token.lexema) && !NmatchL("-", conta, token.lexema) && !NmatchL("*", conta, token.lexema) && !NmatchL("/", conta, token.lexema)) 
            {
                break;
            }
    }

    return true;
    }

    public boolean contaTermo(Node node) 
    {
        if (NmatchT("num", node, token.lexema)) 
        {
            return true;
        }

        if (NmatchT("id", node, token.lexema)) 
        {
            return true;
        }

        if (NmatchL("(", node, token.lexema)) 
        {
            if (!conta(node)) 
            {
                return false;
            }

            return NmatchL(")", node, token.lexema);
        }
        return false;
    }
    
    public boolean condicao(Node node) 
    {
        Node condicao = node.addNode("condicao");
        return id(condicao)  && operador(condicao) &&
               (tipo(condicao)|| 
                conta(condicao));
    }

    public boolean atribuicao(Node node)
    {
        Node atribuicao = node.addNode("atribuicao");
        if(reservadaTipo(atribuicao) && NmatchT("id", atribuicao, token.lexema) && NmatchL("=", atribuicao, "=") && (tipo(atribuicao) || conta(atribuicao)))
        {
            traduz(";");
            return true;
        }
        erroT("Definition with correct types: int, float, string, char or bool", atribuicao);
        return false;
    }
    
    public boolean expressao(Node node)
    {
        Node expressao = node.addNode("expressao");
        if (NmatchT("id", expressao, token.lexema) && NmatchL("=", expressao, token.lexema) && tipo(expressao))
        {
            traduz(";");
            return true;
        }
        erroT("Expression of type: id = num/bool/string/char", expressao);
        return false;
    }
    
    public boolean somatorio(Node node)
    {
        Node somatorio = node.addNode("somatorio");
        if (NmatchT("id", somatorio, token.lexema) && ((NmatchL("+", somatorio, token.lexema) && (NmatchL("+", somatorio, token.lexema))) || 
            NmatchL("-", somatorio, token.lexema) && NmatchL("-", somatorio, token.lexema)))
        {
            return true;
        }
        erroL("Sum of type: id++ / id--", somatorio);
        return false;
    }
    
    public boolean ifelse(Node node)
    {
        Node ifelse = node.addNode("condicional");
        if (NmatchL("if", ifelse, token.lexema))
        {
            traduz("(");
            if(condicao(ifelse))
            {
                traduz(")");
                traduz("\n{");
                if(NmatchL("then", ifelse))
                {
                    if(bloco(ifelse))
                    {
                        traduz("\n}");
                        if(NmatchL("else", ifelse, "\n" + token.lexema)) 
                        {
                            traduz("\n{");
                            if(bloco(ifelse)) 
                            {
                                traduz("\n}");
                                return true;
                            } 
                        }
                        return true;
                    }
                    return false;
                }           
                return false;
            }
            return false;
        }
        erroL("ifelse() format", ifelse);
        return false;
    }
    
    public boolean enquanto(Node node)
    {
       Node enquanto = node.addNode("enquanto");
       if(NmatchL("enquanto", enquanto, "while"))
       {
           traduz("(");
           if(condicao(enquanto) && NmatchL(":", enquanto))
           {
             traduz(")");  
           }
           traduz("\n{");
           if(bloco(enquanto))
           {
               traduz("\n}");
               return true;
           }
       }
       erroL("enquanto() format", enquanto);
       return false;
    }
    
    public boolean para(Node node)
    {
       Node para = node.addNode("para");
       if(NmatchL("para", para, "for") && NmatchL("(", para, token.lexema) && atribuicao(para) &&
          NmatchL(";", para) && condicao(para) &&  NmatchL(";", para, token.lexema) && somatorio(para) && 
          NmatchL(")", para, token.lexema)  && NmatchL(":", para))
       {
           traduz("\n{");
           if(bloco(para))
           {
               traduz("\n}");
               return true;
           }
       }
       erroL("para() format", para);
       return false;
    }
    

    public boolean leitura(Node node) 
    {
        Node leitura = node.addNode("leitura");

        if (NmatchL("leitura", leitura, "scanf") && NmatchL("(", leitura, token.lexema)) 
        {
            if (traduzTipo(leitura)) 
            {
                while (NmatchL(",", leitura, token.lexema)) 
                {
                    if (!traduzTipo(leitura)) 
                    {
                        erro("Esperado tipo de leitura após vírgula");
                        return false;
                    }
                }

                if (NmatchL(")", leitura, ",") && NmatchL("(", leitura)) 
                {
                    if(traduzVar(leitura)) 
                    {
                        while (NmatchL(",", leitura, token.lexema)) 
                        {
                            if (!traduzVar(leitura)) 
                            {
                                erro("Esperado variável de leitura após vírgula");
                                return false;
                            }
                        }

                        if (NmatchL(")", leitura, token.lexema)) 
                        {
                            traduz(";");
                            return true;
                        }
                    }
                }
            }
        }
    
    erroL("Formato de leitura esperado: leitura(tipo1, tipo2, ...)(variavel1, variavel2, ...)", leitura);
    return false;
}

      
    public boolean impressao(Node node)
    {
       Node impressao = node.addNode("impressao");
       if(NmatchL("impressao", impressao, "printf") && NmatchL("(", impressao, token.lexema) &&
               traduzTipo(impressao) && NmatchL(",", impressao, token.lexema) &&
               tipo(impressao) && NmatchL(")", impressao, token.lexema))
       {
           traduz(";");
           return true;
       }
       erroL("impressao() format", impressao);
       return false;
    }
    
    boolean primeiraVez = true;

    private void traduz(String code) 
    {
        if(primeiraVez)
        {
           primeiraVez = false;
           try (FileWriter writer = new FileWriter("src\\main\\java\\com\\mycompany\\projetocompleto_compiladores\\output.txt", false))
            {
                writer.write(code);
            } 
            catch (IOException e){} 
        }
        else
        {
            try (FileWriter writer = new FileWriter("src\\main\\java\\com\\mycompany\\projetocompleto_compiladores\\output.txt", true))
            {
                writer.write(code);
            } 
            catch (IOException e){}
        }
        
    }
    
}
