package com.peater.goos;

import android.content.Context;
import android.content.SharedPreferences;

public class PeaterDB {
    private static String peater = "peater";
    private SharedPreferences preferences;

    public PeaterDB(Context context){
        String NAME = "peater";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setPeater(String data){
        preferences.edit().putString(PeaterDB.peater, data).apply();
    }

    public String getPeater(){
        return preferences.getString(peater, "");
    }
}
