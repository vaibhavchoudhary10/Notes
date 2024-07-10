package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [Note::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        var notesDatabase: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(
                    context
                    , AppDatabase::class.java
                    , "notes.db"
                ).build()
            }
            return notesDatabase!!
        }
    }

    abstract fun noteDao(): NoteDao
}