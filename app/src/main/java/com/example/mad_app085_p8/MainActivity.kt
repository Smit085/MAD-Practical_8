package com.example.mad_app085_p8

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private var pendingIntent: PendingIntent? = null

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

        binding.swtAlarmstate.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                Log.i("State","NOt")
                setAlarm(-1,"Stop")
                pendingIntent = null
                Snackbar.make(binding.root, "Alarm Canceled",Snackbar.LENGTH_SHORT).show()
            }
            else{
                val alarmCalendar= Calendar.getInstance()
                val year: Int = alarmCalendar.get(Calendar.YEAR)
                val month: Int = alarmCalendar.get(Calendar.MONTH)
                val day: Int = alarmCalendar.get(Calendar.DATE)
                val hour = (binding.txtTime.text.split(":")[0]).toInt()
                val minute = (binding.txtTime.text.split(":")[1]).toInt()
                alarmCalendar.set(year, month, day, hour, minute, 0)
                setAlarm(alarmCalendar.timeInMillis,"Start")
            }
        }

        binding.constSetalarm.setOnClickListener{
            val myTimePicker: TimePickerDialog
            val hour = (binding.txtTime.text.split(":")[0]).toInt()
            val minute = (binding.txtTime.text.split(":")[1]).toInt()

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
        Snackbar.make(binding.root, "Alarm Set for ${SimpleDateFormat("hh:mm a", Locale.getDefault()).format(alarmCalendar.time)}",Snackbar.LENGTH_SHORT).show()
        setAlarm(alarmCalendar.timeInMillis,"Start")
    }

    private fun setAlarm(timeInMillis: Long, action: String) {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("BroadcastService", action)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // Cancel the existing pending intent if it exists
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent!!)
            pendingIntent = null
        }

        // If the switch is on, create a new pending intent
        if (action == "Start" && binding.swtAlarmstate.isChecked) {
            // Use a fixed request code
            val requestCode = 12345 // Replace with your desired request code
            pendingIntent = PendingIntent.getBroadcast(applicationContext, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent!!
            )
            Log.i("MBroadcastService", "Start")
        } else if (action == "Stop") {
            sendBroadcast(intent)
            Log.i("MBroadcastService", "STOP")
        }
    }


}