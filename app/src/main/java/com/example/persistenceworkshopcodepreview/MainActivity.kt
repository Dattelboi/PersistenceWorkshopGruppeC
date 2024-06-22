package com.example.persistenceworkshopcodepreview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.persistenceworkshopcodepreview.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val pomodoroViewModel: PomodoroViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Set up session button click listener
        binding.addSessionButton.setOnClickListener {
            val startTime = binding.startTime.text.toString()
            val endTime = binding.endTime.text.toString()
            val session = PomodoroSession(startTime = startTime, endTime = endTime, isCompleted = true)
            pomodoroViewModel.insert(session)
        }

        // Observe the sessions
        pomodoroViewModel.allSessions.observe(this, { sessions ->
            val sessionsText = sessions.joinToString(separator = "\n") { session ->
                "ID: ${session.id}, Start: ${session.startTime}, End: ${session.endTime}"
            }
            binding.sessionsList.text = sessionsText
        })
    }
}
