package com.example.persistenceworkshopcodepreview

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class HomeFragment : Fragment() {

    private lateinit var startTimerButton: Button
    private lateinit var stopTimerButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var viewModel: PomodoroViewModel
    private lateinit var timerSettingViewModel: TimerSettingViewModel
    private var countDownTimer: CountDownTimer? = null
    private var defaultTimerTime: Int = 25 // Default to 25 minutes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(this).get(PomodoroViewModel::class.java)
        timerSettingViewModel = ViewModelProvider(this, TimerSettingViewModelFactory(requireActivity().application)).get(TimerSettingViewModel::class.java)

        startTimerButton = rootView.findViewById(R.id.start_timer_button)
        stopTimerButton = rootView.findViewById(R.id.stop_timer_button)
        timerTextView = rootView.findViewById(R.id.timer_text_view)

        timerSettingViewModel.getTimerSetting { setting ->
            defaultTimerTime = setting?.defaultTimerTime?.toInt() ?: 25
        }

        startTimerButton.setOnClickListener {
            startTimer((defaultTimerTime * 60 * 1000).toLong()) // Default time
            stopTimerButton.visibility = View.VISIBLE
            startTimerButton.visibility = View.GONE
        }

        stopTimerButton.setOnClickListener {
            stopTimer()
            stopTimerButton.visibility = View.GONE
            startTimerButton.visibility = View.VISIBLE
        }

        return rootView
    }

    private fun startTimer(milliseconds: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                stopTimerButton.visibility = View.GONE
                startTimerButton.visibility = View.VISIBLE
                // Here you can add logic to handle what happens when the timer finishes
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        timerTextView.text = String.format("%02d:%02d", defaultTimerTime, 0) // Reset to default timer value
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}
