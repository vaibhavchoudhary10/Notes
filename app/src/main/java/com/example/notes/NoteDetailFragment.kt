package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class NoteDetailFragment : Fragment() {
        private lateinit var viewModel: NoteDetailViewModel // Assuming you have a ViewModel
        private lateinit var titleEditText: EditText
        private lateinit var contentEditText: EditText
        private lateinit var saveButton: Button
        private lateinit var deleteButton: Button
        private var noteId: Int? = null // To store the ID of the note being edited (null for new notes)

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_note_detail, container, false)

            titleEditText = view.findViewById(R.id.noteTitleEditText)
            contentEditText = view.findViewById(R.id.noteContentEditText)
            saveButton = view.findViewById(R.id.saveNoteButton)
            deleteButton = view.findViewById(R.id.deleteNoteButton)

            // Retrieve note ID from arguments if editing an existing note
            arguments?.let {
                noteId = it.getInt("noteId")
            }

            // If editing, fetch the note and populate EditTexts
            if (noteId != null) {
                viewModel.getNote(noteId!!) { note->
                    titleEditText.setText(note.title)
                    contentEditText.setText(note.content)
                    deleteButton.visibility = View.VISIBLE // Show delete button for existing notes
                }
            }

            saveButton.setOnClickListener {
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                if (noteId == null) {
                    viewModel.addNote(title, content)
                } else {
                    viewModel.updateNote(noteId!!, title, content)
                }
                // ... navigate back to Note List screen
                replaceFragment(NoteListFragment())

            }

            deleteButton.setOnClickListener {
                noteId?.let {
                    viewModel.deleteNote(it)
                    // ... navigate back to Note List screen
                    replaceFragment(NoteListFragment())
                }
            }

            return view
        }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Use your fragment container ID
            .commit()
    }
}