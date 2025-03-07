package fr.isen.bastien.isensmartcompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
    @Insert
    suspend fun insert(interaction: Interaction) // Insère une interaction

    @Query("SELECT * FROM interactions ORDER BY date DESC")
    fun getAll(): Flow<List<Interaction>> // Récupère toutes les interactions (observable)

    @Query("DELETE FROM interactions WHERE id = :id")
    suspend fun deleteById(id: Int) // Supprime une interaction par son ID

    @Query("DELETE FROM interactions")
    suspend fun deleteAll() // Supprime toutes les interactions
}