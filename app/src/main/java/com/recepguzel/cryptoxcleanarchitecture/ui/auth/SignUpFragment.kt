package com.recepguzel.cryptoxcleanarchitecture.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.recepguzel.cryptoxcleanarchitecture.R
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isPasswordVisible = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginDirection()
        signUpOperation()
        googleAuth()
        passwordVisible()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun passwordVisible() {
        binding.editTextPassword1.setOnTouchListener { _, event ->
            val drawableEnd = 2 // drawableEnd için indeks
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.editTextPassword1.right - binding.editTextPassword1.compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility(binding.editTextPassword1)
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.editTextPassword2.setOnTouchListener { _, event ->
            val drawableEnd = 2 // drawableEnd için indeks
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.editTextPassword2.right - binding.editTextPassword2.compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility(binding.editTextPassword2)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        val typeface = editText.typeface
        val textSize = editText.textSize

        if (isPasswordVisible) {
            // Şifreyi gizle
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.password_visibility,
                0
            )
        } else {
            // Şifreyi göster
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.show_password,
                0
            )
        }

        // Yazı tipini ve boyutunu tekrar uygula
        editText.typeface = typeface
        editText.textSize = textSize / resources.displayMetrics.scaledDensity

        // İmleci sona taşı
        editText.setSelection(editText.text?.length ?: 0)
        isPasswordVisible = !isPasswordVisible
    }


    private fun loginDirection() {
        binding.loginTextview.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }
    }


    private fun googleAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.buttonGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            }

        }

    @SuppressLint("SuspiciousIndentation")
    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credentinal = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credentinal).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_signUpFragment_to_splashFragment)
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun signUpOperation() {
        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonSignUp.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword1.text.toString()
            val confirmPassword = binding.editTextPassword2.text.toString()
            //    val nameAction=SignUpFragmentDirections.actionSignUpFragmentToProfileFragment(name)
            // findNavController().navigate(nameAction)
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {


                                binding.editTextEmail.text?.clear()
                                binding.editTextPassword1.text?.clear()
                                binding.editTextPassword2.text?.clear()
                                binding.editTextName.text?.clear()


                                val action =
                                    SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Empty Fields Are not Allowed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}