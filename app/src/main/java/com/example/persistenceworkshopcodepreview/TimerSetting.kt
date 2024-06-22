package com.example.persistenceworkshopcodepreview

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Diese Klasse definiert die Datenstruktur für die Timer-Einstellungen.
 * Sie verwendet die @Entity-Annotation, um anzugeben, dass es sich um eine Tabelle in der Room-Datenbank handelt.
 * Der Tabellenname ist "timer_settings".
 */
@Entity(tableName = "timer_settings")
data class TimerSetting(
    // Der Primärschlüssel der Tabelle. Jede Timer-Einstellung hat eine eindeutige ID.
    @PrimaryKey val id: Int,

    // Der Standardwert für die Timer-Zeit in Millisekunden.
    val defaultTimerTime: Long
)
