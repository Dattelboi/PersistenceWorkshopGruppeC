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
    private lateinit var editTextTimerTime: EditText
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        timerSettingViewModel = ViewModelProvider(this, TimerSettingViewModelFactory(requireActivity().application)).get(TimerSettingViewModel::class.java)

        editTextTimerTime = rootView.findViewById(R.id.edit_text_timer_time)
        buttonSave = rootView.findViewById(R.id.button_save)

        timerSettingViewModel.defaultTimerTime.observe(viewLifecycleOwner) { time ->
            editTextTimerTime.setText(time.toString())
        }

        buttonSave.setOnClickListener {
            val newTimerTime = editTextTimerTime.text.toString().toInt()
            timerSettingViewModel.saveTimerSetting(newTimerTime)
        }

        return rootView
    }
}
