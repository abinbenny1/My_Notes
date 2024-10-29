package com.canadore.mynotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        // Enable edge-to-edge display (remove this line if it causes issues)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Floating Action Button
        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            // Start AddNote activity when the FAB is clicked
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }

        // Setup Home Button
        val homeButton: Button = findViewById(R.id.btn_note_page_1)
        homeButton.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}
