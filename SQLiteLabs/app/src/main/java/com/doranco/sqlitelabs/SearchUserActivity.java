package com.doranco.sqlitelabs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.doranco.db.DbHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchUserActivity extends AppCompatActivity {
    private TextView mUserIdTv;
    private EditText mUserIdEt;
    private Button mSearchBtn;
    private TextView mUserFound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        mUserIdTv = findViewById(R.id.user_id);
        mUserIdEt = findViewById(R.id.user_id_edittext);
        mSearchBtn = findViewById(R.id.searchBtn);
        mUserFound = findViewById(R.id.user_found);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = mUserIdEt.getText().toString();
                if(!userId.isEmpty()) {
                    // BDD
                    DbHandler db = new DbHandler(SearchUserActivity.this); // ou pour le context on peut aussi mettre getBaseContext()
                    // Requête
                    ArrayList<HashMap<String, String>> user = db.getUserByUserId(Integer.parseInt(userId));
                    if(!user.isEmpty()) {
                        mUserFound.setVisibility(View.VISIBLE);
                        // Récupérer les données (le user)
                        HashMap<String, String> myUser = user.get(0);
                        String name = myUser.get(DbHandler.KEY_NAME);
                        String location = myUser.get(DbHandler.KEY_LOC);
                        String designation = myUser.get(DbHandler.KEY_DESG);
                        // Afficher message
                        mUserFound.setText(String.format(getString(R.string.user_info), name, location, designation));

                    } else {
                        mUserFound.setVisibility(View.INVISIBLE);
                        Toast.makeText(SearchUserActivity.this, getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(SearchUserActivity.this, getString(R.string.enter_user_id), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
