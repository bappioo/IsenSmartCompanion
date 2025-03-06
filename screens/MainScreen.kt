package fr.isen.bastien.isensmartcompanion.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.bastien.isensmartcompanion.Models.EventModel
import fr.isen.bastien.isensmartcompanion.R

@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    var userInput = remember { mutableStateOf("") }
    val events = EventModel.fakeEvents()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf("Home", "Events", "History")
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            if (screen == "Events") {
                                navController.navigate("events")  // Va vers l'écran des événements
                            } else {
                                navController.navigate(screen)
                            }
                        },
                        icon = {
                            when (screen) {
                                "Home" -> Image(painterResource(R.drawable.home), contentDescription = "Home", modifier = Modifier.size(32.dp))
                                "Events" -> Image(painterResource(R.drawable.events), contentDescription = "Events", modifier = Modifier.size(32.dp))
                                "History" -> Image(painterResource(R.drawable.history), contentDescription = "History", modifier = Modifier.size(32.dp))
                            }
                        },
                        label = { Text(screen) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(R.drawable.isen), context.getString(R.string.isen_logo))
            Text(context.getString(R.string.app_name))
            Text("", modifier = Modifier.fillMaxSize().weight(0.5F))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(events) { event ->
                    EventItem(event)  // Utilisation de EventItem pour afficher l'événement
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                TextField(
                    value = userInput.value,
                    onValueChange = { newValue -> userInput.value = newValue },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1F)
                )

                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "user input : ${userInput.value}", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier.background(Color.Red, shape = RoundedCornerShape(50)),
                    content = {
                        Image(painterResource(R.drawable.send), "")
                    }
                )
            }
        }
    }
}

@Composable
fun EventItem(event: EventModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(event.name)  // Exemple d'affichage du nom de l'événement
        Text(event.date)  // Exemple d'affichage de la date
    }
}
