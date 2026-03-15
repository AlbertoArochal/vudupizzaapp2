package com.example.pizzeria2.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzeria2.PizzaViewModel

@Composable
fun ComandaScreen(
    viewModel: PizzaViewModel,
    onVeureResum: () -> Unit
) {
    val opcions = listOf("Margarita", "Barbacoa", "4 Formatges", "Vegetal")

    val nomValid = viewModel.nomClient.trim().isNotEmpty()
    val quantitatValida = viewModel.quantitat >= 1
    val tipusValid = viewModel.tipusPizza.isNotEmpty()
    val formulariValid = nomValid && quantitatValida && tipusValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Nom del client",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = viewModel.nomClient,
            onValueChange = { viewModel.updateNomClient(it) },
            label = { Text("Nom") },
            singleLine = true,
            isError = !nomValid && viewModel.nomClient.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (viewModel.nomClient.isNotEmpty() && !nomValid) {
                    Text(
                        text = "El nom no pot estar buit",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Text(
            text = "Quantitat de pizzes",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { viewModel.decrementQuantitat() },
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Text(text = "−", fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = viewModel.quantitat.toString(),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.width(24.dp))

            OutlinedButton(
                onClick = { viewModel.incrementQuantitat() },
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Text(text = "+", fontSize = 22.sp)
            }
        }

        Text(
            text = "Tipus de pizza",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        opcions.forEach { tipus ->
            val seleccionat = viewModel.tipusPizza == tipus
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.updateTipusPizza(tipus) },
                colors = CardDefaults.cardColors(
                    containerColor = if (seleccionat)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = if (seleccionat)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = seleccionat,
                        onClick = { viewModel.updateTipusPizza(tipus) }
                    )
                    Text(
                        text = tipus,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = onVeureResum,
            enabled = formulariValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Veure resum")
        }

        if (!formulariValid) {
            Text(
                text = "Omple tots els camps per continuar",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
