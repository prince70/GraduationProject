package com.niwj.graduationproject.api.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharePreferenceUtil的帮助类
 */
public class SharePreferenceUtil {
	private SharedPreferences sp;

    private SharePreferenceUtil(SharedPreferences sp){
        this.sp = sp;
    }

    public static SharePreferenceUtil getInstance(Context context) {
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(context);
        return new SharePreferenceUtil(sp);
    }

    public static SharePreferenceUtil getInstance(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName.trim(), context.MODE_PRIVATE);
        return new SharePreferenceUtil(sp);
    }

    public boolean hasKey(final String key) {
        return sp.contains(key);
    }

    //移除操作
    public void remove(String key){
        sp.edit().remove(key).commit();
    }

    //清空操作
    public void clear(){
        sp.edit().clear().commit();
    }
   
    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void setString(final String key, final String value) {
        sp.edit().putString(key, value).commit();
    }
    
    public boolean getBoolean(final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void setBoolean(final String key, final boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void setInt(final String key, final int value) {
        sp.edit().putInt(key, value).commit();
    }

    public int getInt(final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void setFloat(final String key, final float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public float getFloat(final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void setLong(final String key, final long value) {
        sp.edit().putLong(key, value).commit();
    }

    public long getLong(final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }
}
