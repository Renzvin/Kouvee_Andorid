package com.app.p3l.ui.Logout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.p3l.Activity.LoginActivity;
import com.app.p3l.R;

public class Logout extends Fragment {
    Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        showDialog();
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Logout");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda yakin ingin Logout?")
                .setIcon(R.drawable.paw)
                .setPositiveButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Edit
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete
                        Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}
