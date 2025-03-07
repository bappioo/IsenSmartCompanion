package fr.isen.bastien.isensmartcompanion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.isen.bastien.isensmartcompanion.database.Interaction
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.GenerativeModel
import fr.isen.bastien.isensmartcompanion.Models.EventModel
import fr.isen.bastien.isensmartcompanion.database.AppDatabase
import fr.isen.bastien.isensmartcompanion.screens.EventsScreen
import fr.isen.bastien.isensmartcompanion.screens.HistoryScreen
import fr.isen.bastien.isensmartcompanion.screens.MainScreen
import fr.isen.bastien.isensmartcompanion.screens.TabView
import fr.isen.bastien.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import kotlinx.coroutines.launch
import java.util.Date

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifecycle", "MainActivity onCreate")
        enableEdgeToEdge()
        setContent {
            val homeTab = TabBarItem(title = getString(R.string.bottom_navbar_home), selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
            val eventsTab = TabBarItem(title = getString(R.string.bottom_navbar_events), selectedIcon = Icons.Filled.Notifications, unselectedIcon = Icons.Outlined.Notifications)
            val historyTab = TabBarItem(title = getString(R.string.bottom_navbar_history), selectedIcon = Icons.Filled.List, unselectedIcon = Icons.Outlined.List)

            val tabBarItems = listOf(homeTab, eventsTab, historyTab)

            val navController = rememberNavController()

            val db by lazy {
                AppDatabase.getDatabase(this)
            }

            ISENSmartCompanionTheme {
                Scaffold( bottomBar = {
                    TabView(tabBarItems, navController)
                },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->

                      NavHost(navController = navController, startDestination = homeTab.title) {
                          composable(homeTab.title) {
                              MainScreen(innerPadding)
                          }
                          composable(eventsTab.title) {
                              EventsScreen(
                                  innerPadding = innerPadding,
                                  eventHandler = { event ->
                                      startEventDetailActivity(event)
                                  }
                              )
                          }
                          composable(historyTab.title) {
                              HistoryScreen(innerPadding, context = LocalContext.current)
                          }
                      }

                }
            }
        }
    }

    private val db by lazy {
        AppDatabase.getDatabase(this)
    }

    @SuppressLint("SecretInSource")
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyBTn44myQznoJpHKbMrrlrJbY-i45PrZsA" //Clé API
    )
    private fun saveInteraction(question: String, answer: String) {
        lifecycleScope.launch {
            val interaction = Interaction(
                question = question,
                answer = answer,
                date = Date()
            )
            db.interactionDao().insert(interaction)
        }
    }

    fun analyzeTextWithAI(userInput: String, onResponse: (String) -> Unit) {
        lifecycleScope.launch {
            try {
                val response = generativeModel.generateContent(userInput)

                val responseText = response.text
                if (responseText != null) {
                    Log.d("GeminiResponse", responseText)
                    onResponse(responseText)
                } else {
                    Log.e("GeminiError", "La réponse de ISEN IA est null")
                    onResponse("Erreur : Aucune réponse de ISEN IA")
                }
            } catch (e: Exception) {
                Log.e("GeminiError", "Error: ${e.message}")
                onResponse("Erreur : ${e.message}")
            }
        }
    }

    private fun startEventDetailActivity(event: EventModel) {
        val intent = Intent(this, EventDetailActivity::class.java).apply {
            putExtra(EventDetailActivity.eventExtraKey, event)
        }
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "MainActivity onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "MainActivity onResume")
    }

    override fun onStop() {
        Log.d("lifecycle", "MainActivity onStop")
        super.onStop()
    }

    override fun onPause() {
        Log.d("lifecycle", "MainActivity onPause")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("lifecycle", "MainActivity onDestroy")
        super.onDestroy()
    }
}