
package com.mycompany.projetocompleto_compiladores;
import java.text.CharacterIterator;

public class Number extends AFD 
{
    boolean zero = false;
    
    @Override
    public Token evaluate(CharacterIterator code)
    {
        if(code.current() == '.')
        {
            zero = true;
            code.next();
        }
        
        if(Character.isDigit(code.current()))
        {
            String number = readNumber(code);
            if(endNumber(code))
            {
                return new Token("num",number);
            }
        }
        return null;
    }
    private String readNumber(CharacterIterator code)
    {
        String number="";
        if(zero == true){number += '.';}
        
        while(Character.isDigit(code.current()))
        {
            number += code.current();
            code.next();
        }
        
        if(code.current() == '.' && zero != true)
        {
            number += code.current();
            code.next();
            while(Character.isDigit(code.current()))
            {
                number += code.current();
                code.next();
            }
        }
        
        return number;
    }
    private boolean endNumber(CharacterIterator code)
    {
        return  code.current() == ' ' ||
                code.current() == '+' ||
                code.current() == '-' ||
                code.current() == '*' ||
                code.current() == '/' ||
                code.current() == '\n'||
                code.current() == CharacterIterator.DONE;
    }
}
