package com.ceshi.ha.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 工具类
 */
public class SPUtils {

    private final String SHARED_PATH = "data";
    private SharedPreferences sharedPreferences;
    private Crypto crypto = new Crypto("zhangzhong");
    private static SPUtils spUtils;

    private SPUtils(Context context) {
        getDefaultSP(context);
    }

    public static SPUtils getSPUtil(Context context) {
        if (spUtils == null) {
            spUtils = new SPUtils(context);
        }
        return spUtils;
    }

    public SharedPreferences getDefaultSP(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void putInt(String key, int value) {
        Editor edit = sharedPreferences.edit();
        edit.putInt(crypto.encrypt(key), value);
        edit.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(crypto.encrypt(key), 0);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(crypto.encrypt(key), defValue);
    }

    public void putString(String key, String value) {
        Editor edit = sharedPreferences.edit();
        edit.putString(crypto.encrypt(key), crypto.encrypt(value));
        edit.commit();
    }

    public String getString(String key) {
        String value = sharedPreferences.getString(crypto.encrypt(key), null);
        if (!StrUtil.isEmpty(value)) {
            value = crypto.decrypt(value);
        }
        return value;
    }

    public String getString(String key, String defValue) {
        String value = sharedPreferences.getString(crypto.encrypt(key), defValue);
        if (!StrUtil.isEmpty(value)) {
            value = crypto.decrypt(value);
        }
        return value;
    }

    public void putBoolean(String key, boolean value) {
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void remove(String key) {
        Editor edit = sharedPreferences.edit();
        edit.remove(crypto.encrypt(key));
        edit.commit();
    }

    /****
     * 清除所有的SharedPreference
     *
     * @param context
     */
    public void cleanAllSharedPreference(Context context) {
        final Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
