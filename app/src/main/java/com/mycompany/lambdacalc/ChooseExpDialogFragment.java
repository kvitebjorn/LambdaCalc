package com.mycompany.lambdacalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 *
 * @author Kyle Harrington <gapthrosnir at gmail.com>
 */
public class ChooseExpDialogFragment extends android.support.v4.app.DialogFragment
{
    public interface ChooseExpDialogListener
    {
        public void onDialogNameClick(DialogFragment dialog);
        public void onDialogFunctionClick(DialogFragment dialog);
        public void onDialogApplicationClick(DialogFragment dialog);
    }

    ChooseExpDialogListener eListener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try
        {
            // Instantiate the DialogListener so we can send events to the host
            eListener = (ChooseExpDialogListener) activity;
        }
        catch (ClassCastException e)
        {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ChooseExpDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_exp);
        builder.setItems(R.array.exp_array, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                // The 'which' argument contains the index position
                // of the selected item
                switch(which)
                {
                    case 0:
                        eListener.onDialogNameClick(ChooseExpDialogFragment.this);
                        break;
                    case 1:
                        eListener.onDialogFunctionClick(ChooseExpDialogFragment.this);
                        break;
                    case 2:
                        eListener.onDialogApplicationClick(ChooseExpDialogFragment.this);
                        break;
                    default:
                        eListener.onDialogApplicationClick(ChooseExpDialogFragment.this);
                }
            }
        });
        return builder.create();
    }
}
