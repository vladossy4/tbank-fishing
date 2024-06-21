package com.salfetka.fishing.ui.discussions;


import android.content.ClipData;
import android.content.ClipboardManager;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salfetka.fishing.models.discussions.Message;

import java.util.GregorianCalendar;
import java.util.HashSet;

/** Управление данными о переписках */
public class DiscussionsViewModel extends ViewModel {
    private final MutableLiveData<ObservableList<Message>> messages = new MutableLiveData<>();

    public DiscussionsViewModel() {
        ObservableArrayList<Message> exampleMessages = new ObservableArrayList<>();
        exampleMessages.add(new Message(new GregorianCalendar(2023, 10, 19, 10, 15),"User1", "Первое сообщение в чате"));
        exampleMessages.add(new Message(new GregorianCalendar(2024, 2, 7, 12, 21),"User1", "Второе сообщение в чате"));
        exampleMessages.add(new Message(new GregorianCalendar(2024, 2, 7, 12, 22),"XxxxX", "Xxxxxx xxxxxxxxx x xxxx"));
        exampleMessages.add(new Message(new GregorianCalendar(2024, 4, 30, 22, 55),"User2", "Простое случайное очень длинное сообщение от другого пользователя :) :) :)"));
        exampleMessages.add(new Message(new GregorianCalendar(2024, 5, 2, 11, 9),"User3", "😊😊😊👌😎"));
        exampleMessages.add(new Message(new GregorianCalendar(2024, 5, 19, 15, 17),"User1", "😊😊😊👌😎"));
        exampleMessages.add(new Message(new GregorianCalendar(2024, 5, 19, 19, 45),"User2", "Печатаем дополнительное сообщение, чтобы проверить прокрутку сообщений: аааааааааааа аааааааааааааа аааааааааааааа аааааааааааа ааааааааааа аааааааааааааа ааааааааааааааа ааааааааааа ааа аа аа аа аа аа  ааа"));
        messages.setValue(exampleMessages);
    }

    public MutableLiveData<ObservableList<Message>> getMessages() {
        return messages;
    }

    /** Отправление сообщения от пользователя
     * @param message текст сообщения*/
    public void sendMessage(String message){
        if (messages.getValue() != null) {
            messages.getValue().add(new Message(new GregorianCalendar(), "Slava", message));
        }
    }

    public void copyMessages(HashSet<Integer> selectedMessages, ClipboardManager clipboardManager){
        StringBuilder allSelected = new StringBuilder();
        selectedMessages.forEach( i -> {
            allSelected.append(messages.getValue().get(i).getMessage()).append("\n");
        });
        ClipData clip = ClipData.newPlainText("", allSelected);
        clipboardManager.setPrimaryClip(clip);
    }
}