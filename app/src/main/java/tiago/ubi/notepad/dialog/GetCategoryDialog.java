package tiago.ubi.notepad.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import tiago.ubi.notepad.R;
import tiago.ubi.notepad.model.Category;

public class GetCategoryDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextPassword;
    private GetCategoryDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.getcategory_layout, null);

        builder.setView(view)
                .setTitle("Category")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String password = editTextPassword.getText().toString();
                        listener.getDialogInput(name,password);
                    }
                });
        editTextName = view.findViewById(R.id.get_name);
        editTextPassword = view.findViewById(R.id.get_password);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (GetCategoryDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement GetCategoryDialogListener");
        }
    }

    public interface GetCategoryDialogListener {
        void getDialogInput(String name, String password);
    }
}
