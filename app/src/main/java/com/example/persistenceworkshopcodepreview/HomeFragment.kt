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

// HomeFragment ist ein Fragment, das die Benutzeroberfläche für den Pomodoro-Timer enthält.
class HomeFragment : Fragment() {

    // Deklaration der UI-Elemente und ViewModels
    private lateinit var startTimerButton: Button
    private lateinit var stopTimerButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var viewModel: PomodoroViewModel
    private lateinit var timerSettingViewModel: TimerSettingViewModel
    private var countDownTimer: CountDownTimer? = null
    private var defaultTimerTime: Int = 25 // Standardwert für den Timer (25 Minuten)

    // Diese Methode wird aufgerufen, um die Ansicht des Fragments zu erstellen.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Das Layout für dieses Fragment aufblähen.
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialisierung der ViewModels
        viewModel = ViewModelProvider(this).get(PomodoroViewModel::class.java)
        timerSettingViewModel = ViewModelProvider(this, TimerSettingViewModelFactory(requireActivity().application)).get(TimerSettingViewModel::class.java)

        // Initialisierung der UI-Elemente
        startTimerButton = rootView.findViewById(R.id.start_timer_button)
        stopTimerButton = rootView.findViewById(R.id.stop_timer_button)
        timerTextView = rootView.findViewById(R.id.timer_text_view)

        // Laden der Standard-Timereinstellung aus dem ViewModel
        timerSettingViewModel.getTimerSetting { setting ->
            defaultTimerTime = setting?.defaultTimerTime?.toInt() ?: 25
        }

        // Klick-Listener für den Start-Button
        startTimerButton.setOnClickListener {
            startTimer((defaultTimerTime * 60 * 1000).toLong()) // Starten des Timers mit der Standardzeit
            stopTimerButton.visibility = View.VISIBLE
            startTimerButton.visibility = View.GONE
        }

        // Klick-Listener für den Stopp-Button
        stopTimerButton.setOnClickListener {
            stopTimer()
            stopTimerButton.visibility = View.GONE
            startTimerButton.visibility = View.VISIBLE
        }

        return rootView
    }

    // Methode zum Starten des Timers
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
                // Hier kann Logik hinzugefügt werden, um zu handhaben, was passiert, wenn der Timer endet.
            }
        }.start()
    }

    // Methode zum Stoppen des Timers
    private fun stopTimer() {
        countDownTimer?.cancel()
        timerTextView.text = String.format("%02d:%02d", defaultTimerTime, 0) // Zurücksetzen auf den Standard-Timerwert
    }

    // Methode, die aufgerufen wird, wenn die Ansicht des Fragments zerstört wird.
    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}
