package com.followme.ui.screens.medicacao.medicamento.componentes


import android.annotation.SuppressLint
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.followme.ui.screens.historico_medico.consulta.ConsultaViewModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun Data(
    contexto: String,
    data: (String) -> Unit,
    updateViewModel: (String) -> Unit,
    uiState: ConsultaViewModel.ConsultaUIState,
    viewModel: ConsultaViewModel
) {
    Text(

        text = contexto,
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val currentDate = Date().toFormattedStringDate()
    var selectedDate by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    val datePickerDialog =
        android.app.DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val newDate = Calendar.getInstance()
                newDate.set(year, month, dayOfMonth)
                selectedDate = " ${month.toMonthName()} $dayOfMonth, $year"
                //selectedDate = "$dayOfMonth - $month.t - $year"
                //data(newDate.timeInMillis)
                data(selectedDate)
                updateViewModel(selectedDate)
            },
            year,
            month,
            day
        )

    TextField(
        placeholder = { Text(" Selecionar") },
        readOnly = true,
        value = uiState.dataConsulta,
        onValueChange = {
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendário"
            )
            Icons.Filled.AccessTime
        },
        interactionSource = interactionSource
    )

    if (isPressed) {
        datePickerDialog.show()
    }
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

fun Date.toFormattedStringDate(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.toFormattedStringHour(): String {
    val simpleHour = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleHour.format(this)
}


@SuppressLint("DefaultLocale")
@Composable
fun Hora(
    contexto: String,
    hora: (String) -> Unit,
    updateViewModel: (String) -> Unit,
    uiState: ConsultaViewModel.ConsultaUIState,
    viewModel: ConsultaViewModel
) {
    Text(
        text = contexto,
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val currentHour = Date().toFormattedStringHour()
    var selectedHour by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
    val minute: Int = calendar.get(Calendar.MINUTE)
    val is24HourView = true

    val timePickerDialog =
        android.app.TimePickerDialog(
            context,
            { _: TimePicker, hour: Int, minute: Int ->
                val newHour = Calendar.getInstance()
                selectedHour = String.format("%02d:%02d", hour, minute)
                //hora(newHour.timeInMillis)
                hora(selectedHour)
                updateViewModel(selectedHour)
            },
            hour,
            minute,
            is24HourView

        )

    TextField(
        placeholder = { Text("Selecionar") },
        readOnly = true,
        value = uiState.horaConsulta,
        onValueChange = {
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Relogio"
            )
            Icons.Filled.AccessTime
        },
        interactionSource = interactionSource
    )

    if (isPressed) {
        timePickerDialog.show()
    }
}




