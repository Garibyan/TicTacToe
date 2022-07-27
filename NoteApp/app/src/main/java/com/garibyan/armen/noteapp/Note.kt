package com.garibyan.armen.noteapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteTable")
class Note(
    @ColumnInfo(name = "title")
    val noteTitle: String,
    @ColumnInfo(name = "decsription")
    val noteDescription: String,
    @ColumnInfo(name = "timestamp")
    val timeStamp: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}