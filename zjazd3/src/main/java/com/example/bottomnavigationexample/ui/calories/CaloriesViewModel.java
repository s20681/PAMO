package com.example.bottomnavigationexample.ui.calories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CaloriesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CaloriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calories fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}