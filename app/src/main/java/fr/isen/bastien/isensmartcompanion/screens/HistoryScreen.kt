package fr.isen.bastien.isensmartcompanion.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import fr.isen.bastien.isensmartcompanion.database.AppDatabase
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.isen.bastien.isensmartcompanion.Models.Message
import fr.isen.bastien.isensmartcompanion.R
import fr.isen.bastien.isensmartcompanion.database.Interaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(innerPadding: PaddingValues, db: AppDatabase) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val messageDao = db.messageDao()
    var messages by remember { mutableStateOf<List<Message>>(listOf()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                messages = messageDao.getAll()
            } catch (e: Exception) {
                println("Room Error: ${e.message}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.message_history),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black)
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        messageDao.deleteAll()
                        messages = emptyList()
                    }
                },
            ) {
                Text(stringResource(id = R.string.delete_button), color = colorResource(R.color.white))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(messages) { index, message ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            if (message.isUser) colorResource(R.color.user_color_message)
                            else colorResource(R.color.ai_color_message),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (message.isUser) stringResource(id = R.string.user_id) else stringResource(id = R.string.ai_id),
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.black)
                            )
                            Text(
                                text = formatDate(message.timestamp),
                                fontSize = 12.sp,
                                color = colorResource(R.color.black)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = message.text,
                            color = colorResource(R.color.black)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        try {
                                            val pairMessage: Message? = if (message.isUser) messages.getOrNull(index + 1) else messages.getOrNull(index - 1)
                                            messageDao.delete(message)
                                            if (pairMessage != null) {
                                                messageDao.delete(pairMessage)
                                            }
                                            messages = messageDao.getAll()
                                        } catch (e: Exception) {
                                            println("Error delete: ${e.message}")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(id = R.string.delete_description),
                                    tint = colorResource(R.color.red)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}