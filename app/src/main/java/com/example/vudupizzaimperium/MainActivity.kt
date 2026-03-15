package com.example.vudupizzaimperium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vudupizzaimperium.screens.ComandaScreen
import com.example.vudupizzaimperium.screens.InicialScreen
import com.example.vudupizzaimperium.screens.ResumScreen
import com.example.vudupizzaimperium.ui.theme.VuduPizzaImperiumTheme

object Rutes {
    const val INICI = "inici"
    const val COMANDA = "comanda"
    const val RESUM = "resum"
}

class MainActivity : ComponentActivity() {

    private val pizzaViewModel: PizzaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VuduPizzaImperiumTheme {
                PizzaApp(viewModel = pizzaViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaApp(viewModel: PizzaViewModel) {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route ?: Rutes.INICI

    val titolPantalla = when (rutaActual) {
        Rutes.INICI   -> "Inici"
        Rutes.COMANDA -> "Nova comanda"
        Rutes.RESUM   -> "Resum de la comanda"
        else          -> "Pizzeria Panucci"
    }

    val mostrarBotoEnrere = rutaActual == Rutes.COMANDA || rutaActual == Rutes.RESUM

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = titolPantalla,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (mostrarBotoEnrere) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Enrere"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Rutes.INICI,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Rutes.INICI) {
                InicialScreen(
                    onComencarComanda = {
                        navController.navigate(Rutes.COMANDA)
                    }
                )
            }

            composable(Rutes.COMANDA) {
                ComandaScreen(
                    viewModel = viewModel,
                    onVeureResum = {
                        navController.navigate(Rutes.RESUM)
                    }
                )
            }

            composable(Rutes.RESUM) {
                ResumScreen(
                    viewModel = viewModel,
                    onConfirmar = {
                        viewModel.confirmarComanda()
                    },
                    onReiniciar = {
                        viewModel.reiniciarComanda()
                        navController.navigate(Rutes.INICI) {
                            popUpTo(Rutes.INICI) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
