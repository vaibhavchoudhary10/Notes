package com.example.notes

import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class NoteListViewModel(private val noteDao: NoteDao, private val userId: String) : ViewModel() {

    val notes: Flow<List<Note>> = noteDao.getNotesForUser(userId)
}