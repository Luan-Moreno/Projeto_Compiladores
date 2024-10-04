
package com.mycompany.projetocompleto_compiladores;

import java.text.CharacterIterator;

public class Reservada extends AFD
    {
    
      @Override
      public Token evaluate(CharacterIterator code)
      {

        if(matchC(code, 'i') && matchC(code, 'f') && matchC(code, ' '))
        {
            return new Token("reservada_if", "if");
        }
        
        if(matchC(code, 'e') && matchC(code, 'l') && matchC(code, 's') && matchC(code, 'e') && matchC(code, ' '))
        {
            return new Token("reservada_else", "else");
        }
        
        if(matchC(code, 't') && matchC(code, 'h') && matchC(code, 'e') && matchC(code, 'n') && matchC(code, ' '))
        {
            return new Token("reservada_then", "then");
        }
        return null;
      }
      
      public boolean matchC(CharacterIterator code, char c)
      {
        if(code.current() == c)
        {
            code.next();
            return true;
        }
        return false;
       }
    }
