package com.example.agentmaintenance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
/*
    Author: Lisa Saffel
    Date: October 4, 2020
    Purpose: Android app to add, update or delete agents in a SQLite database
*/

public class DataSource {
    private Context context;
    private SQLiteDatabase db;
    private DBHelper helper;

    public DataSource(Context context) {
        this.context = context;
        Log.d("lisa", "context: " + context);
        // create helper and open database
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public Agent getAgent(int agentId)
    {
        String sql = "select * from Agents where AgentId=?";    // before we submit this, we can
                                                                // replace the ? with the actual value
        String [] args = { agentId + ""};
        Cursor cursor = db.rawQuery(sql, args);

        // position the cursor on the next / first row

        cursor.moveToNext();
        // create an agent using this row
       return new Agent(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
               cursor.getString(3), cursor.getString(4), cursor.getString(5),
               cursor.getString(6), cursor.getInt(7));
    }

    public ArrayList<Agent> getAllAgents()
    {
        ArrayList<Agent> list = new ArrayList<>();
        String [] columns = { "AgentId", "AgtFirstName", "AgtMiddleInitial", "AgtLastName",
                "AgtBusPhone", "AgtEmail", "AgtPosition", "AgencyId" };
                // these above have to match the columns on the database that we will pull out and bring back
        Cursor cursor = db.query("Agents", columns, null, null, null, null,
                null );
        while (cursor.moveToNext())
        {
            // add a new Agent to our arraylist
            list.add(new Agent(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getInt(7)));
        }
        return list;
    }

    public long insertAgent(Agent agt)
    {
        ContentValues cv = new ContentValues();
        cv.put("AgtFirstName", agt.getAgtFirstName());
        cv.put("AgtMiddleInitial", agt.getAgtMiddleInitial());
        cv.put("AgtLastName", agt.getAgtLastName());
        cv.put("AgtBusPhone", agt.getAgtBusPhone());
        cv.put("AgtEmail", agt.getAgtEmail());
        cv.put("AgtPosition", agt.getAgtPosition());
        cv.put("AgencyId", agt.getAgencyId());
        return db.insert("Agents", null, cv);
    }

    public long updateAgent(Agent agt)
    {
        ContentValues cv = new ContentValues();
        cv.put("AgtFirstName", agt.getAgtFirstName());
        cv.put("AgtMiddleInitial", agt.getAgtMiddleInitial());
        cv.put("AgtLastName", agt.getAgtLastName());
        cv.put("AgtBusPhone", agt.getAgtBusPhone());
        cv.put("AgtEmail", agt.getAgtEmail());
        cv.put("AgtPosition", agt.getAgtPosition());
        String [] args = { agt.getAgentId() + "" };          // + "" is to force it to turn into a string, since it's a string array
        String where = "AgentId = ?";
        return db.update("Agents", cv, where, args);    // that will update the database and send back a 1
                                    // if it's successful. It will send back a 0 if no rows were modified.
    }

    public long deleteAgent(int agentId)
    {
        String [] args = { agentId + "" };          // + "" is to force it to turn into a string, since it's a string array
        String where = "AgentId = ?";
        return db.delete("Agents", where, args);
    }
}
