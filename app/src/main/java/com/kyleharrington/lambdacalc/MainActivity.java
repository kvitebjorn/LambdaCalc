package com.kyleharrington.lambdacalc;

/**
 *
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
{
    private ArrayAdapter<Expression> expAdapter;
    private ArrayList<Expression> expressions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressions = new ArrayList<>();

        //Test problems taken from:
        //AN INTRODUCTION TO FUNCTIONAL PROGRAMMING THROUGH LAMBDA CALCULUS
        //By Greg Michaelson
        //Chapter 2 Exercise 2

        //i) ((λc.λd.(d c) λe.λh.e) λi.i)
        final Name c = new Name("c");
        final Name d = new Name("d");
        final Name e = new Name("e");
        final Name h = new Name("h");
        final Name i = new Name("i");

        final Function I = new Function(i,i);
        final Function H = new Function(h,e);
        final Function E = new Function(e,H);
        final Application dc = new Application(d,c);
        final Function D = new Function(d,dc);
        final Function C = new Function(c,D);
        final Application CE = new Application(C,E);
        final Application problem1 = new Application(CE,I);
        expressions.add(problem1);

        //ii) (((λj.λk.λl.((j k) l) λm.λn.(m n)) λo.o) λp.p)
        final Name j = new Name("j");
        final Name k = new Name("k");
        final Name l = new Name("l");
        final Name m = new Name("m");
        final Name n = new Name("n");
        final Name o = new Name("o");
        final Name p = new Name("p");

        final Function P = new Function(p,p);
        final Function O = new Function(o,o);
        final Application mn = new Application(m,n);
        final Function N = new Function(n,mn);
        final Function M = new Function(m,N);
        final Application jk = new Application(j,k);
        final Application jkl = new Application(jk, l);
        final Function L = new Function(l, jkl);
        final Function K = new Function(k,L);
        final Function J = new Function(j,K);
        final Application JM = new Application(J,M);
        final Application JMO = new Application(JM,O);
        final Application problem2 = new Application(JMO,P);
        expressions.add(problem2);

        //iii) (λq.((λr.λu.(u r) q) q) λv.(v v)) ... supposed to be infinite... and it is ^^
        final Name q = new Name("q");
        final Name r = new Name("r");
        final Name u = new Name("u");
        final Name v = new Name("v");

        final Application vv = new Application(v,v);
        final Function V = new Function(v,vv);
        final Application ur = new Application(u,r);
        final Function U = new Function(u,ur);
        final Function R_ = new Function(r,U);
        final Application Rq = new Application(R_,q);
        final Application Rqq = new Application(Rq,q);
        final Function Q = new Function(q,Rqq);
        final Application problem3 = new Application(Q,V);
        expressions.add(problem3);

        //iv) ((λw.λz.(w z) (λx.x λa.λb.a)) λk.k)
        final Name w = new Name("w");
        final Name z = new Name("z");
        final Name x2 = new Name("x");
        final Name a2 = new Name("a");
        final Name b2 = new Name("b");
        final Name k2 = new Name("k");

        final Function K2 = new Function(k2,k2);
        final Function B2 = new Function(b2,a2);
        final Function A2 = new Function(a2,B2);
        final Function X2 = new Function(x2,x2);
        final Application XA = new Application(X2, A2);
        final Application WZ = new Application(w,z);
        final Function Z = new Function(z,WZ);
        final Function W = new Function(w,Z);
        final Application WXA = new Application(W,XA);
        final Application problem4 = new Application(WXA,K2);
        expressions.add(problem4);

        //v) (((λf.λg.λx.(f (g x)) λs.(s s)) λa.λb.b) λt.λy.t)
        final Name f = new Name("f");
        final Name g = new Name("g");
        final Name s = new Name("s");
        final Name a = new Name("a");
        final Name b = new Name("b");
        final Name x = new Name("x");
        final Name y = new Name("y");
        final Name t = new Name("t");

        final Application gx = new Application(g, x);   // (g x)
        final Application fgx = new Application(f, gx); // f (g x)
        final Application ss = new Application(s, s);   // (s s)

        final Function X = new Function(x, fgx); // \x.(f (g x))
        final Function G = new Function(g, X);   // \g.\x.(f (g x))
        final Function F = new Function(f, G);   // \f.\g.\x.(f (g x))*

        final Function S = new Function(s, ss); // \s.(s s)*
        final Function B = new Function(b, b);  // \b.b
        final Function A = new Function(a, B);  // \a.\b.b *

        final Function Y = new Function(y, t);  // \y.x
        final Function T = new Function(t, Y);  // \t.\y.t *

        final Application fTOs = new Application(F, S);     // (\f.\g.\x.(f(g x)) \s.(s s))
        final Application fsTOa = new Application(fTOs, A); // ((\f.\g.\x.(f(g x)) \s.(s s)) \a.\b.b)
        final Application problem5 = new Application(fsTOa, T); // (((\f.\g.\x.(f(g x)) \s.(s s)) \a.\b.b) \t.\y.t)
        expressions.add(problem5);
        expressions.add(x);
        expressions.add(X2);

        //array adapter for the list of expressions
        expAdapter = new ArrayAdapter<Expression>(this,
               android.R.layout.simple_list_item_1 , expressions);
        final ListView listView = (ListView) findViewById(R.id.expressions_list);
        listView.setAdapter(expAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final TextView evaluationArea = (TextView) findViewById(R.id.evaluation_id);
                evaluationArea.setText("");
                if(expressions.get(position) instanceof Application)
                {
                    final Application a = (Application) expressions.get(position);
                    a.setBetas("");
                    final Expression evaluation = a.evaluate();
                    if (evaluation != null)
                    {
                        final String evalString = evaluation.toString();
                        final String betaReductions = a.getBetas();
                        evaluationArea.setText(betaReductions
                                + "\n"
                                + evalString);
                        a.setDepth(0);
                    }
                    else
                    {
                        evaluationArea.setText("\nThis expression is infinitely recursive!");
                        a.setDepth(0);
                    }
                }
                else
                    evaluationArea.setText("\n" + expressions.get(position).toString());
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.expressions_list)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(expressions.get(info.position).toString());
            menu.add(R.string.contextMenu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final int menuItemIndex = item.getItemId();

        if(menuItemIndex == 0)
        {
            expAdapter.remove(expressions.get(info.position));
            final TextView evaluationArea = (TextView) findViewById(R.id.evaluation_id);
            evaluationArea.setText("");
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_clear:
                new AlertDialog.Builder(this)
                        .setTitle("Clear")
                        .setMessage("This will clear all expressions. Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                expAdapter.clear();
                                final TextView evaluationArea = (TextView) findViewById(R.id.evaluation_id);
                                evaluationArea.setText("");
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            case R.id.action_tutorial:
                openTutorial();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void buildName(final View view)
    {
        final LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.build_name_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(v)
                .setTitle("Define a name:")
                .setMessage("Only alphanumeric characters allowed")
                .setPositiveButton(R.string.button_send, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Boolean hasNonCharOrDigit = false;
                        final EditText input = (EditText) v.findViewById(R.id.create_name);
                        final String name = input.getText().toString();

                        for (int i = 0; i < name.length(); i++)
                            if (!Character.isLetterOrDigit(name.toCharArray()[i]))
                                hasNonCharOrDigit = true;

                        if (!hasNonCharOrDigit && !name.equals("")) {
                            final Name newName = new Name(name);
                            expAdapter.add(newName);
                            dialog.cancel();
                        }
                        else
                        {
                            dialog.cancel();
                            new AlertDialog.Builder(view.getContext())
                                    .setMessage("ERROR: Only alphanumeric characters allowed!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.cancel();
                                            buildName(view);
                                        }
                                    })
                                    .show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                })
        .show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void buildFunction(View view)
    {
        final ArrayList<Name> names = new ArrayList<>();
        //fill with names
        for(Expression e : expressions)
            if(e instanceof Name)
                names.add((Name) e);

        final LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.build_function_dialog, null);
        final Spinner names_spinner = (Spinner) v.findViewById(R.id.names_spinner);
        final Spinner exps_spinner = (Spinner) v.findViewById(R.id.exps_spinner);
        final ArrayAdapter<Name> namesAdapter = new ArrayAdapter<Name>(this,
                android.R.layout.simple_spinner_item, names);
        namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        names_spinner.setAdapter(namesAdapter);
        expAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exps_spinner.setAdapter(expAdapter);

        new AlertDialog.Builder(this)
                .setView(v)
                .setTitle("Define a function:")
                .setMessage("λ<name>.<expression>")
                .setPositiveButton(R.string.button_send, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (names.isEmpty() || expAdapter.isEmpty())
                        {
                            dialog.cancel();
                            return;
                        }

                        //Retrieve name and exp, make a new Function and add it to expressions
                        final Spinner name_f = (Spinner) v.findViewById(R.id.names_spinner);
                        final Spinner exp_f = (Spinner) v.findViewById(R.id.exps_spinner);
                        final Name n = new Name(names.get(name_f.getSelectedItemPosition()).getName());
                        final Expression e = expressions.get(exp_f.getSelectedItemPosition());
                        final Expression exp;

                        if (n == null || e == null)
                            dialog.cancel();

                        if (e instanceof Name)
                            exp = new Name(((Name) e).getName());
                        else if (e instanceof Function)
                            exp = new Function(((Function) e).getName(), ((Function) e).getBody());
                        else
                            exp = new Application(((Application) e).getFunction(), ((Application) e).getArgument());

                        final Function f = new Function(n, exp);
                        expAdapter.add(f);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void buildApplication(View view)
    {
        final LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.build_application_dialog, null);
        final Spinner exps1_spinner = (Spinner) v.findViewById(R.id.exps1_spinner);
        final Spinner exps2_spinner = (Spinner) v.findViewById(R.id.exps2_spinner);
        expAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exps1_spinner.setAdapter(expAdapter);
        exps2_spinner.setAdapter(expAdapter);

        new AlertDialog.Builder(this)
                .setView(v)
                .setTitle("Define an application:")
                .setMessage("<expression><expression>")
                .setPositiveButton(R.string.button_send, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (expAdapter.isEmpty())
                        {
                            dialog.cancel();
                            return;
                        }

                        final Spinner exp1s = (Spinner) v.findViewById(R.id.exps1_spinner);
                        final Spinner exp2s = (Spinner) v.findViewById(R.id.exps2_spinner);
                        final Expression e1 = expressions.get(exp1s.getSelectedItemPosition());
                        final Expression e2 = expressions.get(exp2s.getSelectedItemPosition());
                        final Expression exp1;
                        final Expression exp2;

                        if (e1 instanceof Name)
                            exp1 = new Name(((Name) e1).getName());
                        else if (e1 instanceof Function)
                            exp1 = new Function(((Function) e1).getName(), ((Function) e1).getBody());
                        else
                            exp1 = new Application(((Application) e1).getFunction(), ((Application) e1).getArgument());

                        if (e2 instanceof Name)
                            exp2 = new Name(((Name) e2).getName());
                        else if (e2 instanceof Function)
                            exp2 = new Function(((Function) e2).getName(), ((Function) e2).getBody());
                        else
                            exp2 = new Application(((Application) e2).getFunction(), ((Application) e2).getArgument());

                        final Application a = new Application(exp1, exp2);
                        expAdapter.add(a);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void openTutorial()
    {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }
}
