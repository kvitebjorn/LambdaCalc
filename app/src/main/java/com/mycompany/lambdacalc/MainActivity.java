package com.mycompany.lambdacalc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
        Name x = new Name("x");
        Function X = new Function(x,x);

        //provide an initial name x and its identity function
        expressions.add(x);
        expressions.add(X);

        //array adapter for the list of expressions
        expAdapter = new ArrayAdapter<Expression>(this,
               android.R.layout.simple_list_item_1 , expressions);
        ListView listView = (ListView) findViewById(R.id.expressions_list);
        listView.setAdapter(expAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buildName(View view)
    {
        final LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.build_name_dialog, null);
        new AlertDialog.Builder(this)
                .setView(v)
                .setTitle("Enter a string:")
                .setPositiveButton(R.string.button_send, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        EditText input = (EditText) v.findViewById(R.id.create_name);
                        String name = input.getText().toString();
                        Name newName = new Name(name);
                        expAdapter.add(newName);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                })
        .show();
    }

    public void buildFunction(View view)
    {

    }

    public void buildApplication(View view)
    {

    }
}
