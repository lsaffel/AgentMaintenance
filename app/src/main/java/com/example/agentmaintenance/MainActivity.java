package com.example.agentmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;
/*
    Author: Lisa Saffel
    Date: October 4, 2020
    Purpose: Android app to add, update or delete agents in a SQLite database
    Works best with an Android emulator: Nexus 7 (2012) API 30
*/
public class MainActivity extends AppCompatActivity {
    DataSource dataSource;
    Button btnAdd;
    ListView lvAgents;
    ArrayAdapter<Agent> adapter;

    // this app uses a sqlite database.
    // It needs a datasource class, which will have methods to get,
    // insert, update and delete data from a table.
    // It needs a helper class that will enable us to open, close, create,
    // and upgrade tables.
    // It needs an entity class for each table so we can create objects
    // and send them to the database for processing.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);
        btnAdd = findViewById(R.id.btnAdd);
        lvAgents = findViewById(R.id.lvAgents);

        // add a listener to the button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("mode", "insert");
                startActivity(intent);
            }
        });

        // add a listener to the listview
        lvAgents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("agt", adapter.getItem(position));
                startActivity(intent);
            }
        });
        // load all agents from the database
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // load all agents from the database
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // load all agents from the database
        loadData();
    }

    private void loadData() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSource.getAllAgents());
        lvAgents.setAdapter(adapter);
    }
}