package edu.ucne.registrosimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.registrosimple.ui.theme.RegistroSimpleTheme
import edu.ucne.registrosimple.presentation.navigation.TaskNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroSimpleTheme {
                Alfredo()
                //TaskNavHost()
            }
        }
    }
}

@Preview
@Composable
fun Alfredo() {
    Column() {
        Row {
            Text("Alfred")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edad:33")
        }
        HorizontalDivider()
        Text("15 mins Ago")
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}*/

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RegistroSimpleTheme {
//        Greeting("Android")
//    }
//}