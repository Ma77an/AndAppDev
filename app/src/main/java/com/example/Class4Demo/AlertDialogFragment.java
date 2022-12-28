package com.example.Class4Demo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sarit Haddad");
        builder.setMessage("Yalla go home Motty");
        builder.setPositiveButton("Shalom", (dialogInterface, i) -> {
            Toast.makeText(getContext(), "Ve Toda", Toast.LENGTH_LONG).show();
        });
        return builder.create();
    }
}