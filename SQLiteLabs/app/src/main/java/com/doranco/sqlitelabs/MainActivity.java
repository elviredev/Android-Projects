package com.doranco.sqlitelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doranco.db.DbHandler;

public class MainActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mLocation;
    private TextView mDesignation;
    private EditText mUsername;
    private EditText mUserLocation;
    private EditText mUserDesignation;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.name);
        mLocation = findViewById(R.id.location);
        mDesignation = findViewById(R.id.designation);
        mUsername = findViewById(R.id.name_edittext);
        mUserLocation = findViewById(R.id.location_edittext);
        mUserDesignation = findViewById(R.id.designation_edittext);
        mSaveButton = findViewById(R.id.btnSave);

        // Event onClick()
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupère les éléments saisis par utilisateur
                String username = mUsername.getText().toString();
                String location = mLocation.getText().toString();
                String designation = mDesignation.getText().toString();
                // insertion d'un user dans SQLite
                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.insertUserDetails(username, location, designation);
                // lance l'activity DetailsActivity à partir de ma MainActivity
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, getString(R.string.details_saved), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
