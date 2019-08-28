package com.doranco.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
