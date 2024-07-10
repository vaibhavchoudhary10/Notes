package com.example.notes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (isUserLoggedIn()) {
            showNoteListFragment()
        } else {
            showLoginFragment()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        // Implement your authentication check here.
        // For example, check if a user token is stored in SharedPreferences.
        val user = sharedPreferences.getString("user_id", null)
        return user != null
    }

    private fun showLoginFragment() {
        replaceFragment(LoginFragment())
    }

    private fun showNoteListFragment() {
        replaceFragment(NoteListFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}