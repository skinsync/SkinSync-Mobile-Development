package com.example.skinsync.activity.users.scheduling.morning

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.users.scheduling.NotificationReceiver
import com.example.skinsync.activity.users.scheduling.night.NightSchedulingActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MorningSchedulingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_scheduling)
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

        // Set up morning notification
        setMorningNotification()

        // Set up click listener for seeDetails
        val seeDetails: TextView = findViewById(R.id.seeDetails)
        seeDetails.setOnClickListener {
            val intent = Intent(this, NightSchedulingActivity::class.java)
            startActivity(intent) // Memulai NightSchedulingActivity
        }

        showDevelopmentAlert()
    }

    private fun setMorningNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // Set the alarm to start at 7:00 AM
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun showDevelopmentAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Fitur dalam Pengembangan")
        builder.setMessage("Mohon Maaf, Fitur ini masih dalam tahap pengembangan.")
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}