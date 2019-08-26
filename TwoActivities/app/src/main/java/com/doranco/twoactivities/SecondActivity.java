package com.doranco.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    TextView mReceivedTextView;

    // déclaration de la key extra reply
    public static final String EXTRA_REPLY =
            "com.doranco.twoactivities.extra.REPLY";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mReceivedTextView = findViewById(R.id.text_message);
        mReply = findViewById(R.id.editText_second);
        mReceivedTextView.setText(message);
    }

    public void returnReply(View view) {
        String reply = mReply.getText().toString();
        // on vide l'edit text après envoi réponse
        mReply.setText("");
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK,replyIntent);
        finish();
    }
}
