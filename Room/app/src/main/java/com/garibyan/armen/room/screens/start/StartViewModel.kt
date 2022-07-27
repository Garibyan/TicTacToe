package com.garibyan.armen.room.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.garibyan.armen.room.REPOSITORY
import com.garibyan.armen.room.db.NoteDataBase
import com.garibyan.armen.room.db.repository.NoteRealization
import com.garibyan.armen.room.model.NoteModel

class StartViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    fun initDataBase(){
        val daoNote = NoteDataBase.getInstance(context).getNoteDao()
        REPOSITORY = NoteRealization(daoNote)
    }

    fun getAllNotes(): LiveData<List<NoteModel>>{
        return  REPOSITORY.allNotes
    }

}