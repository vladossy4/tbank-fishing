package com.salfetka.fishing.models.discussions;

import java.util.Calendar;

/** Хранит данные о сообщении */
public class Message {
    static long idCounter = 0;
    final long id;
    final Calendar date;
    final String userName;
    final String message;

    /** Создание нового сообщения
     * @param id уникальный идентификатор сообщения
     * @param date время отправки сообщения
     * @param userName имя пользователя, отправившего сообщение
     * @param message текст сообщения
     * @see Message#Message(Calendar date, String userName, String message) */
    public Message(long id, Calendar date, String userName, String message) {
        this.id = id;
        this.date = date;
        this.userName = userName;
        this.message = message;
        if (id > idCounter) {
            idCounter = id + 1;
        }
    }
    /** Создание нового сообщения
     * @param date время отправки сообщения
     * @param userName имя пользователя, отправившего сообщение
     * @param message текст сообщения
     * @see Message#Message(long id, Calendar date, String userName, String message) */
    public Message(Calendar date, String userName, String message) {
        this(idCounter++, date, userName, message);
    }

    /** @return Получение уникального идентификатора сообщения */
    public long getId() {
        return id;
    }
    /** @return Получение времени отправки сообщения */
    public Calendar getDate() {
        return date;
    }
    /** @return Получение имени пользователя, отправившего сообщение */
    public String getUserName() {
        return userName;
    }
    /** @return Получение текста сообщения */
    public String getMessage() {
        return message;
    }
}
