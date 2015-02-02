package com.mycompany.lambdacalc;

/**
 * 
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

public class Application implements Expression
{
    // Function applications have the form <function expression><argument expression>
    // Function applications evaluate from left to right
    private Expression function;
    private Expression argument;
    private static String betas = "";
    
    public Application(final Expression f, final Expression a)
    {
        //defensively copy f and a
        if(f instanceof Name)
            function = new Name(((Name) f).getName());
        else if(f instanceof Function)
            function = new Function(((Function) f).getName(), ((Function) f).getBody());
        else
            function = new Application(((Application) f).getFunction(),((Application) f).getArgument());

        if(a instanceof Name)
            argument = new Name(((Name) a).getName());
        else if(a instanceof Function)
            argument = new Function(((Function) a).getName(), ((Function) a).getBody());
        else
            argument = new Application(((Application) a).getFunction(),((Application) a).getArgument());
    }

    public String getBetas()
    {
        return betas;
    }

    public void setBetas(String s)
    {
        betas = s;
    }
    
    public Expression getFunction()
    {
        return function;
    }
    
    public Expression getArgument()
    {
        return argument;
    }
    
    @Override public String toString()
    {
        return "(" + function.toString() + " " + argument.toString() + ")";
    }

    @Override public Expression evaluate()
    {
        if(function instanceof Function)
        {
            Function f = (Function) function;
            betas = betas + "\n" + function.toString() + " " + argument.toString() + " ->";
            Function ff = new Function(f.getName(), f.getBody());
            ff.substitute(ff.getName(), argument);
            Expression newExpression = ff.getBody();
            betas = betas + "\nβ :: " + newExpression.toString() + "\n";
            return newExpression.evaluate();
        }
        else if(function instanceof Name)
        {
            Expression newArgument = argument.evaluate();
            Application newApplication = new Application(function, newArgument);
            return newApplication;
        }
        else
        {
            betas = betas + "\n" + function.toString() + " " + argument.toString() + " =>";
            Expression newExpression = function.evaluate();
            Application newApplication = new Application(newExpression, argument);
            return newApplication.evaluate();
        }
    }
    
    @Override public Expression substitute(final Name id, final Expression exp)
    {
        function = function.substitute(id, exp);
        argument = argument.substitute(id, exp);
        return this;
    }
}