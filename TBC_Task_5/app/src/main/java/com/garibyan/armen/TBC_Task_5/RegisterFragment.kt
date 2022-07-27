package com.garibyan.armen.TBC_Task_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garibyan.armen.TBC_Task_5.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        binding.btnNext.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(MAIN, R.string.blank_fields, Toast.LENGTH_SHORT).show()
            return
        }
        when (false) {
            isEmailValid() -> {
                Toast.makeText(MAIN, R.string.invalid_email, Toast.LENGTH_SHORT).show()
                binding.edtEmail.error
            }
            isValidPassword() -> {
                Toast.makeText(MAIN, R.string.invalid_password, Toast.LENGTH_SHORT).show()
                binding.edtPassword.error
            }
            else -> {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MAIN) {
                    if (it.isSuccessful) {
                        Toast.makeText(MAIN, R.string.successful_signup, Toast.LENGTH_SHORT).show()
                        MAIN.navController.navigate(R.id.action_registerFragment_to_registerStepTwoFragment)
                    } else {
                        Toast.makeText(MAIN, R.string.signup_failed, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun isEmailValid(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text).matches()

    private fun isValidPassword(): Boolean = binding.edtPassword.text.toString().length >= 6
}