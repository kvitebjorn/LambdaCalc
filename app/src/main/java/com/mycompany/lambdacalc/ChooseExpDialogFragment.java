package com.mycompany.lambdacalc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 *
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */
public class ChooseExpDialogFragment extends android.support.v4.app.DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_exp)
                .setItems(R.array.exp_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // The 'which' argument contains the index position
                        // of the selected item
                        // navigate to the appropriate build activity here depending on index
                    }
                });
        return builder.create();
    }
}
