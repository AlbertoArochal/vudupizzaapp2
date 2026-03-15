package com.example.pizzeria2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PizzaViewModel : ViewModel() {

    var nomClient by mutableStateOf("")
        private set

    var quantitat by mutableIntStateOf(1)
        private set

    var tipusPizza by mutableStateOf("")
        private set

    var comandaConfirmada by mutableStateOf(false)
        private set

    fun updateNomClient(nom: String) {
        nomClient = nom
    }

    fun incrementQuantitat() {
        quantitat++
    }

    fun decrementQuantitat() {
        if (quantitat > 1) quantitat--
    }

    fun updateTipusPizza(tipus: String) {
        tipusPizza = tipus
    }

    fun confirmarComanda() {
        comandaConfirmada = true
    }

    fun reiniciarComanda() {
        nomClient = ""
        quantitat = 1
        tipusPizza = ""
        comandaConfirmada = false
    }

    fun resetConfirmacio() {
        comandaConfirmada = false
    }
}
