package com.doranco.hellotoastchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Integer counter=0;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.show_count);
    }

    /*public void showToast(View view) {
        String msg = (String) getString(R.string.msgToast);
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }*/

    public void incrementCount(View view) {
        counter++;
        mTextView.setText(counter.toString());
    }

    public void decrementCount(View view) {
        counter--;
        mTextView.setText(counter.toString());
    }
}
