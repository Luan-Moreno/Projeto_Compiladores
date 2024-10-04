
package com.mycompany.projetocompleto_compiladores;

public class Token 
{
    public String lexema;
    public String tipo;

    public Token(String tipo, String lexema) 
    {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    public String getLexema() 
    {
        return lexema;
    }

    public String getTipo() 
    {
        return tipo;
    }
    
    @Override
    public String toString() 
    {
        return "<" + tipo + "," + "'" + lexema + "'" + ">";
    }
    
}
