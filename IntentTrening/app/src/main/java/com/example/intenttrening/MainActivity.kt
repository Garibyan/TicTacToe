package com.example.intenttrening

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.intenttrening.databinding.ActivityMainBinding
import kotlin.random.Random

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btnIncrement.setOnClickListener { increment() }
        binding.btnRandomColor.setOnClickListener { randomColor() }
        binding.btnSwitchVisibility.setOnClickListener { switchVisibility() }
    }

    private fun increment() {
        var counter = binding.tvNumber.text.toString().toInt()
        counter++
        binding.tvNumber.setText(counter.toString())
    }

    private fun randomColor() {
        var randomColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        binding.tvNumber.setTextColor(randomColor)
    }

    private fun switchVisibility() = with(binding.tvNumber) {
        visibility = if (visibility == VISIBLE)
            INVISIBLE
        else
            VISIBLE
    }
}

