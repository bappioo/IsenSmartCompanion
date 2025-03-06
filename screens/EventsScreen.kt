package fr.isen.bastien.isensmartcompanion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.bastien.isensmartcompanion.Models.EventModel

@Composable
fun EventsScreen(navController: NavController) {
    val events = EventModel.fakeEvents()

    Scaffold(
        topBar = {
            Text(
                text = "Événements",
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(events) { event ->
                EventItem(event)
            }
        }
    }
}
