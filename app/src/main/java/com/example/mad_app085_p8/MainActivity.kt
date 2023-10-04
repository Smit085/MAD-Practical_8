package com.example.mad_app085_p8

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.mad_app085_p8.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.constSetalarm.visibility = View.GONE

        binding.btnAddalarm.setOnClickListener {
            val myTimePicker: TimePickerDialog
            val mycurrentTime = Calendar.getInstance()
            val hour = mycurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mycurrentTime.get(Calendar.MINUTE)

            myTimePicker = TimePickerDialog(this,
                { tp, hrs, min -> sendDialogDataToActivity(hrs, min) }, hour, minute, false)
            myTimePicker.show()
        }

    }

    private fun sendDialogDataToActivity(hrs: Int, min: Int) {
        val alarmCalendar= Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hrs, min, 0)
        binding.txtTime.text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(alarmCalendar.time)
        binding.txtTimeconv.text = SimpleDateFormat("a", Locale.getDefault()).format(alarmCalendar.time)
        binding.swtAlarmstate.isChecked = true
        binding.constSetalarm.visibility = View.VISIBLE
        setAlarm(alarmCalendar.timeInMillis,"Start")
    }

    private fun setAlarm(timeInMillis: Long, action: String) {
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        intent.putExtra("com.example.mad_app085_p8.AlarmService",action)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext,234324243, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == "Start"){
            alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }else if(action == "Stop") {
            alarmManager.cancel(pendingIntent)
            sendBroadcast(intent)
        }
    }
}