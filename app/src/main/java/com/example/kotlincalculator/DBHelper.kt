package com.example.kotlincalculator

import DataClasses.Notes
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.core.database.getStringOrNull

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allNotes"
        private const val COLUMN_ID = "noteID"
        private const val COLUMN_TITLE = "noteName"
        private const val COLUMN_DESCRIPTION = "noteDescription"
    }

    override fun onCreate(db : SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INT PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db : SQLiteDatabase?, oldVersion : Int, newVersion : Int) {
        val refreshTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(refreshTableQuery)
        onCreate(db)
    }

    fun createNote(note: Notes) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.noteName)
            put(COLUMN_DESCRIPTION, note.noteDescription)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNotes(): List<Notes> {
        val notesList = mutableListOf<Notes>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val note = Notes(id, title, description)
            notesList.add(note)
        }

        cursor.close()
        db.close()
        return notesList
    }

    fun getNoteID(noteID: Int): Notes{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return Notes(id, title, description)
    }

    fun deleteNote(noteID : Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}