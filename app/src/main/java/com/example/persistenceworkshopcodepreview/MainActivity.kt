package com.example.persistenceworkshopcodepreview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.persistenceworkshopcodepreview.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// MainActivity ist die Hauptaktivität der App, die die Benutzeroberfläche und die Navigation verwaltet.
class MainActivity : AppCompatActivity() {

    // Initialisierung des ViewModels für die Pomodoro-Sitzungen
    private val pomodoroViewModel: PomodoroViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    // Diese Methode wird aufgerufen, wenn die Aktivität erstellt wird.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Einrichtung der Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Einrichten der unteren Navigationsleiste
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Klick-Listener für den "Sitzung hinzufügen"-Button
        binding.addSessionButton.setOnClickListener {
            val startTime = binding.startTime.text.toString()
            val endTime = binding.endTime.text.toString()
            val session = PomodoroSession(startTime = startTime, endTime = endTime, isCompleted = true)
            pomodoroViewModel.insert(session)
        }

        // Beobachtung der Pomodoro-Sitzungen
        pomodoroViewModel.allSessions.observe(this, { sessions ->
            val sessionsText = sessions.joinToString(separator = "\n") { session ->
                "ID: ${session.id}, Start: ${session.startTime}, End: ${session.endTime}"
            }
            binding.sessionsList.text = sessionsText
        })
    }
}
