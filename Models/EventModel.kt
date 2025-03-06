package fr.isen.bastien.isensmartcompanion.Models

import java.util.UUID

data class EventModel(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,

) {
    val name: String = "BP"

    companion object {
        fun fakeEvents(): List<EventModel> {
            return listOf(
                EventModel(
                    id = UUID.randomUUID().toString(),
                    title = "Conférence IA et Futur",
                    description = "Une conférence sur l'avenir de l'intelligence artificielle et son impact sur notre société.",
                    date = "10 Mars 2025",
                    location = "Auditorium ISEN"
                ),
                EventModel(
                    id = UUID.randomUUID().toString(),
                    title = "Hackathon ISEN 2025",
                    description = "Un challenge de 48h pour innover et créer des solutions technologiques.",
                    date = "15 Avril 2025",
                    location = "Salle Innovation"
                ),
                EventModel(
                    id = UUID.randomUUID().toString(),
                    title = "Afterwork Étudiant",
                    description = "Un moment de détente entre étudiants avec networking et jeux.",
                    date = "20 Mai 2025",
                    location = "Cafétéria ISEN"
                )
            )
        }
    }
}