package com.mycompany.lambdacalc;

/**
 * 
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

public class Application implements Expression
{
    // Function applications have the form <function expression><argument expression>
    // Function applications evaluate from left to right
    Expression function;
    Expression argument;
    
    public Application(final Expression f, final Expression a)
    {
        function = f;
        argument = a;
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
            Function ff = new Function(f.name, f.body);
            ff.substitute(ff.getName(), argument);
            Expression newExpression = ff.getBody();
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