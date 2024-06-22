package com.example.persistenceworkshopcodepreview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Annotation, um eine Room-Datenbank mit zwei Entitäten zu definieren: PomodoroSession und TimerSetting.
// Die Version der Datenbank ist auf 2 gesetzt und exportSchema ist auf false gesetzt, um das Schema nicht in das Verzeichnis zu exportieren.
@Database(entities = [PomodoroSession::class, TimerSetting::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Abstrakte Methode, um das DAO für PomodoroSession zu erhalten.
    abstract fun pomodoroSessionDao(): PomodoroSessionDao

    // Abstrakte Methode, um das DAO für TimerSetting zu erhalten.
    abstract fun timerSettingDao(): TimerSettingDao

    companion object {
        // Das Schlüsselwort Volatile stellt sicher, dass Änderungen an der INSTANCE-Variable für alle Threads sichtbar sind.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // ExecutorService, um Datenbank-Schreibvorgänge in einem Hintergrund-Thread zu behandeln.
        private val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        // Funktion, um die Datenbankinstanz zu erhalten. Wenn sie noch nicht existiert, wird sie erstellt.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Erstellen der Datenbank mit einem Migrations-Objekt, um von Version 1 auf Version 2 zu migrieren.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pomodoro_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }

        // Migration-Objekt, um die Datenbank von Version 1 auf Version 2 zu migrieren.
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // SQL-Befehl zum Erstellen der neuen Tabelle "timer_settings".
                database.execSQL("CREATE TABLE IF NOT EXISTS `timer_settings` (`id` INTEGER PRIMARY KEY NOT NULL, `defaultTimerTime` INTEGER NOT NULL)")
            }
        }
    }
}
