
package com.mycompany.projetocompleto_compiladores;

import java.text.CharacterIterator;

public class Reservada extends AFD
    {
      private int posicaoInicial;
      @Override
      public Token evaluate(CharacterIterator code)
      {
        this.posicaoInicial = code.getIndex();
        
        if(matchC(code, 'c') && matchC(code, 'a') && matchC(code, 's') && matchC(code, 'o'))
        {
            return new Token("reservada", "caso");
        }
        
        if(matchC(code, 's') && matchC(code, 'e') && matchC(code, 'n') && matchC(code, 'a') && matchC(code, 'o'))
        {
            return new Token("reservada", "senao");
        }
        
        if(matchC(code, 'e') && matchC(code, 'n') && matchC(code, 't') && matchC(code, 'a') && matchC(code, 'o'))
        {
            return new Token("reservada", "entao");
        }
        
        if(matchC(code, 's') && matchC(code, 'a') && matchC(code, 'i') && matchC(code, 'd') && matchC(code, 'a'))
        {
            return new Token("reservada_saida", "saida");
        }
        
        if(matchC(code, 'e') && matchC(code, 'n') && matchC(code, 'q') && matchC(code, 'u') && matchC(code, 'a') && matchC(code, 'n') && matchC(code, 't') && matchC(code, 'o'))
        {
            return new Token("reservada", "enquanto");
        }
        
        if(matchC(code, 'p') && matchC(code, 'a') && matchC(code, 'r') && matchC(code, 'a'))
        {
            return new Token("reservada", "para");
        }
        
        if(matchC(code, 'l') && matchC(code, 'e')  && matchC(code, 'i')  && matchC(code, 't') && matchC(code, 'u') && matchC(code, 'r') && matchC(code, 'a'))
        {
            return new Token("reservada", "leitura");
        }
        
        if(matchC(code, 'i') && matchC(code, 'm')  && matchC(code, 'p')  && matchC(code, 'r') && matchC(code, 'e') && matchC(code, 's') && matchC(code, 's') && matchC(code, 'a') && matchC(code, 'o'))
        {
            return new Token("reservada", "impressao");
        }
        
        if(matchC(code, 'i') && matchC(code, 'n') && matchC(code, 't'))
        {
            return new Token("reservada_tipo_inteiro", "int");
        }
        
        if(matchC(code, 'd') && matchC(code, 'e')  && matchC(code, 'c'))
        {
            return new Token("reservada_tipo_decimal", "dec");
        }
        
        if(matchC(code, 't') && matchC(code, 'e')  && matchC(code, 'x')  && matchC(code, 't') && matchC(code, 'o'))
        {
            return new Token("reservada_tipo_texto", "texto");
        }
        
        if(matchC(code, 'c')  && matchC(code, 'a')  && matchC(code, 'r'))
        {
            return new Token("reservada_tipo_caracter", "car");
        }
        
        if(matchC(code, 'b') && matchC(code, 'i')  && matchC(code, 'n'))
        {
            return new Token("reservada_tipo_binario", "bin");
        }
        
        if(matchC(code, 'V') && matchC(code, 'e')  && matchC(code, 'r')  && matchC(code, 'd') && matchC(code, 'a') && 
           matchC(code, 'd') && matchC(code, 'e') && matchC(code, 'i') && matchC(code, 'r') && matchC(code, 'o')) 
        {
            return new Token("tipo_binario", "Verdadeiro");
        }
        
        if(matchC(code, 'F') && matchC(code, 'a')  && matchC(code, 'l')  && matchC(code, 's') && matchC(code, 'o'))
        {
            return new Token("tipo_binario", "Falso");
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
        code.setIndex(this.posicaoInicial);
        return false;
       }
    }
