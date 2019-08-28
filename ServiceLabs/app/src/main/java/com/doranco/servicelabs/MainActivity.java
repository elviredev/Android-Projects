package com.doranco.servicelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doranco.services.MyService;

public class MainActivity extends AppCompatActivity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButton.getText().toString().equals(getString(R.string.start_service))) {
                    mButton.setText(getString(R.string.stop_service));
                    Intent stopServiceIntent = new Intent(MainActivity.this, MyService.class);
                    stopService(stopServiceIntent);
                } else {
                    mButton.setText(getString(R.string.start_service));
                    startService(new Intent(MainActivity.this, MyService.class));
                }
            }
        });


    }
}
