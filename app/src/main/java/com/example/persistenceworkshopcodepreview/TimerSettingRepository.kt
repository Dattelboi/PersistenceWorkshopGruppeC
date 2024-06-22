package com.example.persistenceworkshopcodepreview

/**
 * Diese Klasse dient als Repository für die Verwaltung von Timer-Einstellungen.
 * Das Repository fungiert als Vermittler zwischen der Datenquelle (DAO) und der restlichen Anwendung.
 *
 * @property timerSettingDao Das Data Access Object (DAO) für Timer-Einstellungen.
 */
class TimerSettingRepository(private val timerSettingDao: TimerSettingDao) {

    /**
     * Ruft die aktuelle Timer-Einstellung ab.
     * Diese Methode wird synchron aufgerufen und gibt das Ergebnis sofort zurück.
     *
     * @return Die Timer-Einstellung, falls vorhanden, sonst null.
     */
    fun getTimerSetting(): TimerSetting? {
        return timerSettingDao.getTimerSetting()
    }

    /**
     * Fügt eine neue Timer-Einstellung in die Datenbank ein oder aktualisiert sie, falls sie bereits existiert.
     * Diese Methode wird asynchron aufgerufen und kann innerhalb einer Coroutine verwendet werden.
     *
     * @param timerSetting Die Timer-Einstellung, die eingefügt oder aktualisiert werden soll.
     */
    suspend fun insertTimerSetting(timerSetting: TimerSetting) {
        timerSettingDao.insertTimerSetting(timerSetting)
    }
}
