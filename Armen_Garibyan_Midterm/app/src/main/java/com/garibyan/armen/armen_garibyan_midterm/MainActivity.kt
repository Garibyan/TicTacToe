package com.garibyan.armen.armen_garibyan_midterm

import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garibyan.armen.armen_garibyan_midterm.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.btnSend.setOnClickListener {
            if (binding.edtFirstName.text.length >= 3 &&
                binding.edtLastName.text.length >= 5 &&
                binding.edtReview.text.length > 10 &&
                binding.edtEmail.text.isValidEmail()
            ) {
                Toast.makeText(this, R.string.validation_passed, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, R.string.validation_didnot_passed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}