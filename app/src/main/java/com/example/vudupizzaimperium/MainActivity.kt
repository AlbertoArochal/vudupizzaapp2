package com.example.vudupizzaimperium

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vudupizzaimperium.components.StepIndicator
import com.example.vudupizzaimperium.components.TechnicalPanel
import com.example.vudupizzaimperium.ui.theme.VuduPizzaImperiumTheme

// ─────────────────────────────────────────────
// MainActivity: gestiona el ciclo de vida y
// arranca la UI principal
// ─────────────────────────────────────────────
class MainActivity : ComponentActivity() {
    private val lifecycleViewModel: LifecycleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleEvent("onCreate")
        enableEdgeToEdge()

        setContent {
            VuduPizzaImperiumTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PizzaApp(viewModel = lifecycleViewModel)
                }
            }
        }
    }

    override fun onStart()   { super.onStart();   registerLifecycleEvent("onStart") }
    override fun onResume()  { super.onResume();  registerLifecycleEvent("onResume") }
    override fun onPause()   { registerLifecycleEvent("onPause");   super.onPause() }
    override fun onStop()    { registerLifecycleEvent("onStop");    super.onStop() }
    override fun onRestart() { super.onRestart(); registerLifecycleEvent("onRestart") }
    override fun onDestroy() { registerLifecycleEvent("onDestroy"); super.onDestroy() }

    private fun registerLifecycleEvent(event: String) {
        Log.d(TAG, event)
        lifecycleViewModel.addLog(event)
    }

    companion object {
        private const val TAG = "PizzaLifecycle"
    }
}

// ─────────────────────────────────────────────
// PizzaApp: composable raíz, gestiona el estado
// del pedido y los contadores del ejercicio
// ─────────────────────────────────────────────
@Composable
fun PizzaApp(viewModel: LifecycleViewModel) {

    var currentStep by rememberSaveable { mutableStateOf(PizzaStep.NOM) }

    // Req. 5: 3 tipos de variable para observar diferencias de estado
    var contadorNormal = 0
    var contadorRemember by remember { mutableStateOf(0) }
    var contadorSaveable by rememberSaveable { mutableStateOf(0) }

    // Estado del pedido
    var nom by rememberSaveable { mutableStateOf("") }
    var quantitatText by rememberSaveable { mutableStateOf("") }
    var tipusPizza by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cabecera con nombre de la app y barra de progreso de pasos
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Buon Vudu Pizza",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(text = "Haz tu pedido en 4 pasos", style = MaterialTheme.typography.bodyLarge)
                StepIndicator(currentStep = currentStep)
            }
        }

        // Pantalla activa según el paso actual (Req. 1 + 2)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (currentStep) {
                    PizzaStep.NOM -> ScreenNombre(
                        nom = nom,
                        onNomChange = { nom = it },
                        onNext = { currentStep = PizzaStep.QUANTITAT }
                    )
                    PizzaStep.QUANTITAT -> ScreenQuantitat(
                        quantitatText = quantitatText,
                        onQuantitatChange = { quantitatText = it },
                        onNext = { currentStep = PizzaStep.TIPUS }
                    )
                    PizzaStep.TIPUS -> ScreenTipus(
                        tipusSeleccionat = tipusPizza,
                        onTipusChange = { tipusPizza = it },
                        onNext = { currentStep = PizzaStep.RESUM }
                    )
                    PizzaStep.RESUM -> ScreenResum(
                        nom = nom,
                        quantitat = quantitatText.toIntOrNull() ?: 0,
                        tipusPizza = tipusPizza,
                        onRestart = {
                            nom = ""
                            quantitatText = ""
                            tipusPizza = ""
                            currentStep = PizzaStep.NOM
                        }
                    )
                }
            }
        }

        // Panel técnico: contadores (Req. 5) + historial lifecycle (Req. 4)
        TechnicalPanel(
            viewModel = viewModel,
            contadorNormal = contadorNormal,
            contadorRemember = contadorRemember,
            contadorSaveable = contadorSaveable,
            onIncrementCounters = {
                contadorNormal++
                contadorRemember++
                contadorSaveable++
            }
        )
    }
}

// ─────────────────────────────────────────────
// Pantallas del flujo de pedido (Req. 1 + 3)
// ─────────────────────────────────────────────

@Composable
fun ScreenNombre(
    nom: String,
    onNomChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val nomValid = nom.trim().isNotEmpty()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Paso 1: Nombre", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nom,
            onValueChange = onNomChange,
            label = { Text("Nombre del cliente") },
            singleLine = true,
            isError = !nomValid,
            modifier = Modifier.fillMaxWidth()
        )

        if (!nomValid) {
            Text(
                text = "Error: el nombre no puede estar vacío",
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onNext,
            enabled = nomValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Següent")
        }
    }
}

@Composable
fun ScreenQuantitat(
    quantitatText: String,
    onQuantitatChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val quantitat = quantitatText.toIntOrNull() ?: 0
    val quantitatValida = quantitat > 0

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Paso 2: Cantidad", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = quantitatText,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) onQuantitatChange(newValue)
            },
            label = { Text("Número de pizzas") },
            singleLine = true,
            isError = !quantitatValida,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (quantitatText.isEmpty()) {
            Text(text = "Error: la cantidad es obligatoria", color = MaterialTheme.colorScheme.error)
        } else if (!quantitatValida) {
            Text(text = "Error: la cantidad debe ser mayor que 0", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = onNext,
            enabled = quantitatValida,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Següent")
        }
    }
}

@Composable
fun ScreenTipus(
    tipusSeleccionat: String,
    onTipusChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val opcions = listOf("Margarita", "4 Formatges", "Barbacoa", "Vegetal")
    val tipusValid = tipusSeleccionat.isNotEmpty()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Paso 3: Tipo", style = MaterialTheme.typography.titleLarge)

        opcions.forEach { tipus ->
            val seleccionat = tipusSeleccionat == tipus
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTipusChange(tipus) },
                colors = CardDefaults.cardColors(
                    containerColor = if (seleccionat) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (seleccionat) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = seleccionat,
                        onClick = { onTipusChange(tipus) }
                    )
                    Text(text = tipus)
                }
            }
        }

        if (!tipusValid) {
            Text(text = "Error: debes elegir un tipo de pizza", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = onNext,
            enabled = tipusValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Següent")
        }
    }
}

@Composable
fun ScreenResum(
    nom: String,
    quantitat: Int,
    tipusPizza: String,
    onRestart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Paso 4: Resumen", style = MaterialTheme.typography.titleLarge)
            Text(text = "Nombre: $nom")
            Text(text = "Cantidad: $quantitat")
            Text(text = "Tipo: $tipusPizza")

            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(text = "Reiniciar")
            }
        }
    }
}
