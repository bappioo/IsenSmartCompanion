package fr.isen.bastien.isensmartcompanion.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.isen.bastien.isensmartcompanion.Models.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM msg")
    suspend fun getAll(): List<Message>

    @Insert
    suspend fun insert(vararg message: Message)

    @Delete
    suspend fun delete(vararg message: Message)

    @Query("DELETE FROM msg")
    suspend fun deleteAll()
}