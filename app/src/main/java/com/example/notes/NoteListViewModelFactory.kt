package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteListViewModelFactory(
    private val noteDao: NoteDao,
    private val userId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteListViewModel(noteDao, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}