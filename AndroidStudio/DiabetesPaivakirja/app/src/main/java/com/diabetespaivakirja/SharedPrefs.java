package com.diabetespaivakirja;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPrefs {
    private String name = "prefs";
    Activity activity;

    SharedPrefs(Activity activity) {
        this.activity = activity;
    }

    SharedPrefs(Activity activity, String name) {
        this.activity = activity;
        this.name = name;
    }

    // Shared Preferences

    private SharedPreferences getSharedPrefs() {
        return this.activity.getSharedPreferences(this.name, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getSharedPrefsEditor(SharedPreferences SharedPrefs) {
        return SharedPrefs.edit();
    }

    public boolean prefsCommit() {
        return getSharedPrefsEditor(getSharedPrefs()).commit();
    }

    public void putPref(String prefName, int value) {
        SharedPreferences.Editor prefEditor = getSharedPrefsEditor(getSharedPrefs());
        prefEditor.putInt(prefName, value);
        prefEditor.commit();
    }

    public void putPref(String prefName, String value) {
        SharedPreferences.Editor prefEditor = getSharedPrefsEditor(getSharedPrefs());
        prefEditor.putString(prefName, value);
        prefEditor.commit();
    }

    public int getPrefInt(String prefName) {
        SharedPreferences prefGet = getSharedPrefs();
        return prefGet.getInt(prefName, 0);
    }

    public String getPrefString(String prefName) {
        SharedPreferences prefGet = getSharedPrefs();
        return prefGet.getString(prefName, "");
    }

    // Default Shared Preferences

    private SharedPreferences getDefaultSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getDefaultPrefString(String prefName, Context context) {
        return getDefaultSharedPrefs(context).getString(prefName, "");
    }

    public int getDefaultPrefInt(String prefName, Context context) {
        return getDefaultSharedPrefs(context).getInt(prefName, 0);
    }
}
