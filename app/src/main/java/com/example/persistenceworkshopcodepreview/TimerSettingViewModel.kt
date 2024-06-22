package com.example.persistenceworkshopcodepreview

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerSettingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimerSettingRepository
    val defaultTimerTime: MutableLiveData<Int> = MutableLiveData()

    init {
        val timerSettingDao = AppDatabase.getDatabase(application).timerSettingDao()
        repository = TimerSettingRepository(timerSettingDao)
        loadTimerSetting()
    }

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

    fun saveTimerSetting(newTimerTime: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val timerSetting = TimerSetting(id = 1, defaultTimerTime = newTimerTime.toLong())
            repository.insertTimerSetting(timerSetting)
        }
    }
}

class TimerSettingViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application)
