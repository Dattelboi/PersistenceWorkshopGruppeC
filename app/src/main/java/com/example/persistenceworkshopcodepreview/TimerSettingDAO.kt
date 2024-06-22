package com.example.persistenceworkshopcodepreview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Diese Schnittstelle definiert die Datenzugriffsobjekte (DAO) für die Timer-Einstellungen.
 * Sie enthält Methoden zum Abrufen und Einfügen von Timer-Einstellungen in die Room-Datenbank.
 */
@Dao
interface TimerSettingDao {

    /**
     * Diese Methode ruft die Timer-Einstellung ab.
     * Da nur eine Timer-Einstellung erwartet wird, wird die Abfrage auf eine einzelne Zeile beschränkt.
     *
     * @return Die Timer-Einstellung, wenn vorhanden, sonst null.
     */
    @Query("SELECT * FROM timer_settings LIMIT 1")
    fun getTimerSetting(): TimerSetting?

    /**
     * Diese Methode fügt eine neue Timer-Einstellung in die Datenbank ein oder aktualisiert sie, wenn sie bereits existiert.
     *
     * @param timerSetting Die Timer-Einstellung, die eingefügt oder aktualisiert werden soll.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimerSetting(timerSetting: TimerSetting)
}
