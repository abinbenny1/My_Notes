package com.canadore.mynotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNote : AppCompatActivity() {

    private lateinit var noteHeading: EditText
    private lateinit var noteDescription: EditText
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        noteHeading = findViewById(R.id.noteHeading)
        noteDescription = findViewById(R.id.noteDescription)
        val saveButton: Button = findViewById(R.id.saveButton)

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference.child("Notes")

        saveButton.setOnClickListener {
            val heading = noteHeading.text.toString().trim()
            val description = noteDescription.text.toString().trim()

            if (heading.isNotEmpty() && description.isNotEmpty()) {
                saveNoteToFirebase(heading, description)
            } else {
                Toast.makeText(this, "Please enter both heading and description", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveNoteToFirebase(heading: String, description: String) {
        // Generate a unique key for each note
        val noteId = database.push().key

        if (noteId != null) {
            val note = Note(noteId, heading, description)

            // Save the note under the unique key
            database.child(noteId).setValue(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save note: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


