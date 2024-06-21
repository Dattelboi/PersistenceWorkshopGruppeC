package com.example.persistenceworkshopcodepreview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.persistenceworkshopcodepreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val pomodoroViewModel: PomodoroViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addSessionButton.setOnClickListener {
            val startTime = binding.startTime.text.toString()
            val endTime = binding.endTime.text.toString()
            val session = PomodoroSession(startTime = startTime, endTime = endTime, isCompleted = true)
            pomodoroViewModel.insert(session)
        }

        pomodoroViewModel.allSessions.observe(this, Observer { sessions ->
            val sessionsText = sessions.joinToString(separator = "\n") { session ->
                "ID: ${session.id}, Start: ${session.startTime}, End: ${session.endTime}"
            }
            binding.sessionsList.text = sessionsText
        })
    }
}

