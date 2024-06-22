package com.example.persistenceworkshopcodepreview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Die PomodoroSessionDao-Schnittstelle definiert Datenzugriffsmethoden für die PomodoroSession-Entität.
// @Dao: Annotation, die diese Schnittstelle als Data Access Object (DAO) kennzeichnet.
@Dao
interface PomodoroSessionDao {

    // Methode zum Einfügen einer neuen Pomodoro-Sitzung in die Datenbank.
    // @Insert: Annotation, die diese Methode als Einfügemethode kennzeichnet.
    // suspend: Kennzeichnet die Methode als suspendierend, sodass sie in einer Coroutine aufgerufen werden kann.
    @Insert
    suspend fun insert(session: PomodoroSession)

    // Methode zum Abrufen aller Pomodoro-Sitzungen aus der Datenbank.
    // @Query: Annotation, die eine SQL-Abfrage für die Methode definiert.
    // Die Abfrage gibt alle Sitzungen zurück, sortiert nach absteigender ID.
    // Flow<List<PomodoroSession>>: Gibt einen Flow zurück, der eine Liste von Pomodoro-Sitzungen enthält.
    // Flow ermöglicht es, Daten asynchron und reaktiv zu empfangen, ideal für die Arbeit mit LiveData.
    @Query("SELECT * FROM pomodoro_sessions ORDER BY id DESC")
    fun getAllSessions(): Flow<List<PomodoroSession>>
}
