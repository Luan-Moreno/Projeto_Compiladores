
package com.mycompany.projetocompleto_compiladores;

import java.text.CharacterIterator;

public class MathOperator extends AFD 
{
   @Override
   public Token evaluate(CharacterIterator code)
   {
       switch(code.current())
       {
           case '+':
               code.next();
               return new Token("operador_adicao", "+");
               
           case '-':
               code.next();
               return new Token("operador_subtracao", "-");
               
           case '*':
               code.next();
               return new Token("operador_multiplicacao", "*");
               
           case '/':
               code.next();
               return new Token("operador_divisao", "/");
               
           case '>':
               code.next();
               if(code.current() == '=')
               {
                 code.next();
                 return new Token("operador_maior_igual", ">=");
               }
               return new Token("operador_maior", ">");
               
           case '<':
                code.next();
                if(code.current() == '=')
                {
                  code.next();
                  return new Token("operador_menor_igual", "<=");
                }
                return new Token("operador_menor", "<");  
               
           case '=':
               code.next();
               if(code.current() == '=')
               {
                 code.next();
                 return new Token("operador_comparacao", "==");
               }
               return new Token("operador_atribuicao", "=");
               
            case '!':
               code.next();
               if(code.current() == '=')
               {
                 code.next();
                 return new Token("operador_diferente", "!=");
               }
            
           case ':':
               code.next();
               return new Token("operador_definicao", ":");
            
           case '(':
               code.next();
               return new Token("abertura_condicao", "(");
            
            case ')':
               code.next();
               return new Token("fechamento_condicao", ")");
               
            case ';':
               code.next();
               return new Token("fim_sentença", ";");
               
            case ',':
               code.next();
               return new Token("virgula", ",");
            
            case '[':
               code.next();
               return new Token("abre_car", "[");
               
            case ']':
               code.next();
               return new Token("fecha_car", "]");
           
           default:
               return null;
       }
   }
}
