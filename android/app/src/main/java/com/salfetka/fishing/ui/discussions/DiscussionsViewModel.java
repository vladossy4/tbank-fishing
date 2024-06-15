package com.salfetka.fishing.ui.discussions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DiscussionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is discussions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}