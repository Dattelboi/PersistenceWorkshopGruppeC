package com.example.persistenceworkshopcodepreview

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel zur Verwaltung der Timer-Einstellungen.
 * Diese Klasse verbindet sich mit dem Repository, um Daten zu laden und zu speichern.
 */
class TimerSettingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimerSettingRepository
    val defaultTimerTime: MutableLiveData<Int> = MutableLiveData()

    init {
        val timerSettingDao = AppDatabase.getDatabase(application).timerSettingDao()
        repository = TimerSettingRepository(timerSettingDao)
        loadTimerSetting()
    }

    /**
     * Lädt die aktuelle Timer-Einstellung aus dem Repository.
     * Diese Methode führt die Datenbankoperationen im Hintergrund aus.
     */
    private fun loadTimerSetting() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val timerSetting = repository.getTimerSetting()
                withContext(Dispatchers.Main) {
                    defaultTimerTime.value = timerSetting?.defaultTimerTime?.toInt() ?: 25
                }
            }
        }
    }

    /**
     * Speichert die neue Timer-Einstellung im Repository.
     * Diese Methode führt die Datenbankoperationen im Hintergrund aus.
     *
     * @param newTimerTime Die neue Timer-Einstellung, die gespeichert werden soll.
     */
    fun saveTimerSetting(newTimerTime: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val timerSetting = TimerSetting(id = 1, defaultTimerTime = newTimerTime.toLong())
            repository.insertTimerSetting(timerSetting)
        }
    }

    /**
     * Ruft die aktuelle Timer-Einstellung ab und gibt sie an einen Callback weiter.
     * Diese Methode führt die Datenbankoperationen im Hintergrund aus.
     *
     * @param callback Der Callback, der mit der abgerufenen Timer-Einstellung aufgerufen wird.
     */
    fun getTimerSetting(callback: (TimerSetting?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val timerSetting = repository.getTimerSetting()
            withContext(Dispatchers.Main) {
                callback(timerSetting)
            }
        }
    }

    /**
     * Aktualisiert die Timer-Einstellung mit einem neuen Wert.
     *
     * @param newTimerTime Die neue Timer-Einstellung.
     */
    fun updateTimerSetting(newTimerTime: Int) {
        saveTimerSetting(newTimerTime)
    }
}

/**
 * Factory zur Erstellung einer Instanz von TimerSettingViewModel.
 *
 * @param application Die Anwendungsklasse.
 */
class TimerSettingViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerSettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerSettingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
