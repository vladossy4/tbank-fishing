package com.salfetka.fishing.models.discussions;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Message {
    final int id;
    final Calendar date;
    final String userName;
    final String message;

    public Message(int id, String userName, String message) {
        this.id = id;
        date = new GregorianCalendar();
        this.userName = userName;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Calendar getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }
}
