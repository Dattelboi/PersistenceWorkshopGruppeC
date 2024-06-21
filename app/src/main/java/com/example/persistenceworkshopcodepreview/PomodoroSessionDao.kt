package com.example.persistenceworkshopcodepreview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroSessionDao {
    @Insert
    suspend fun insert(session: PomodoroSession)

    @Query("SELECT * FROM pomodoro_sessions ORDER BY id DESC")
    fun getAllSessions(): Flow<List<PomodoroSession>>
}
