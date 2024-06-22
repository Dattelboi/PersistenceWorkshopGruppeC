package com.example.persistenceworkshopcodepreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    // Deklaration des TimerSettingViewModel
    private lateinit var timerSettingViewModel: TimerSettingViewModel

    // Deklaration der UI-Elemente
    private lateinit var timerEditText: EditText
    private lateinit var saveButton: Button

    // Erstellung des View für das Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating des Layouts für das Fragment
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialisierung des ViewModels
        timerSettingViewModel = ViewModelProvider(this, TimerSettingViewModelFactory(requireActivity().application)).get(TimerSettingViewModel::class.java)

        // Initialisierung der UI-Elemente
        timerEditText = rootView.findViewById(R.id.edit_text_timer_time)
        saveButton = rootView.findViewById(R.id.button_save)

        // Abrufen und Anzeigen der aktuellen Timer-Einstellung
        timerSettingViewModel.getTimerSetting { setting ->
            timerEditText.setText(setting?.defaultTimerTime?.toString() ?: "25")
        }

        // Festlegen des Click-Listeners für den Speichern-Button
        saveButton.setOnClickListener {
            val newTime = timerEditText.text.toString().toIntOrNull() ?: 25
            timerSettingViewModel.updateTimerSetting(newTime)
            showConfirmation()
        }

        return rootView
    }

    // Methode zum Anzeigen einer Bestätigung
    private fun showConfirmation() {
        lifecycleScope.launch {
            val toast = Toast.makeText(requireContext(), "Timer-Einstellung gespeichert", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
