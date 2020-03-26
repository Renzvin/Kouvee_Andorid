package com.app.p3l.ui.customer_cs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.app.p3l.R;
import com.app.p3l.ui.customer.CustomerViewModel;

public class CustomerCSViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomerCSViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is customer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
