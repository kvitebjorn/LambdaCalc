package com.kyleharrington.lambdacalc;

/**
 * 
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

public class Function implements Expression
{
    private Name name;
    private Expression body;
    
    public Function(final Name n, final Expression b)
    {
        // A function has the form of \<name>.<body> where <body> is an expression
        // Defensively copy n and b
        name = new Name(n.getName());

        if(b instanceof Name)
            body = new Name(((Name) b).getName());
        else if(b instanceof Function)
            body = new Function(((Function) b).getName(), ((Function) b).getBody());
        else
            body = new Application(((Application) b).getFunction(),((Application) b).getArgument());
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
