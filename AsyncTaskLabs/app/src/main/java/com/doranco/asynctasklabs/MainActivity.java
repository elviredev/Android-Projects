package com.doranco.asynctasklabs;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TEXT_STATE = "currentText";
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = findViewById(R.id.textView1);
        mButton = findViewById(R.id.button);

        // Restore TextView if there is a savedInstanceState
        if(savedInstanceState!=null){
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }

        //mTextView.setText("Nex text");

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText(R.string.changing_stuff);
            }
        });


    }

    public void startTask(View view){
        Toast.makeText(this, getString(R.string.clicking_start), Toast.LENGTH_SHORT).show();
        mTextView.setText(getString(R.string.testing_text));


        // Start the AsyncTask.
        SimpleAsyncTask sat = new SimpleAsyncTask(getBaseContext(), mTextView);
        sat.execute();
    }

    // Save the state of the TextView
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

}
