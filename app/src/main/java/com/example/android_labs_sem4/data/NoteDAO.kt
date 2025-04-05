package com.example.android_labs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg notes: Note): List<Long>

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAll(): List<Note>
}