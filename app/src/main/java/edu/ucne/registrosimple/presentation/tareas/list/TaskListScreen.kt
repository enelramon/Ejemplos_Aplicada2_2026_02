package edu.ucne.registrosimple.presentation.tareas.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrosimple.domain.tareas.model.Task

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
    onAddTask: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            onAddTask()
        }
    }
    
    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let { id ->
            onNavigateToEdit(id)
        }
    }
    
    TaskListBody(state, viewModel::onEvent, onAddTask)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListBody(
    state: TaskListUiState,
    onEvent: (TaskListUiEvent) -> Unit,
    onAddTask: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(TaskListUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else {
                if (state.tasks.isEmpty()) {
                    Text(
                        text = "No hay tareas",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.tasks,
                            key = { it.tareaId }
                        ) { task ->
                            TaskItem(
                                task = task,
                                onDelete = {
                                    onEvent(TaskListUiEvent.Delete(task.tareaId))
                                },
                                onClick = {
                                    onEvent(TaskListUiEvent.Edit(task.tareaId))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("task_item_${task.tareaId}"),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "${task.tiempo} min",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${task.tareaId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea"
                )
            }
        }
    }
}
