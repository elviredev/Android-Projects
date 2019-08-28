package com.doranco.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    private final static String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService(){
        super(TAG); // égal au simple name MyIntentService
    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) { // cette méthode s'exécute en background
        Log.d(TAG, "Intent service is running...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "We are done with the intent service, bye ;)");
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "Starting an intent service");
        Toast.makeText(MyIntentService.this, "Intent service started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Stopping an intent service");
        Toast.makeText(MyIntentService.this, "Intent service stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
