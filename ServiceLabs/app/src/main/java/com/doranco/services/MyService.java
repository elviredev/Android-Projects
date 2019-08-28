package com.doranco.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.doranco.servicelabs.R;

public class MyService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public MyService() {

        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, getApplicationContext().getString(R.string.starting_service), Toast.LENGTH_SHORT).show();
        return START_STICKY; // Dire a android de redemarrer le service quand il aura suffisamment de mémoire
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, getApplicationContext().getString(R.string.stopping_service), Toast.LENGTH_SHORT).show();
    }
}
