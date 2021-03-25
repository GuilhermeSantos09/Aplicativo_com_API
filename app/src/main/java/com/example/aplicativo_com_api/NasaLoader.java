package com.example.aplicativo_com_api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONObject;

public class NasaLoader extends AsyncTaskLoader<JSONObject> {
    public NasaLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {
        return NetworkUtils.query("mars portrait");
    }
}
