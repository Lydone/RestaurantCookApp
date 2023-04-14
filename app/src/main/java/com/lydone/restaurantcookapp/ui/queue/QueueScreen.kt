package com.lydone.restaurantcookapp.ui.queue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.lydone.restaurantcookapp.R
import com.lydone.restaurantcookapp.data.Entry
import kotlinx.datetime.Clock

@Composable
fun QueueRoute(viewModel: QueueViewModel = hiltViewModel()) {
    var updateStatusEntry by remember { mutableStateOf<Entry?>(null) }
    Screen(viewModel.state.collectAsState().value, onEntryClick = { updateStatusEntry = it })
    updateStatusEntry?.let {
        UpdateStatusDialog(
            onDismiss = { updateStatusEntry = null },
            onConfirmClick = { viewModel.updateEntryStatus(it.id) },
        )
    }
}

@Composable
private fun UpdateStatusDialog(onDismiss: () -> Unit, onConfirmClick: () -> Unit) {
    Dialog(onDismiss) {
        Column(
            Modifier
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.shapes.large
                )
                .padding(24.dp),
            Arrangement.spacedBy(16.dp)
        ) {
            Text(
                stringResource(R.string.title_update_dish_status_confirmation),
                style = MaterialTheme.typography.displayMedium,
            )
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceAround) {
                Button(
                    onClick = {
                        onConfirmClick()
                        onDismiss()
                    },
                    Modifier.sizeIn(minWidth = 200.dp, minHeight = 100.dp)
                ) {
                    Text(
                        stringResource(R.string.button_confirm),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                Button(
                    onClick = onDismiss,
                    Modifier.sizeIn(minWidth = 200.dp, minHeight = 100.dp)
                ) {
                    Text(
                        stringResource(R.string.button_cancel),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(state: QueueViewModel.State, onEntryClick: (Entry) -> Unit) = LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    items(state.queue) { entry ->
        Card(
            onClick = { onEntryClick(entry) },
            colors = CardDefaults.cardColors(
                containerColor = if (entry.status == Entry.Status.QUEUE) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        entry.dish.name,
                        style = MaterialTheme.typography.displayLarge,
                    )
                    if (entry.comment != null) Text(
                        entry.comment,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        stringResource(R.string.table_placeholder, entry.table),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        stringResource(
                            R.string.minutes_ago_placeholder,
                            Clock.System.now().minus(entry.instant).inWholeMinutes
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        stringStatus(entry.status),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun stringStatus(status: Entry.Status) = stringResource(
    when (status) {
        Entry.Status.QUEUE -> R.string.status_queue
        Entry.Status.COOKING -> R.string.status_cooking
        Entry.Status.READY -> R.string.status_ready
    }
)