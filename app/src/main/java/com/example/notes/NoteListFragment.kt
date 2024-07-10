package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class NoteListFragment : Fragment() {

        private lateinit var viewModel: NoteListViewModel
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: NoteAdapter
    var arrNotes = ArrayList<Note>()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_note_list, container, false)
            recyclerView = view.findViewById(R.id.noteRecyclerView)
            adapter = NoteAdapter(emptyList()) // Initialize with an empty list
            recyclerView.adapter = adapter

            // Set click listener for the FAB to navigate to Note Detail screen
            view.findViewById<FloatingActionButton>(R.id.addNoteFab).setOnClickListener {

                // ... navigate to Note Detail fragment for adding a new note
                replaceFragment(NoteDetailFragment())


            }

            return view
        }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)



            // Observe notes from ViewModel and update RecyclerView

            viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
            lifecycleScope.launch {

                viewModel.notes.collect { notes ->
                    adapter.updateNotes(notes) // Update adapter with new notes
                    recyclerView.scrollToPosition(0)
                }
            }
        }

}