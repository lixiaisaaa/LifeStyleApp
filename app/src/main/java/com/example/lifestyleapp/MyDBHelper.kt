package com.example.lifestyleapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.example.lifestyleapp.MyDBHelper

class MyDBHelper(context: Context, name: String) :
    SQLiteOpenHelper(context, name, null, 1) {

    companion object {
        const val DB_TABLE = "person"
    }

    override fun onCreate(db: SQLiteDatabase) {
        createDbTable(db, DB_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    private fun createDbTable(db: SQLiteDatabase, tableName: String) {
        db.execSQL(
            "CREATE TABLE " + tableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ",name VARCHAR(20)" +
                    ",age VARCHAR(20)" +
                    ",sex VARCHAR(20)" +
                    ",location VARCHAR(20)" +
                    ",height VARCHAR(20)" +
                    ",weight VARCHAR(20)" +
                    ",activityLevel VARCHAR(20)" +
                    ")"
        )
    }
}