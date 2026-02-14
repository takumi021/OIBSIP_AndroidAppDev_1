package com.takumi.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takumi.unitconverter.ui.theme.UnitConverterTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                ConverterScreen()
            }
        }
    }
}

enum class LengthUnit(val label: String, val metersFactor: Double) {
    Millimeter("Millimeter", 0.001),
    Centimeter("Centimeter", 0.01),
    Meter("Meter", 1.0),
    Kilometer("Kilometer", 1000.0),
    Inch("Inch", 0.0254),
    Foot("Foot", 0.3048)
}

@Composable
fun ConverterScreen(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf(LengthUnit.Centimeter) }
    var toUnit by remember { mutableStateOf(LengthUnit.Meter) }
    var resultText by remember { mutableStateOf("0.0000 Meter") }
    var inputError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                        inputError = false
                    },
                    label = { Text("Input value") },
                    isError = inputError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (inputError) {
                            Text("Enter a valid numeric value.")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                UnitSelectorRow(
                    fromUnit = fromUnit,
                    toUnit = toUnit,
                    onFromSelected = { fromUnit = it },
                    onToSelected = { toUnit = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val parsedValue = inputValue.toDoubleOrNull()
                        if (parsedValue == null) {
                            inputError = true
                        } else {
                            val meters = parsedValue * fromUnit.metersFactor
                            val converted = meters / toUnit.metersFactor
                            resultText = String.format(Locale.US, "%.4f %s", converted, toUnit.label)
                            inputError = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Convert")
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Result: $resultText",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun UnitSelectorRow(
    fromUnit: LengthUnit,
    toUnit: LengthUnit,
    onFromSelected: (LengthUnit) -> Unit,
    onToSelected: (LengthUnit) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UnitDropdown(
            modifier = Modifier.weight(1f),
            label = "From",
            selected = fromUnit,
            onSelected = onFromSelected
        )
        UnitDropdown(
            modifier = Modifier.weight(1f),
            label = "To",
            selected = toUnit,
            onSelected = onToSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UnitDropdown(
    modifier: Modifier,
    label: String,
    selected: LengthUnit,
    onSelected: (LengthUnit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected.label,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            LengthUnit.entries.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.label) },
                    onClick = {
                        onSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConverterPreview() {
    UnitConverterTheme {
        ConverterScreen()
    }
}
