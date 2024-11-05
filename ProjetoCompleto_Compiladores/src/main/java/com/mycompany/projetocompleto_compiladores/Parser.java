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
    
    public void main() 
    {
       token = getNextToken();
       if(ifelse(false) || enquanto(false) || para(false))
       {
          if(token.tipo.equals("EOF"))
          {
              System.out.println("Sintaticamente Correto");
          }
       }
       else
       {
           erro("EOF");
       }
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
    
    public boolean matchT(String tipo)
    {
        if(token.tipo.equals(tipo))
        {
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    public boolean operador(boolean erro)
    {
        if(matchL(">") || matchL("<") || matchL("==") || matchL(">=") || matchL("<="))
        {
            return true;
        }
        if(erro)
        {
            erro("operador");
        }
        return false;
    }
    
    public boolean condicao(boolean erro)
    {
        if(matchT("id") && operador(true) && (matchT("num") || matchT("tipo_booleano") || matchT("string") || matchT("char")))
        {
            return true;
        }
        if(erro)
        {
            erro("condicao");
        }
        return false;
    }
    
    
    public boolean atribuicao(boolean erro)
    {
        if(matchT("reservada_tipo_inteiro") && matchT("id") && matchL("=") && matchT("num"))
        {
            return true;
        }
        
        if(matchT("reservada_tipo_decimal") && matchT("id") && matchL("=") && matchT("decimal"))
        {
            return true;
        }
        
        if(matchT("reservada_tipo_texto") && matchT("id") && matchL("=") && matchT("string"))
        {
            return true;
        }
        
        if(matchT("reservada_tipo_caracter") && matchT("id") && matchL("=") && matchT("char"))
        {
            return true;
        }
        
        if(matchT("reservada_tipo_booleano") && matchT("id") && matchL("=") && matchT("bool"))
        {
            return true;
        }
        if(erro)
        {
            erro("atribuicao");
        }
        return false;
    }
    
    
    
    public boolean expressao(boolean erro)
    {
        if(matchT("id") && matchL("=") && (matchT("num") || matchT("tipo_booleano") || matchT("string") || matchT("char")))
        {
            return true;
        }
        if(erro)
        {
            erro("expressao");
        }
        return false;
    }
    
    public boolean somatorio(boolean erro)
    {
        if(matchT("id") && (matchL("+") || matchL("-")) && (matchL("+") || matchL("-")))
        {
            return true;
        }
        if(erro)
        {
           erro("somatorio"); 
        }
        return false;
    }
    
    public boolean ifelse(boolean erro)
    {
        if(matchL("if") && condicao(true) && matchL("then"))
        {
            if(expressao(false) || ifelse(false) || enquanto(false) || para(false))
            {
                if(matchL("else")) 
                {
                    if(expressao(false) || ifelse(false) || enquanto(false) || para(false)) 
                    {
                        return true;
                    } 
                else return expressao(true);
                }
                return true;
            }
        }
        if(erro)
        {
            erro("ifelse");
        }
        return false;
    }
    
    public boolean enquanto(boolean erro)
    {
       if(matchL("while") && condicao(true) && matchL(":"))
       {
           if(expressao(false) || ifelse(false) || enquanto(false) || para(false))
           {
               return true;
           }
       }
       if(erro)
       {
           erro("enquanto");
       }  
       return false;
    }
    
    public boolean para(boolean erro)
    {
       if(matchL("for") && matchL("(") && atribuicao(true) && matchL(";") && condicao(true) && matchL(";") && somatorio(true) && matchL(")")  && matchL(":"))
       {
           if(expressao(false) || ifelse(false) || enquanto(false) || para(false))
           {
               return true;
           }
       }
       if(erro)
       {
           erro("para");
       }
       return false;
    }
    
}
