package com.mycompany.lambdacalc;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements ChooseExpDialogFragment.ChooseExpDialogListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void buildExpression(View view)
    {
        ChooseExpDialogFragment expChooser = new ChooseExpDialogFragment();
        showChooseExpDialog();
    }

    public void showChooseExpDialog()
    {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new ChooseExpDialogFragment();
        dialog.show(getSupportFragmentManager(), "ChooseExpDialogFragment");
    }

    @Override
    public void onDialogNameClick(DialogFragment dialog)
    {
        Intent intent = new Intent(this, BuildNameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogFunctionClick(DialogFragment dialog)
    {
        Intent intent = new Intent(this, BuildFunctionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogApplicationClick(DialogFragment dialog)
    {
        Intent intent = new Intent(this, BuildApplicationActivity.class);
        startActivity(intent);
    }
}
