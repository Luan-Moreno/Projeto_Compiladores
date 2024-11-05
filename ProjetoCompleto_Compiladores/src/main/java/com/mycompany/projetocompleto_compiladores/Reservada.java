
package com.mycompany.projetocompleto_compiladores;

import java.text.CharacterIterator;

public class Reservada extends AFD
    {
    
      @Override
      public Token evaluate(CharacterIterator code)
      {

        if(matchC(code, 'i') && matchC(code, 'f') && matchC(code, ' '))
        {
            return new Token("reservada", "if");
        }
        
        if(matchC(code, 'e') && matchC(code, 'l') && matchC(code, 's') && matchC(code, 'e') && matchC(code, ' '))
        {
            return new Token("reservada", "else");
        }
        
        if(matchC(code, 't') && matchC(code, 'h') && matchC(code, 'e') && matchC(code, 'n') && matchC(code, ' '))
        {
            return new Token("reservada", "then");
        }
        
        if(matchC(code, 'w') && matchC(code, 'h') && matchC(code, 'i') && matchC(code, 'l') && matchC(code, 'e') && matchC(code, ' '))
        {
            return new Token("reservada", "while");
        }
        
        if(matchC(code, 'f') && matchC(code, 'o') && matchC(code, 'r') && matchC(code, ' '))
        {
            return new Token("reservada", "for");
        }
        
        if(matchC(code, 'i') && matchC(code, 'n') && matchC(code, 't') && matchC(code, ' '))
        {
            return new Token("reservada_tipo_inteiro", "int");
        }
        
        if(matchC(code, 'f') && matchC(code, 'l')  && matchC(code, 'o')  && matchC(code, 'a') && matchC(code, 't') && matchC(code, ' '))
        {
            return new Token("reservada_tipo_decimal", "float");
        }
        
        if(matchC(code, 's') && matchC(code, 't')  && matchC(code, 'r')  && matchC(code, 'i') && matchC(code, 'n') && matchC(code, 'g') && matchC(code, ' '))
        {
            return new Token("reservada_tipo_texto", "string");
        }
        
        if(matchC(code, 'c') && matchC(code, 'h')  && matchC(code, 'a')  && matchC(code, 'r') && matchC(code, ' '))
        {
            return new Token("reservada_tipo_caracter", "char");
        }
        
        if(matchC(code, 'b') && matchC(code, 'o')  && matchC(code, 'o')  && matchC(code, 'l') && matchC(code, ' '))
        {
            return new Token("reservada_tipo_booleano", "bool");
        }
        
        if(matchC(code, 'T') && matchC(code, 'r')  && matchC(code, 'u')  && matchC(code, 'e'))
        {
            return new Token("tipo_booleano", "True");
        }
        
        if(matchC(code, 'F') && matchC(code, 'a')  && matchC(code, 'l')  && matchC(code, 's') && matchC(code, 'e'))
        {
            return new Token("tipo_booleano", "False");
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
