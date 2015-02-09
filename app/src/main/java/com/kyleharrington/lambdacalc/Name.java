package com.kyleharrington.lambdacalc;

/**
 * 
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

public class Name implements Expression
{
    private String name = "";
    
    public Name(final String n)
    {
        name = n;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(final String n)
    {
        name = n;
    }
    
    @Override public String toString()
    {
        return name;
    }
    
    @Override public Expression evaluate()
    {
        return this;
    }
    
    @Override public Expression substitute(final Name id, final Expression exp)
    {
        if(id.toString().equals(name))
            return exp;
        else
            return this;
    }
}
