package com.kyleharrington.lambdacalc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class TutorialActivity extends ActionBarActivity
{
    private final String tutorial = "\nLambda Calculus is a mathematical system for expressing"    +
            " computation developed by Church in the 1930's. Lambda Calculus as a model for "      +
            "computability has very important applications to computer science. The level of "     +
            "abstraction in lambda calculus makes it possible to formalize programming languages " +
            "and programming.\n"                                                                   +
            "\n"                                                                                   +
            "A lambda expression can be a name, function or application. You will see that "       +
            "these become recursive definitions.\n"                                                +
            "<expression> :: <name> | <function> | <application>\n"                                +
            "\n"                                                                                   +
            "A name may be any sequence of non-blank alphanumeric characters.\n"                   +
            "\n"                                                                                   +
            "A function has the form λ<name>.<body> where <body> is an expression. The name "      +
            "of a function is referred to as its bound variable, like a formal parameter. Since "  +
            "the body of a function can be any expression, it is possible for functions to return "+
            "other functions as values. \n"                                                        +
            "\n"                                                                                   +
            "An application has the form <function expression><argument expression> where each "   +
            "are also expressions. In an application, the function expression is the abstraction " +
            "to be specialized. The argument expression provides a value for the name of the "     +
            "function expression to be specialized. It is said that the function expression is "   +
            "applied to the argument expression. You will notice that function definitions can "   +
            "appear directly in function calls. \n"                                                +
            "\n"                                                                                   +
            "Applications will be evaluated in normal order here. Normal order is "                +
            "left-associative. The leftmost outer redex gets evaluated first. In other words, "    +
            "an argument is never evaluated before having a function applied to it. The argument " +
            "will always be substituted into the body of the function before it is reduced.\n"     +
            "\n"                                                                                   +
            "Variables that are within the scope of a function are said to be bound, as noted "    +
            "earlier. A variable is within scope and bound wherever it occurs in the body of the " +
            "function. A bound variable is bound by the nearest abstraction. Note that this "      +
            "makes it possible to be working with the same variable names within the body of "     +
            "functions, but each occurrence can be bound differently. For example, consider "      +
            "λx.(λx.x x) y. Here, the only x in the body of the first λx that is bound to "        +
            "that abstraction is the lone x within the application of its body (the x to the "     +
            "right of λx.x in (λx.x x). The x within λx.x is bound to its λx. If λx(λx.x x)"       +
            " was applied to y, it would reduce to λx.x y, which would further reduce to y. "      +
            "All other variables present are called free, and are not bound to a particular "      +
            "abstraction. \n"                                                                      +
            "\n"                                                                                   +
            "Beta reduction is the process of applying a function to an argument. All occurrences" +
            " of the bound name of the function get substituted with the argument expression "     +
            "within the body of that function. \n"                                                 +
            "\n"                                                                                   +
            "\nExamples:\n"                                                                        +
            "\nIdentity function: λx.x"                                                            +
            "\nIn the identify function, x is the name and a bound "                               +
            "variable. x appears in the body of the function as bound variable. Applying the "     +
            "identity function to any expression will yield that argument expression, hence why "  +
            "it is called the identity function. For example, consider the application of the "    +
            "identity function to a free variable x: λx.x x. A beta reduction will occur, "        +
            "substituting the free x for every occurrence of the bound x in the identity "         +
            "function. The result is simply x, as expected. \n"                                    +
            "\n"                                                                                   +
            "\nSelf application function: λs.(s s)"                                                +
            "\nThe self application function applies an "                                          +
            "argument expression to itself. In the self application function, s is the name of "   +
            "the abstraction and has two occurrences of bound s's in the body expression, which "  +
            "is an application of s to s. Consider the following example of applying the self "    +
            "application function to the identity function: λs.(s s) λx.x. Here, the identity "    +
            "function is the argument expression. A beta reduction will occur and substitute "     +
            "any bound occurrence of s in the self-application function with λx.x. Doing this "    +
            "results in (λx.x λx.x). (λx.x λx.x) is now an application of λx.x to λx.x. Applying"  +
            " the first λx.x to its argument λx.x substitutes the bound x with λx.x, evaluating "  +
            "to the final result of λx.x\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        final TextView textView = (TextView) findViewById(R.id.tutorial);
        textView.setText(tutorial);
    }
}
