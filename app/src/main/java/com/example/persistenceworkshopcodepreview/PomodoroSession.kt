package com.example.persistenceworkshopcodepreview

import androidx.room.Entity
import androidx.room.PrimaryKey

// Die Klasse PomodoroSession repräsentiert eine Entität in der Room-Datenbank.
@Entity(tableName = "pomodoro_sessions")
data class PomodoroSession(
    // Die id ist der Primärschlüssel und wird automatisch generiert.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Die Startzeit der Pomodoro-Sitzung als String.
    val startTime: String,
    // Die Endzeit der Pomodoro-Sitzung als String.
    val endTime: String,
    // Ein Boolean-Wert, der anzeigt, ob die Sitzung abgeschlossen ist.
    val isCompleted: Boolean
)
