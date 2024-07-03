package com.salfetka.fishing.ui.wiki;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WikiViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WikiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is wiki fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}