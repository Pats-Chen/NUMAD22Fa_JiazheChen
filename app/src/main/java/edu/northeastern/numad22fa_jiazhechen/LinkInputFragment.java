package edu.northeastern.numad22fa_jiazhechen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LinkInputFragment extends AppCompatDialogFragment {
    private TextInputEditText linkName;
    private TextInputEditText linkString;
    private LinkDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_link_input, null);
        dialogBuilder.setView(view);
        dialogBuilder.setTitle("URL Input")
                .setPositiveButton("SUBMIT", (dialogInterface, i) -> {
                    String urlName = linkName.getText().toString();
                    String urlString = linkString.getText().toString();
                    listener.sendInfo(urlName, urlString);
                });

        linkName = view.findViewById(R.id.linkNameInput);
        linkString = view.findViewById(R.id.linkStringInput);

//        final EditText linkName = new EditText(getContext());
//        final EditText linkString = new EditText(getContext());

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (LinkDialogListener) context;
    }

    public interface LinkDialogListener {
        void sendInfo(String urlName, String urlString);
    }
}
