package fr.isen.bastien.isensmartcompanion.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "interactions") // Nom de la table dans la base de données
data class Entity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Clé primaire auto-générée
    val question: String, // Colonne pour stocker la question
    val answer: String,   // Colonne pour stocker la réponse de l'IA
    val date: Date        // Colonne pour stocker la date de l'interaction
)
