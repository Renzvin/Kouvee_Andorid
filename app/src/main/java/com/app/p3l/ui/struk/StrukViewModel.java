package com.app.p3l.ui.struk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StrukViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public StrukViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is struk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
