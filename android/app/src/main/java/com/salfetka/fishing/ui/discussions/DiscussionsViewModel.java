package com.salfetka.fishing.ui.discussions;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salfetka.fishing.models.discussions.Message;

import java.util.ArrayList;
import java.util.List;

public class DiscussionsViewModel extends ViewModel {
    final MutableLiveData<List<Message>> messages;

    public DiscussionsViewModel() {
        ArrayList<Message> exampleMessages = new ArrayList<>();
        exampleMessages.add(new Message(0, "User1", "Первое сообщение в чате"));
        exampleMessages.add(new Message(1, "User1", "Второе сообщение в чате"));
        exampleMessages.add(new Message(2, "User2", "Простое случайное очень длинное сообщение от другого пользователя :) :) :)"));
        exampleMessages.add(new Message(3, "User3", "😊😊😊👌😎"));
        exampleMessages.add(new Message(4, "User1", "😊😊😊👌😎"));
        exampleMessages.add(new Message(5, "User2", "Печатаем дополнительное сообщение, чтобы проверить прокрутку сообщений: аааааааааааа аааааааааааааа аааааааааааааа аааааааааааа ааааааааааа аааааааааааааа ааааааааааааааа ааааааааааа ааа аа аа аа аа аа  ааа"));
        messages = new MutableLiveData<>(exampleMessages);
    }

    public MutableLiveData<List<Message>> getMessages() {
        return messages;
    }
}