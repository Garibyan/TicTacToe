package com.garibyan.armen.room.db.repository

import androidx.lifecycle.LiveData
import com.garibyan.armen.room.model.NoteModel

interface NoteRepository {

    val allNotes: LiveData<List<NoteModel>>

    suspend fun insertNote(noteModel: NoteModel, onSuccess:() -> Unit)
    suspend fun deletetNote(noteModel: NoteModel, onSuccess:() -> Unit)
}