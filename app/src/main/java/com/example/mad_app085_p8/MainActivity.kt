package com.example.mad_app085_p8

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.mad_app085_p8.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myTimePicker: TimePickerDialog
        val mycurrentTime = Calendar.getInstance()
        val hour = mycurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mycurrentTime.get(Calendar.MINUTE)

        myTimePicker = TimePickerDialog(this,
            { _, hourOfDay, minute -> binding.btnAddalarm.text = String.format("%d : %d", hourOfDay, minute) }, hour, minute, false)

        binding.btnAddalarm.setOnClickListener {
            myTimePicker.show()
        }
    }
}