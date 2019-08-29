package com.doranco.sqlitelabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.doranco.db.DbHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Récupérer BDD
        DbHandler db = new DbHandler(this);
        // Récupérer List de users
        ArrayList<HashMap<String, String>> users = new ArrayList<>();
        users = db.getUsers();
        mListView = findViewById(R.id.user_list);

        // Créer un Adapter pour adapter les views afin de les afficher dans le RecyclerView
        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, users, R.layout.user_item,
                new String[]{"name", "location", "designation"},
                new int[]{R.id.name, R.id.location, R.id.designation});
        mListView.setAdapter(adapter);

        // Récupérer le Button Back et ajout Listener onClick()
        mBack = findViewById(R.id.btnBack);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
