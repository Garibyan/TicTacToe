package com.garibyan.armen.TBC_Task_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garibyan.armen.TBC_Task_5.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    lateinit var binding: FragmentLogInBinding
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
//            MAIN.navController.navigate(R.id.action_logInFragment_to_mainFragment)
            findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
        }
        binding.btnLogIn.setOnClickListener {
            login()

        }
    }

    private fun login() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(MAIN, R.string.blank_fields, Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MAIN) {
            if (it.isSuccessful) {
                MAIN.navController.navigate(R.id.action_logInFragment_to_mainFragment)
                Toast.makeText(MAIN, R.string.successful_login, Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(MAIN, R.string.login_failed, Toast.LENGTH_SHORT).show()
        }
    }
}