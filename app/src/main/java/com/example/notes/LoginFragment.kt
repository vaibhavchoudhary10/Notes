package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginFragment : Fragment() {

        private lateinit var signInClient: GoogleSignInClient
        private val RC_SIGN_IN = 9001 // Request code for sign-in

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_login, container, false)

            // Configure Google Sign-In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("258262463189-pqd9b3ar6ve1q2lufm4jlvelorctb2mg.apps.googleusercontent.com")
                .requestEmail()
                .build()
            signInClient = GoogleSignIn.getClient(requireActivity(), gso)

            // Set click listener for the Sign-In button
            view.findViewById<SignInButton>(R.id.signInButton).setOnClickListener {
                signIn()
            }

            return view
        }

        private fun signIn() {
            val signInIntent = signInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

        private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
            try {

                Toast.makeText(requireContext(), "Sign-in successful", Toast.LENGTH_SHORT).show()
                val account = completedTask.getResult(ApiException::class.java)


                // Signed in successfully, store user ID in SharedPreferences/database
                // ...
                val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                with (sharedPreferences.edit()) {
                    putString("user_id", account.id)
                    commit()
                }
                // Navigate to Note List screen
                replaceFragment(NoteListFragment())
            } catch (e: ApiException) {
                // Handle sign-in failure
                // ...
                Toast.makeText(requireContext(), "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}