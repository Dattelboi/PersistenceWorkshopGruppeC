package com.example.persistenceworkshopcodepreview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PomodoroViewModel(application: Application) : AndroidViewModel(application) {

    private val pomodoroSessionDao: PomodoroSessionDao
    val allSessions: LiveData<List<PomodoroSession>>

    init {
        val db = AppDatabase.getDatabase(application)
        pomodoroSessionDao = db.pomodoroSessionDao()
        allSessions = pomodoroSessionDao.getAllSessions().asLiveData()
    }

    fun insert(session: PomodoroSession) {
        viewModelScope.launch {
            pomodoroSessionDao.insert(session)
        }
    }
}
