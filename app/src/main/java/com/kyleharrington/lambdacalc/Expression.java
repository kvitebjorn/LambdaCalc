package com.kyleharrington.lambdacalc;

/**
 *
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

public interface Expression
{
    public Expression evaluate();
    public Expression substitute(Name id, Expression exp);
}
