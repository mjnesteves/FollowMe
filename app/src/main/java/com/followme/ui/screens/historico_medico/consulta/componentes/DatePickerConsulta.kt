package com.followme.ui.screens.historico_medico.consulta.componentes



import android.widget.DatePicker
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
import com.followme.ui.screens.medicacao.medicamento.MedicamentoViewModel
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Date


@Composable
fun Data(
    contexto: String,
    data: (String) -> Unit,
    updateViewModel: (String) -> Unit,
    uiState: MedicamentoViewModel.MedicamentoUIState,
) {
    Text(

        text = contexto,
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    var selectedDate by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val ano: Int = calendar.get(Calendar.YEAR)
    val mes: Int = calendar.get(Calendar.MONTH)
    val dia: Int = calendar.get(Calendar.DAY_OF_MONTH)

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
            ano,
            mes,
            dia
        )

    TextField(
        placeholder = { Text(" Selecionar") },
        readOnly = true,
        value = uiState.dataFim,
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








