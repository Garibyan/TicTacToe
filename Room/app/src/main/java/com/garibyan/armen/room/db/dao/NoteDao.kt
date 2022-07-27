package com.garibyan.armen.room.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.garibyan.armen.room.model.NoteModel


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteModel: NoteModel)

    @Delete
    suspend fun delete(noteModel: NoteModel)

    @Query("SELECT * from note_table")
    fun getAllNotes(): LiveData<List<NoteModel>>

}