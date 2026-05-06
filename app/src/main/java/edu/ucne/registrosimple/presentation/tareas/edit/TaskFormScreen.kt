package edu.ucne.registrosimple.presentation.tareas.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    viewModel: TaskFormViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // Efecto para navegar hacia atrás cuando se guarda
    LaunchedEffect(state.saved) {
        if (state.saved) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if(state.isNew) "Nueva Tarea" else "Editar Tarea") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { viewModel.onEvent(TaskFormUiEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_description"),
                isError = state.descripcionError != null,
                supportingText = state.descripcionError?.let { { Text(it) } },
                singleLine = false,
                minLines = 3,
                maxLines = 5
            )

            OutlinedTextField(
                value = state.tiempo,
                onValueChange = { viewModel.onEvent(TaskFormUiEvent.TiempoChanged(it)) },
                label = { Text("Tiempo (minutos)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_time"),
                isError = state.tiempoError != null,
                supportingText = state.tiempoError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Button(
                onClick = { viewModel.onEvent(TaskFormUiEvent.Save) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_save"),
                enabled = !state.isSaving
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}
