package com.example.persistenceworkshopcodepreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SettingsFragment : Fragment() {

    private lateinit var timerSettingViewModel: TimerSettingViewModel
    private lateinit var timerEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        timerSettingViewModel = ViewModelProvider(this, TimerSettingViewModelFactory(requireActivity().application)).get(TimerSettingViewModel::class.java)
        timerEditText = rootView.findViewById(R.id.edit_text_timer_time)
        saveButton = rootView.findViewById(R.id.button_save)

        timerSettingViewModel.getTimerSetting { setting ->
            timerEditText.setText(setting?.defaultTimerTime?.toString() ?: "25")
        }

        saveButton.setOnClickListener {
            val newTime = timerEditText.text.toString().toIntOrNull() ?: 25
            timerSettingViewModel.updateTimerSetting(newTime)
        }

        return rootView
    }
}
