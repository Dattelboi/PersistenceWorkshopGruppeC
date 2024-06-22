package com.example.persistenceworkshopcodepreview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TimerSettingDao {
    @Query("SELECT * FROM timer_settings LIMIT 1")
    fun getTimerSetting(): TimerSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimerSetting(timerSetting: TimerSetting)
}
