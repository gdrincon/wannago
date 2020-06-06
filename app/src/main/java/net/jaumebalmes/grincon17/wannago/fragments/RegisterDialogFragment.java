package net.jaumebalmes.grincon17.wannago.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.jaumebalmes.grincon17.wannago.interfaces.OnRegisterListener;
import net.jaumebalmes.grincon17.wannago.R;

import java.util.Objects;

public class RegisterDialogFragment extends DialogFragment {
    private OnRegisterListener mListener;
    private EditText userName;
    private EditText pwd;
    private EditText email;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_register_dialog, null);
        userName = view.findViewById(R.id.textEditNewUserName);
        pwd = view.findViewById(R.id.textEditNewPwd);
        email = view.findViewById(R.id.textEditNewEmail);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view);
        builder.setPositiveButton(R.string.register_txt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = String.valueOf(userName.getText());
                        String pass = String.valueOf(pwd.getText());
                        String mail = String.valueOf(email.getText());
                        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(mail)) {
                            mListener.onRegisterClickListener(name, pass, mail);
                        } else {
                            Toast.makeText(getContext(), "Must not be empty", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });



        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnRegisterListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
