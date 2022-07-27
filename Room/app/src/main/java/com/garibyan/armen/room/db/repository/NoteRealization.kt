package com.garibyan.armen.room.db.repository

import androidx.lifecycle.LiveData
import com.garibyan.armen.room.db.dao.NoteDao
import com.garibyan.armen.room.model.NoteModel

class NoteRealization(private val noteDao: NoteDao): NoteRepository {

    override val allNotes: LiveData<List<NoteModel>>
        get() = noteDao.getAllNotes()

    override suspend fun insertNote(noteModel: NoteModel, onSuccess: () -> Unit) {
        noteDao.insert(noteModel)
        onSuccess
    }

    override suspend fun deletetNote(noteModel: NoteModel, onSuccess: () -> Unit) {
        noteDao.delete(noteModel)
        onSuccess
    }
}