package com.mycompany.lambdacalc;

/**
 * 
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

public class Function implements Expression
{
    Name name;
    Expression body;
    
    public Function(final Name n, final Expression b)
    {
        // A function has the form of \<name>.<body> where <body> is an expression
        name = n; 
        body = b;
    }
    
    public Name getName()
    {
        return name;
    }
    
    public void setName(final Name n)
    {
        name = n;
    }
    
    public Expression getBody()
    {
        return body;
    }
    
    @Override public String toString()
    {
        return "λ" + name.toString() + "." + body.toString();
    }
    
    @Override public Expression evaluate()
    {
        return this;
    }
    
    @Override public Expression substitute(final Name id, final Expression exp)
    {
        body = body.substitute(id,exp);
        return this;
    }
}
