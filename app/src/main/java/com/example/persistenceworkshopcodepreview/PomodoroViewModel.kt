package com.example.persistenceworkshopcodepreview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// PomodoroViewModel verwaltet die UI-bezogenen Daten für das Pomodoro-Feature der App.
// Die Klasse erweitert AndroidViewModel, das Zugriff auf das Anwendungskontext bietet.
class PomodoroViewModel(application: Application) : AndroidViewModel(application) {

    // Referenz zum DAO (Data Access Object) für PomodoroSession.
    private val pomodoroSessionDao: PomodoroSessionDao

    // LiveData, das eine Liste aller Pomodoro-Sitzungen enthält.
    val allSessions: LiveData<List<PomodoroSession>>

    // Initialisierungsblock, der beim Erstellen der ViewModel-Instanz ausgeführt wird.
    init {
        // Zugriff auf die Datenbankinstanz.
        val db = AppDatabase.getDatabase(application)

        // Initialisierung des DAOs.
        pomodoroSessionDao = db.pomodoroSessionDao()

        // Abrufen aller Sitzungen und Konvertierung zu LiveData.
        allSessions = pomodoroSessionDao.getAllSessions().asLiveData()
    }

    // Methode zum Einfügen einer neuen Pomodoro-Sitzung in die Datenbank.
    // Diese Methode wird in einer Coroutine ausgeführt, um die Haupt-UI-Schicht nicht zu blockieren.
    fun insert(session: PomodoroSession) {
        viewModelScope.launch {
            pomodoroSessionDao.insert(session)
        }
    }
}
