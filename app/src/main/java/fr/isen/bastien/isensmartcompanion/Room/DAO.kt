package fr.isen.bastien.isensmartcompanion.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InteractionDao {
    @Insert
    suspend fun insert(interaction: Entity) // Insère une interaction

    @Query("SELECT * FROM interactions ORDER BY date DESC")
    fun getAll(): List<Entity> // Récupère toutes les interactions

    @Query("DELETE FROM interactions")
    suspend fun deleteAll() // Supprime toutes les interactions
}