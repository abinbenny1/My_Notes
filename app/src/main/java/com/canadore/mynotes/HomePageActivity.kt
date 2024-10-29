package com.canadore.mynotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class HomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val btnAddNote: Button = findViewById(R.id.btn_notes)
        btnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
        // Setup Notes Button
        val btnNotes: Button = findViewById(R.id.btn_notes)
        btnNotes.setOnClickListener {
            val intent = Intent(this, NotesListActivity::class.java) // Navigate to NotesListActivity
            startActivity(intent)
        }

        // Setup Dark/Light Button
        val btnDarkLight: Button = findViewById(R.id.btn_dark_light)
        btnDarkLight.setOnClickListener {
            toggleTheme()
        }
    }

    private fun toggleTheme() {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Set to Light Mode
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // Set to Dark Mode
        }
    }
}
