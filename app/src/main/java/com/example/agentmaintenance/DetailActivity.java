package com.example.agentmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.ProtectionDomain;
/*
    Author: Lisa Saffel
    Date: October 4, 2020
    Purpose: Android app to add, update or delete agents in a SQLite database
*/

public class DetailActivity extends AppCompatActivity {
    Button btnSave, btnDelete;
    EditText etAgentId, etAgtFirstName, etAgtMiddleInitial, etAgtLastName, etAgtBusPhone, etAgtEmail, etAgtPosition, etAgencyId;
    String mode;
    Agent agent;
    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dataSource = new DataSource(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        etAgentId = findViewById(R.id.etAgentId);
        etAgtFirstName = findViewById(R.id.etAgtFirstName);
        etAgtMiddleInitial = findViewById(R.id.etAgtMiddleInitial);
        etAgtLastName = findViewById(R.id.etAgtLastName);
        etAgtBusPhone = findViewById(R.id.etAgtBusPhone);
        etAgtEmail = findViewById(R.id.etAgtEmail);
        etAgtPosition = findViewById(R.id.etAgtPosition);
        etAgencyId = findViewById(R.id.etAgencyId);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        if (mode.equals("update"))
        {
            agent = (Agent) intent.getSerializableExtra("agt");
            etAgentId.setText(agent.getAgentId() + "");             // force this to be a string
            etAgtFirstName.setText(agent.getAgtFirstName());
            etAgtMiddleInitial.setText(agent.getAgtMiddleInitial());
            etAgtLastName.setText(agent.getAgtLastName());
            etAgtBusPhone.setText(agent.getAgtBusPhone());
            etAgtEmail.setText(agent.getAgtEmail());
            etAgtPosition.setText(agent.getAgtPosition());
            etAgencyId.setText(agent.getAgencyId() + "");             // force this to be a string
            btnDelete.setEnabled(true);
        }
        else
        {
            btnDelete.setEnabled(false);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("update"))              // we have data in the fields, so we want to take that data and put that into a new agent
                {
                    // add a toString to each text field to take them from an editable field to a string
                    Agent a = new Agent(Integer.parseInt(etAgentId.getText().toString()), etAgtFirstName.getText().toString(), etAgtMiddleInitial.getText().toString(), etAgtLastName.getText().toString(), etAgtBusPhone.getText().toString(), etAgtEmail.getText().toString(), etAgtPosition.getText().toString(), Integer.parseInt(etAgencyId.getText().toString()));

                    // send this agent object off to the data source
                    if  (dataSource.updateAgent(a) == -1)
                    {
                        Log.d("lisa", "update failed");
                    }
                    else
                    {
                        Log.d("lisa", "update successful");
                    }
                }
                else                        // the mode is insert
                {
                    // setting the agentId to 0 here
                    Agent a = new Agent(0, etAgtFirstName.getText().toString(), etAgtMiddleInitial.getText().toString(), etAgtLastName.getText().toString(), etAgtBusPhone.getText().toString(), etAgtEmail.getText().toString(), etAgtPosition.getText().toString(), Integer.parseInt(etAgencyId.getText().toString()));

                    // send this agent object off to the data source
                    long myResult = dataSource.insertAgent(a);
                    Log.d("lisa", "insert result: " + myResult);
                    if  (myResult == -1)
                    {
                        Log.d("lisa", "insert failed");
                    }
                    else
                    {
                        Log.d("lisa", "insert successful");
                    }

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send this agent object off to the data source
                if  (dataSource.deleteAgent(Integer.parseInt(etAgentId.getText().toString())) == -1)
                {
                    Log.d("lisa", "delete failed");
                }
                else
                {
                    Log.d("lisa", "delete successful");
                }

            }
        });
    }
}