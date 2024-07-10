package com.example.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteDetailViewModel(private val noteDao: NoteDao, private val userId: String) : ViewModel() {

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val note = Note(userId = userId, title = title, content = content)
            noteDao.insertNote(note)
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            val note = Note(id = id, userId = userId, title = title, content = content)
            noteDao.updateNote(note)
        }
    }

    fun getNote(id: Int, onNoteFetched: (Note) -> Unit) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(id) // Assuming you add this method to NoteDao
            onNoteFetched(note)
        }
    }

    fun deleteNote(it: Int) {
        viewModelScope.launch {
            val note = Note(id = it, userId = userId, title = "", content = "") // Only ID is needed for deletion
            noteDao.deleteNote(note)
        }

    }
}