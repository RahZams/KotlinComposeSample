package com.example.kotlincomposesample

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlincomposesample.ui.theme.Grey1
import com.example.kotlincomposesample.ui.theme.OmGreenDark
import com.example.kotlincomposesample.ui.theme.OmGreenLight
import com.example.kotlincomposesample.ui.theme.OmTextDark
import com.example.kotlincomposesample.ui.theme.dp1
import com.example.kotlincomposesample.ui.theme.dp16
import com.example.kotlincomposesample.ui.theme.dp24
import com.example.kotlincomposesample.ui.theme.dp32
import com.example.kotlincomposesample.ui.theme.dp48
import com.example.kotlincomposesample.ui.theme.dp6
import com.example.kotlincomposesample.ui.theme.lightGrey
import com.example.kotlincomposesample.ui.theme.sp16
import com.example.kotlincomposesample.ui.theme.sp24

@Composable
fun BloodPressureDetails(
    onBack: () -> Unit = {},
    viewModel: BloodPressureViewModel = viewModel()
) {
    val weightValue by viewModel.weightValue.collectAsStateWithLifecycle()
    val selectedUnit by viewModel.selectedUnit.collectAsStateWithLifecycle()
    val dateAdded by viewModel.dateValue.collectAsStateWithLifecycle()
    val timeAdded by viewModel.timeValue.collectAsStateWithLifecycle()

    var showWeightPicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = java.util.Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            val months = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
            viewModel.onDateValueChange(String.format("%02d %s %04d", day, months[month], year))
        },
        calendar.get(java.util.Calendar.YEAR),
        calendar.get(java.util.Calendar.MONTH),
        calendar.get(java.util.Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            val amPm = if (hour >= 12) "PM" else "AM"
            val hour12 = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }
            viewModel.onTimeValueChange(String.format("%02d:%02d %s", hour12, minute, amPm))
        },
        calendar.get(java.util.Calendar.HOUR_OF_DAY),
        calendar.get(java.util.Calendar.MINUTE),
        false
    )

    if (showWeightPicker) {
        WeightPickerDialog(
            initialWeight = weightValue,
            onDismiss = { showWeightPicker = false },
            unit = "kg",
            onConfirm = {
                viewModel.onWeightValueChange(it)
                showWeightPicker = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderDetails()
            Spacer(modifier = Modifier.height(dp24))
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Close",
                    tint = Color.Black

                )
            }
            Spacer(modifier = Modifier.height(dp32))
            Icon(
                painter = painterResource(R.drawable.weight),
                contentDescription = "Weight",
                tint = OmGreenDark,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dp16))
            Text(
                text = "Weight",
                fontSize = sp24.value.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(dp48))
            Column(modifier = Modifier.fillMaxWidth()
                .padding(start = dp16)) {
                Text(
                    text = "Weight",
                    fontSize = sp16.value.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Light,
                )
                Spacer(modifier = Modifier.height(dp16))
                AddUnitsLayout(
                    selectedUnit = selectedUnit,
                    textValue = weightValue,
                    onValueChange = { viewModel.onWeightValueChange(it) },
                    onUnitSelected = { viewModel.onUnitSelected(it) },
                    onWeightClick = { showWeightPicker = true }
                )
            }
            Spacer(modifier = Modifier.height(dp48))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dp16)) {
                Column(modifier = Modifier.weight(1f).padding(start = dp16)) {
                    Text(
                        text = "Date",
                        fontSize = sp16.value.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Light,
                    )
                    Spacer(modifier = Modifier.height(dp16))
                    Box(modifier = Modifier.clickable { datePickerDialog.show() }) {
                        BasicTextField(
                            value = dateAdded,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = dp1, color = lightGrey, shape = RoundedCornerShape(dp6))
                                .padding(dp24),
                            singleLine = true,
                            textStyle = TextStyle(color = OmTextDark, fontSize = dp16.value.sp),
                            decorationBox = { inner -> inner() }
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f).padding(start = dp16)) {
                    Text(
                        text = "Time",
                        fontSize = sp16.value.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Light,
                    )
                    Spacer(modifier = Modifier.height(dp16))
                    Box(modifier = Modifier.clickable { timePickerDialog.show() }) {
                        BasicTextField(
                            value = timeAdded,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = dp1, color = lightGrey, shape = RoundedCornerShape(dp6))
                                .padding(dp24),
                            singleLine = true,
                            textStyle = TextStyle(color = OmTextDark, fontSize = dp16.value.sp),
                            decorationBox = { inner -> inner() }
                        )
                    }
                }
            }

        }
    }

}

@Composable
fun HeaderDetails() {
    Box(modifier = Modifier.fillMaxWidth()
        .background(brush = Brush.verticalGradient(colors = listOf(OmGreenDark,OmGreenLight)))){
        Spacer(modifier = Modifier.fillMaxWidth().windowInsetsTopHeight(WindowInsets.statusBars))
    }
}

@Composable
fun AddUnitsLayout(
    selectedUnit: String?,
    textValue: String,
    onValueChange: (String) -> Unit,
    onUnitSelected: (String) -> Unit,
    onWeightClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(end = dp16),
        horizontalArrangement = Arrangement.spacedBy(dp16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable { onWeightClick() }
        ) {
            BasicTextField(
                value = textValue,
                onValueChange = onValueChange,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = dp1, color = lightGrey, shape = RoundedCornerShape(dp6))
                    .padding(dp24),
                singleLine = true,
                textStyle = TextStyle(color = OmTextDark, fontSize = dp16.value.sp),
                decorationBox = { inner ->
                    if (textValue.isEmpty()) {
                        Text(text = "- - -", color = Grey1)
                    }
                    inner()
                }
            )
        }

        SegmentedUnitSelector(
            options = listOf("kg", "lbs"),
            selectedOption = selectedUnit,
            onOptionSelected = onUnitSelected,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SegmentedUnitSelector(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .border(1.dp, OmGreenDark, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option == selectedOption

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(if (isSelected) OmGreenDark else Color.White)
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = dp24, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.White else Color(0xFF9E9E9E),
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    fontSize = 16.sp
                )
            }

            if (index < options.size - 1) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(OmGreenDark)
                )
            }
        }
    }
}

@Composable
fun WeightPickerDialog(
    initialWeight: String,
    unit: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val parts = initialWeight.split(".")
    val initialInteger = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val initialDecimal = parts.getOrNull(1)?.toIntOrNull() ?: 0

    val integerState = remember { mutableIntStateOf(initialInteger) }
    val decimalState = remember { mutableIntStateOf(initialDecimal) }

    val maxUnitValue = if(unit == "lbs") 1100 else 300
    val minUnitValue = if (unit == "lbs") 66 else 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Weight",
                fontWeight = FontWeight.SemiBold,
                fontSize = sp16.value.sp
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = minUnitValue
                            maxValue = maxUnitValue
                            value = initialInteger
                            setOnValueChangedListener { _, _, newVal -> integerState.intValue = newVal }
                        }
                    }
                )
                Text(
                    text = ".",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = dp6)
                )
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 9
                            value = initialDecimal
                            setOnValueChangedListener { _, _, newVal -> decimalState.intValue = newVal }
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm("${integerState.intValue}.${decimalState.intValue}") }) {
                Text(text = "OK", color = OmGreenDark)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel", color = OmGreenDark)
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BloodPressureDetailsPreview(){
    BloodPressureDetails()
}
