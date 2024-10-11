import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fit5046_project.Sleep
import com.example.fit5046_project.SleepViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField

@Composable
fun SleepScreen(viewModel: SleepViewModel) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val sleepList by viewModel.allSleepData.observeAsState(initial = emptyList())
            var isAddingSleep by remember { mutableStateOf(false) }
            var editingSleepId by remember { mutableStateOf(-1L) }
            var startTime by remember { mutableStateOf("") }
            var endTime by remember { mutableStateOf("") }
            var quality by remember { mutableStateOf("") }

            // Dialog for adding/editing sleep
            if (isAddingSleep || editingSleepId != -1L) {
                AddEditSleepDialog(
                    isAddingSleep = isAddingSleep,
                    sleepId = editingSleepId,
                    startTime = startTime,
                    endTime = endTime,
                    quality = quality,
                    onDismiss = {
                        isAddingSleep = false
                        editingSleepId = -1L
                        startTime = ""
                        endTime = ""
                        quality = ""
                    },
                    onSleepAdded = {
                        isAddingSleep = false
                        editingSleepId = -1L
                        startTime = ""
                        endTime = ""
                        quality = ""
                    },
                    onSleepUpdated = {
                        isAddingSleep = false
                        editingSleepId = -1L
                        startTime = ""
                        endTime = ""
                        quality = ""
                    }
                )
            }

            // Sleep entries list

            LazyColumn {
                items(sleepList) { sleep ->
                    SleepListItem(
                        sleep = sleep,
                        onEditClicked = {
                            editingSleepId = sleep.id
                            startTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                    Date(sleep.startTime)
                                )
                            endTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                    Date(sleep.endTime)
                                )
                            quality = sleep.quality.toString()
                            isAddingSleep = true
                        },
                        onDeleteClicked = { viewModel.delete(sleep) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to add new sleep
            Button(
                onClick = { isAddingSleep = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Add Sleep")
            }
        }
    }
}

@Composable
fun SleepListItem(
    sleep: Sleep,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Start Time: ${
                    SimpleDateFormat(
                        "yyyy-MM-dd HH:mm",
                        Locale.getDefault()
                    ).format(Date(sleep.startTime))
                }",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "End Time: ${
                    SimpleDateFormat(
                        "yyyy-MM-dd HH:mm",
                        Locale.getDefault()
                    ).format(Date(sleep.endTime))
                }",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "Quality: ${sleep.quality}",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEditClicked) {
                    Text(text = "Edit")
                }
                TextButton(onClick = onDeleteClicked) {
                    Text(text = "Delete")
                }
            }
        }
    }
}


@Composable
fun AddEditSleepDialog(
    isAddingSleep: Boolean,
    sleepId: Long,
    startTime: String,
    endTime: String,
    quality: String,
    onDismiss: () -> Unit,
    onSleepAdded: () -> Unit,
    onSleepUpdated: () -> Unit
) {
    var newStartTime by remember { mutableStateOf(startTime) }
    var newEndTime by remember { mutableStateOf(endTime) }
    var newQuality by remember { mutableStateOf(quality) }

    if (isAddingSleep || sleepId != -1L) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = if (isAddingSleep) "Add Sleep" else "Edit Sleep")
            },
            confirmButton = {
                Button(
                    onClick = {
                        val startDateTime =
                            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(
                                newStartTime
                            )?.time
                        val endDateTime =
                            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(
                                newEndTime
                            )?.time
                        val sleepQuality = newQuality.toIntOrNull() ?: 0

                        val newSleep = startDateTime?.let {
                            endDateTime?.let { it1 ->
                                Sleep(
                                    id = sleepId,
                                    startTime = it,
                                    endTime = it1,
                                    quality = sleepQuality
                                )
                            }
                        }

                        if (isAddingSleep) {
                            onSleepAdded()
                        } else {
                            onSleepUpdated()
                        }

                        onDismiss()
                    }
                ) {
                    Text(text = if (isAddingSleep) "Add" else "Update")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    TextField(
                        value = newStartTime,
                        onValueChange = { newStartTime = it },
                        label = { Text(text = "Start Time") },
                        singleLine = true
                    )
                    TextField(
                        value = newEndTime,
                        onValueChange = { newEndTime = it },
                        label = { Text(text = "End Time") },
                        singleLine = true
                    )
                    TextField(
                        value = newQuality,
                        onValueChange = { newQuality = it },
                        label = { Text(text = "Quality") },
                        singleLine = true
                    )
                }
            }
        )
    }
}

