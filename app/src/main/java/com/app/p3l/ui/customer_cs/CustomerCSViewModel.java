package com.app.p3l.ui.customer_cs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
