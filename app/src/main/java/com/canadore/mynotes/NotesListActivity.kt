package com.canadore.mynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class NotesListActivity : AppCompatActivity() {

    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesList: MutableList<Note>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        // Initialize RecyclerView
        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize notes list and adapter
        notesList = mutableListOf()
        notesAdapter = NotesAdapter(notesList, this::onEditClick, this::onDeleteClick)
        notesRecyclerView.adapter = notesAdapter

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("Notes")

        // Fetch notes from Firebase
        fetchNotes()
    }

    private fun fetchNotes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()
                for (noteSnapshot in snapshot.children) {
                    val note = noteSnapshot.getValue(Note::class.java)
                    if (note != null) {
                        notesList.add(note)
                    }
                }
                notesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@NotesListActivity, "Failed to load notes", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onEditClick(note: Note) {
        // Show dialog to update note
        showEditDialog(note)
    }

    private fun showEditDialog(note: Note) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null)
        val editHeading = dialogView.findViewById<EditText>(R.id.editNoteHeading)
        val editDescription = dialogView.findViewById<EditText>(R.id.editNoteDescription)

        // Set existing data
        editHeading.setText(note.heading)
        editDescription.setText(note.description)

        // Create AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newHeading = editHeading.text.toString()
                val newDescription = editDescription.text.toString()

                // Update note in Firebase
                val updatedNote = note.copy(heading = newHeading, description = newDescription)
                database.child(note.id).setValue(updatedNote).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun onDeleteClick(note: Note) {
        database.child(note.id).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                notesList.remove(note)
                notesAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
