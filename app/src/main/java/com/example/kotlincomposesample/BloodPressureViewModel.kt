package com.example.kotlincomposesample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class BloodPressureViewModel : ViewModel() {

    private val _weightValue = MutableStateFlow("60")
    val weightValue: StateFlow<String> = _weightValue.asStateFlow()

    private val _selectedUnit = MutableStateFlow("kg")
    val selectedUnit: StateFlow<String> = _selectedUnit.asStateFlow()

    private val _dateValue = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
    val dateValue: StateFlow<String> = _dateValue.asStateFlow()

    private val _timeValue = MutableStateFlow(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")))
    val timeValue: StateFlow<String> = _timeValue.asStateFlow()

    fun onWeightValueChange(value: String) {
        _weightValue.value = value
    }

    fun onUnitSelected(unit: String) {
        _selectedUnit.value = unit
    }

    fun onDateValueChange(value: String) {
        _dateValue.value = value
    }

    fun onTimeValueChange(value: String) {
        _timeValue.value = value
    }

}