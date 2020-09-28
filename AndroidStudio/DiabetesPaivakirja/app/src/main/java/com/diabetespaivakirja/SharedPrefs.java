package com.diabetespaivakirja;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SharedPrefs extends AppCompatActivity {
    private String name = "prefs";

    SharedPrefs() {

    }

    SharedPrefs(String name) {
        this.name = name;
    }

    // Shared Preferences

    private SharedPreferences getSharedPrefs() {
        return getSharedPreferences(this.name, Activity.MODE_PRIVATE);
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
