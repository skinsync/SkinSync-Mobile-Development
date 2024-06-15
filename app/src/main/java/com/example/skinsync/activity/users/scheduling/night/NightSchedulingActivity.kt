package com.example.skinsync.activity.users.scheduling.night

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.users.scheduling.NotificationReceiver
import com.example.skinsync.activity.users.scheduling.morning.MorningSchedulingActivity
import java.util.Calendar
import java.util.Locale

class NightSchedulingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_night_scheduling)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewDate: TextView = findViewById(R.id.textViewDate)

        // Membuat format tanggal
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(java.util.Date())

        // Menampilkan tanggal di TextView
        textViewDate.text = currentDate

        // Set up button back
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        // Set up night routine alarm
        setNightRoutineAlarm()

        // Set up click listener for seeDetails
        val seeDetails: TextView = findViewById(R.id.seeDetails)
        seeDetails.setOnClickListener {
            val intent = Intent(this, MorningSchedulingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setNightRoutineAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set alarm for night routine (adjust the time as needed)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 20) // Set the hour for night routine notification
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, // Repeat daily for night routine notification
            pendingIntent
        )
    }
}