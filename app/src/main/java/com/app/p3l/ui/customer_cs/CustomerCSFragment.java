package com.app.p3l.ui.customer_cs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.p3l.R;

public class CustomerCSFragment extends Fragment {
    private CustomerCSViewModel customerCSViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerCSViewModel =
                ViewModelProviders.of(this).get(CustomerCSViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cs_customer, container, false);
        final TextView textView = root.findViewById(R.id.text_cs_customer);
        customerCSViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
