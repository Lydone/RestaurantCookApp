package com.lydone.restaurantcookapp.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRoute() =
    Column(Modifier.fillMaxSize().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = "", onValueChange = {}, label = { Text("Логин") })
        TextField(value = "", onValueChange = {}, label = { Text("Пин-код") })
        Spacer(Modifier.height(16.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Войти")
        }
    }