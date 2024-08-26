package com.recepguzel.cryptoxcleanarchitecture.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetPassword()
        backButton()
    }

    private fun backButton() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()

        }
    }

    private fun resetPassword() {
        binding.buttonReset.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            if (email != null) {
                if (email.isNotEmpty()) {
                    sendPasswordResetEmail(email)
                } else {
                    Toast.makeText(context, "Please enter your email address!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.editTextEmail.text?.clear()
                    Toast.makeText(context, "Password reset request sent .", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}