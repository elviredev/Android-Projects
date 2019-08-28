package com.doranco.servicelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doranco.services.MyIntentService;
import com.doranco.services.MyService;

public class MainActivity extends AppCompatActivity {
    private Button mButtonService;
    private Button mButtonIntentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonService = findViewById(R.id.btn_service);
        mButtonIntentService = findViewById(R.id.btn_intent_service);

        mButtonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonService.getText().toString().equals(getString(R.string.start_service))) {
                    mButtonService.setText(getString(R.string.stop_service));
                    Intent stopServiceIntent = new Intent(MainActivity.this, MyService.class);
                    stopService(stopServiceIntent);
                } else {
                    mButtonService.setText(getString(R.string.start_service));
                    startService(new Intent(MainActivity.this, MyService.class));
                }
            }
        });


    }

    public void intentServiceManagement(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        if (mButtonIntentService.getText().toString()
                .equals(getString(R.string.start_an_intent_service))) {
            mButtonIntentService.setText(getString(R.string.stop_an_intent_service));
            startService(intent);
        } else {
            mButtonIntentService.setText(getString(R.string.start_an_intent_service));
            stopService(intent);
        }

    }
}
