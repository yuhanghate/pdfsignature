package com.example.pdf_signature.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 设备信息保存
 */
public class SharedPreferencesUtils {
    private static final String PREF_NAME = "MyPrefs";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void removeKey(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void saveAddress(Context context, String address) {
        saveString(context, "address", address);
    }

    public static void saveSignApi(Context context, String sign) {
        saveString(context, "signApi", sign);
    }

    public static void saveListApi(Context context, String api) {
        saveString(context, "listApi", api);
    }



    /**
     * 获取服务器地址
     *
     * @param context
     * @return
     */
    public static String getAddress(Context context) {
        return getString(context, "address", "");
    }

    /**
     * 获取列表地址
     *
     * @param context
     * @return
     */
    public static String getListApi(Context context) {
        return getString(context, "listApi", "");
    }

    public static String getSignApi(Context context) {
        return getString(context, "signApi", "");
    }
}