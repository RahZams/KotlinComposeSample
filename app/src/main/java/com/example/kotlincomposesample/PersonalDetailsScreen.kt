package com.example.kotlincomposesample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlincomposesample.ui.theme.OmBorder
import com.example.kotlincomposesample.ui.theme.OmGreenDark
import com.example.kotlincomposesample.ui.theme.OmGreenLight
import com.example.kotlincomposesample.ui.theme.OmIconOrange
import com.example.kotlincomposesample.ui.theme.OmPlaceholder
import com.example.kotlincomposesample.ui.theme.OmTextDark

// ── Screen ─────────────────────────────────────────────────────────────────────
@Composable
fun PersonalDetailsScreen(
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    var selectedGender by remember { mutableStateOf<String?>(null) }
    val isFormValid = selectedGender != null

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Column(modifier = Modifier.fillMaxSize()) {
            // Green top bar (extends behind the status bar)
            PersonalDetailsHeader(onBack = onBack, onClose = onClose)

            // Scrollable form body
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 23.dp)
                    .padding(top = 23.dp, bottom = 90.dp),  // bottom pad keeps last field above the button
                verticalArrangement = Arrangement.spacedBy(23.dp)
            ) {
                FormField(
                    label = "Nickname",
                    placeholder = "Enter your nickname",
                    icon = painterResource(R.drawable.name)
                )
                FormField(
                    label = "First Name",
                    placeholder = "Enter your name",
                    icon = painterResource(R.drawable.name)
                )
                FormField(
                    label = "Last Name",
                    placeholder = "Enter your last name",
                    icon = painterResource(R.drawable.name)
                )
                FormField(
                    label = "Date of Birth",
                    placeholder = "Enter your date of birth",
                    icon = painterResource(R.drawable.dob)
                )
                GenderSelector(
                    selectedGender   = selectedGender,
                    onGenderSelected = { selectedGender = it }
                )
            }
        }

        // ── Floating "Next" button ──────────────────────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .shadow(elevation = 8.dp)
                .background(Color.White)
                .padding(horizontal = 23.dp, vertical = 15.dp)
        ) {
            val greenDark = OmGreenDark
            Button(
                onClick  = onNext,
                enabled  = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape  = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = greenDark.copy(alpha = 0.38f),
                    disabledContentColor   = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text       = "Next",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ── Header ─────────────────────────────────────────────────────────────────────
@Composable
fun PersonalDetailsHeader(onBack: () -> Unit, onClose: () -> Unit) {
    // The Box covers status-bar + toolbar so the gradient fills both areas.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        OmGreenDark,
                        OmGreenLight
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Reserve space for the system status bar
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )
            // Toolbar row
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back arrow
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "Back",
                        tint               = Color.White
                    )
                }
                // Logo placeholder (centre)
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text       = "OM",
                        color      = Color.White,
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                // Close button
                IconButton(onClick = onClose) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Close",
                        tint               = Color.White
                    )
                }
            }
        }
    }
}

// ── Reusable form field ─────────────────────────────────────────────────────────
@Composable
fun FormField(label: String, placeholder: String, icon: Painter) {
    // Internal state — holds whatever the user types into this field
    var text by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
        Text(
            text          = label,
            color         = OmTextDark,
            fontSize      = 16.sp,
            fontWeight    = FontWeight.SemiBold,
            letterSpacing = (-0.39).sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .border(1.dp, OmBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 15.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter        = icon,
                contentDescription = null,
                tint               = OmIconOrange,
                modifier           = Modifier.size(21.dp)
            )
            // BasicTextField handles user input.
            // decorationBox overlays the hint text when the field is empty.
            BasicTextField(
                value         = text,
                onValueChange = { text = it },
                modifier      = Modifier.weight(1f),
                singleLine    = true,
                textStyle     = TextStyle(
                    color         = OmTextDark,
                    fontSize      = 14.sp,
                    letterSpacing = (-0.39).sp
                ),
                decorationBox = { innerTextField ->
                    // Show hint only when field is empty
                    if (text.isEmpty()) {
                        Text(
                            text          = placeholder,
                            color         = OmPlaceholder,
                            fontSize      = 14.sp,
                            letterSpacing = (-0.39).sp
                        )
                    }
                    innerTextField() // the actual cursor + typed text
                }
            )
        }
    }
}

// ── Gender selector ────────────────────────────────────────────────────────────
@Composable
fun GenderSelector(selectedGender: String?, onGenderSelected: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
        Text(
            text          = "Gender",
            color         = OmTextDark,
            fontSize      = 16.sp,
            fontWeight    = FontWeight.SemiBold,
            letterSpacing = (-0.39).sp
        )
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            GenderOption(
                label    = "Male",
                icon     = painterResource(R.drawable.male),
                selected = selectedGender == "Male",
                onClick  = { onGenderSelected("Male") },
                modifier = Modifier.weight(1f)
            )
            GenderOption(
                label    = "Female",
                icon     = painterResource(R.drawable.female),
                selected = selectedGender == "Female",
                onClick  = { onGenderSelected("Female") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun GenderOption(
    label    : String,
    icon     : Painter,
    selected : Boolean,
    onClick  : () -> Unit,
    modifier : Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(46.dp)
            .border(1.dp, OmBorder, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter        = icon,
            contentDescription = label,
            tint               = OmIconOrange,
            modifier           = Modifier.size(22.dp)
        )
        Text(
            text     = label,
            color    = OmTextDark,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selected,
            onClick  = onClick,
            colors   = RadioButtonDefaults.colors(
                selectedColor = OmGreenDark
            )
        )
    }
}

// ── Preview ────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalDetailsScreenPreview() {
    PersonalDetailsScreen()
}