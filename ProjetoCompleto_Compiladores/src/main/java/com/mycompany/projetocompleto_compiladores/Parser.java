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
        System.out.println("Regra: " + regra);
        System.out.println("Token Invalido: " + token.lexema);
        System.exit(0);
    }
    
    public void main() 
    {
       token = getNextToken();
       if(ifelse())
       {
          if(token.tipo.equals("EOF"))
          {
              System.out.println("Sintaticamente Correto");
          }
          else
          {
              erro("EOF");
          }
       }
       
       /*if(enquanto())
       {
          if(token.tipo.equals("EOF"))
          {
              System.out.println("Sintaticamente Correto");
          }
          else
          {
              erro("EOF");
          }
       }*/
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
    
    public boolean operador()
    {
        if(matchL(">") || matchL("<") || matchL("==") || matchL(">=") || matchL("<="))
        {
            return true;
        }
        return false;
    }
    
    public boolean condicao()
    {
        if(matchT("id") && operador() && matchT("num"))
        {
            return true;
        }
        erro("condicao");
        return false;
    }
    
    
    public boolean atribuicao()
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
        
        erro("atribuicao");
        return false;
    }
    
    
    
    public boolean expressao()
    {
        if(matchT("id") && matchL("=") && matchT("num"))
        {
            return true;
        }
        erro("expressao");
        return false;
    }
    
    public boolean ifelse()
    {
        if(matchL("if") && condicao() && matchL("then") && expressao() && matchL("else") && expressao())
        {
            return true;
        }
        erro("ifelse");
        return false;
    }
    
    public boolean enquanto()
    {
       if(matchL("while") && condicao() && matchL(":") && expressao())
       {
           return true;
       }
       erro("enquanto");
       return false;
    }
    
    public boolean para()
    {
       if(matchL("for") && condicao() && matchL(":") && expressao())
       {
           return true;
       }
       erro("enquanto");
       return false;
    }
    
}
