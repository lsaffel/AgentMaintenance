package com.example.agentmaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
/*
    Author: Lisa Saffel
    Date: October 4, 2020
    Purpose: Android app to add, update or delete agents in a SQLite database
*/

public class DBHelper extends SQLiteOpenHelper {
    private static final int version = 2;       // anytime we want to force it to run the onUpgrade method,
    // we could just change the version number, put some new SQL into the onCreate and we could change this number
    // and if it doesn't match the database file's version number, it will trigger it to run the onUpgrade.
    // If the databse file doesn't exist, it will run the onCreate for us.
    private static final String name = "TravelExpertsSqlLite.db";   // the name of the database that the DBHelper will try to open

    // created by message saying "create constructor matching super"
    public DBHelper(@Nullable Context context) {
        // original generated version:       super(context, name, factory, version);
        super(context, name, null, version);
    }

    // two methods implemented since it said they were needed - implement methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("lisa", "creating database");
        String sql = "CREATE TABLE Agents (" +
                "`AgentId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "`AgtFirstName` VARCHAR(20)," +
                "`AgtMiddleInitial` VARCHAR(5)," +
                "`AgtLastName` VARCHAR(20)," +
                "`AgtBusPhone` VARCHAR(20)," +
                "`AgtEmail` VARCHAR(50)," +
                "`AgtPosition` VARCHAR(20)," +
                "`AgencyId` INT" +
                ")";
        db.execSQL(sql);
        sql = "insert into Agents values(0, 'Fred', 'J', 'Sanderson', '444-555-6666', 'fred@heyyou.com', 'Manager', 2 )";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("lisa", "upgrading database, old version: " + oldVersion + " new version: " + newVersion);

        db.execSQL("drop table Agents");
        onCreate(db);

    }
}
