package fr.isen.bastien.isensmartcompanion.database

import android.app.Notification
import androidx.room.Database
import androidx.room.RoomDatabase
import fr.isen.bastien.isensmartcompanion.Dao.MessageDao

@Database(entities = [Notification.MessagingStyle.Message::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}