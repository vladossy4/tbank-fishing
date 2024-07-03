package com.salfetka.fishing.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_OBJECT = "key_object";

    public static void saveObject(Context context, String key, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(key, json);
        editor.apply();
    }

    public static <T> T getObject(Context context, String key, Class<T> classOfT) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        return gson.fromJson(json, classOfT);
    }

    public static <T> T getObjectList(Context context, String key, Type type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        return gson.fromJson(json, type);
    }
}
