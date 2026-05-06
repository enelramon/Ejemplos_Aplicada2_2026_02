package edu.ucne.registrosimple.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.registrosimple.presentation.tareas.edit.TaskFormScreen
import edu.ucne.registrosimple.presentation.tareas.list.TaskListScreen

@Composable
fun TaskNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TaskList
    ) {
        composable<Screen.TaskList> {
            TaskListScreen(
                onAddTask = {
                    navController.navigate(Screen.TaskForm(0))
                },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.TaskForm(id))
                }
            )
        }
        
        composable<Screen.TaskForm> {
            TaskFormScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
