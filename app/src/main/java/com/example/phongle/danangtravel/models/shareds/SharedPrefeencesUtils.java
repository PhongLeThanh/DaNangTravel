package com.example.phongle.danangtravel.models.shareds;

import android.text.TextUtils;

import com.example.phongle.danangtravel.models.User;
import com.google.gson.Gson;

public final class SharedPrefeencesUtils {
    private static final String AUTH_TOKEN = "auth_token";
    private static final String USER_KEY = "user";

    public SharedPrefeencesUtils() {
    }

    public static void putDocument(String document) {
        SharedPrefsFactory.getInstance().put(AUTH_TOKEN, document);
    }

    public static String getDocument() {
        String document = SharedPrefsFactory.getInstance().get(AUTH_TOKEN, String.class);
        return TextUtils.isEmpty(document) ? null : document;
    }

    public static void clear() {
        SharedPrefsFactory.getInstance().clear();
    }

    public static void putUser(User user) {
        Gson gson = new Gson();
        SharedPrefsFactory.getInstance().put(USER_KEY, gson.toJson(user));
    }

    public static User getUser() {
        String userStr = SharedPrefsFactory.getInstance().get(USER_KEY, String.class);
        Gson gson = new Gson();
        return gson.fromJson(userStr, User.class);
    }
}
