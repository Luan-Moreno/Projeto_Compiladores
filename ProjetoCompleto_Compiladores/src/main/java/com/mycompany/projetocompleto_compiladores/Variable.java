
package com.mycompany.projetocompleto_compiladores;
import java.text.CharacterIterator;

public class Variable extends AFD 
{
    @Override
    public Token evaluate(CharacterIterator code)
    {
        if(Character.isLetter(code.current()))
        {
            String variable = readVariable(code);
            if(endVariable(code))
            {
                return new Token("id",variable);
            }
        }
        return null;
    }
    private String readVariable(CharacterIterator code)
    {
        String variable="";
        if(Character.isLetter(code.current()))
        {
            variable += code.current();
            code.next();
        }
        
        while(Character.isLetterOrDigit(code.current()))
        {
            variable += code.current();
            code.next();
        }
        return variable;
    }
    private boolean endVariable(CharacterIterator code)
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
