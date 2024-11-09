
package com.mycompany.projetocompleto_compiladores;
import java.text.CharacterIterator;

public class Text extends AFD 
{
    
    @Override
    public Token evaluate(CharacterIterator code)
    {
        if(code.current() == '"')
        {
            code.next();
            if(code.current() != ' ')
            {
                String texto = readString(code);
                return new Token("texto", texto);
            }
        }
        
        else if(code.current() == '\'')
        {
            String caracter = "";
            caracter += code.current();
            code.next();
            caracter += code.current();
            code.next();
            if(code.current() != '\''){return null;}
            caracter += '\'';
            code.next();
            return new Token("car", caracter);
        }
        
        return null;
    }
    
    private String readString(CharacterIterator code)
    {
        String texto="";
        texto += '"';

        while(code.current() != '\"' && code.current() != '"')
        {
            texto += code.current();
            code.next();
            System.out.println(texto);
        }
        
        texto += '"';
        code.next();
        
        return texto;
    }
    
}
