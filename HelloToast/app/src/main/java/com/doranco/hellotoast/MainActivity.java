package com.doranco.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String COUNT_KEY = "counter_key";
    Integer counter=0;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.show_count);

        // Méthode 1: sauvegarde des données après changement d'orientation
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter_key");
            if (mTextView != null)
                mTextView.setText(counter.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNT_KEY, counter);
    }

    // Méthode 2: sauvegarde des données après changement d'orientation
    /*@Override
    public void onRestoreInstanceState (Bundle mySavedState) {
        super.onRestoreInstanceState(mySavedState);
        if (mySavedState != null) {
            counter = mySavedState.getInt("counter_key");
            if (counter != null)
                mTextView.setText(counter.toString());
        }
    }*/

    // affichage d'un toast dans l'application
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
