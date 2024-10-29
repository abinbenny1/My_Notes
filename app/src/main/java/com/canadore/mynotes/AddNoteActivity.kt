package com.canadore.mynotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {

    private lateinit var noteHeading: EditText
    private lateinit var noteDescription: EditText
    private lateinit var saveButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        noteHeading = findViewById(R.id.noteHeading)
        noteDescription = findViewById(R.id.noteDescription)
        saveButton = findViewById(R.id.saveButton)

        database = FirebaseDatabase.getInstance().getReference("notes")

        saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val heading = noteHeading.text.toString()
        val description = noteDescription.text.toString()

        if (heading.isNotEmpty() && description.isNotEmpty()) {
            val noteId = database.push().key ?: ""
            val note = Note(noteId, heading, description)

            database.child(noteId).setValue(note).addOnCompleteListener {
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
