package com.example.kotlincomposesample

import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlincomposesample.ui.theme.KotlinComposeSampleTheme
import com.example.kotlincomposesample.ui.theme.OmGreenDark
import com.example.kotlincomposesample.ui.theme.OmGreenLight
import com.example.kotlincomposesample.ui.theme.White
import com.example.kotlincomposesample.ui.theme.dp11
import com.example.kotlincomposesample.ui.theme.dp24
import com.example.kotlincomposesample.ui.theme.dp32
import com.example.kotlincomposesample.ui.theme.dp52
import com.example.kotlincomposesample.ui.theme.sp24
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            Row(modifier = Modifier.fillMaxSize(0.9f)
//                .width(200.dp)
//                .height(100.dp)
//                .background(Color.Red),
//               verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceAround
//
//            )
//            {
//                Text("Hello Android")
//                Text("Hello 2")
//            }

//            Column(
//                modifier = Modifier.fillMaxSize()
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .background(Color.Yellow),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.SpaceEvenly
//
//            ) {
//                Text("Hello Android")
//                Text("Hello 2")
//                Text("Hello 3")
//            }

//            Box(Modifier.fillMaxWidth().fillMaxHeight(),
//                contentAlignment = Alignment.Center) {
//                ShowImageCard(
//                    painterResource(R.drawable.d1),
//                    "This is sample image",
//                    "This is sample image"
//                )

//                Text(
//                    text = buildAnnotatedString{
//                        withStyle(
//                            style = SpanStyle(color = Color.Red)
//                        ){
//                            append("J")
//                        }
//                        append("etpack")
//                        withStyle(
//                            style = SpanStyle(color = Color.Red)
//                        ){
//                            append("C")
//                        }
//                        append("ompose")
//                    },
//                    modifier = Modifier
//                        .background(Color.Yellow),
//                    color = Color.Black,
//                    fontSize = 20.sp,
//                    fontStyle = FontStyle.Italic,
//                    fontFamily = FontFamily.SansSerif,
//                    fontWeight = FontWeight.ExtraBold,
//                    textAlign = TextAlign.Center,
//                    textDecoration = TextDecoration.Underline
//                )
           // }
                //PersonalDetailsScreen()
            Greeting()
        }
    }
}

@Composable
fun ColumnBox(modifier: Modifier,
              updateColor: (Color) -> Unit){
        Box(modifier = modifier.background(Color.Red).clickable{
            updateColor(Color(Random.nextInt()))
        }
            )
}

@Composable
fun Greeting() {
    var showPersonalDetailsScreen by remember { mutableStateOf(false) }
    var showBloodPressureDetailsScreen by remember { mutableStateOf(false) }
    
    if (showPersonalDetailsScreen){
        BackHandler{ showPersonalDetailsScreen = false}
        PersonalDetailsScreen (onBack = {showPersonalDetailsScreen = false})
    }

    else if (showBloodPressureDetailsScreen){
        BackHandler {  showBloodPressureDetailsScreen = false}
        BloodPressureDetails(onBack = { showBloodPressureDetailsScreen = false})
    }
else
    {
        Box(modifier = Modifier.fillMaxSize()
        .background(White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dp32),
            verticalArrangement = Arrangement.spacedBy(dp24)
        ) {
            Button(
                onClick = { showPersonalDetailsScreen = true },
                shape = RoundedCornerShape(dp32),
                modifier = Modifier.fillMaxWidth()
                    .height(dp52)
                    .padding(horizontal = dp11)


            ) {
                Text(
                    text = "Show Personal Details",
                    color = OmGreenLight,
                    fontSize = sp24
                )

            }

            Button(
                onClick = {
                    showBloodPressureDetailsScreen = true
                },
                modifier = Modifier.fillMaxWidth()
                    .height(dp52)
                    .padding(horizontal = dp11),


                ) {
                Text(
                    text = "Add Weight Details",
                    color = OmGreenDark,
                    fontSize = sp24,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    }
}

@Composable
fun ShowImageCard(
    painter: Painter,
    desc: String,
    text: String,
    modifier: Modifier = Modifier
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(modifier = Modifier.height(200.dp)){
            Image(painter = painter, contentDescription = desc, contentScale = ContentScale.Crop)
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Red
                        ), startY = 300f
                    )
                ))
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), contentAlignment = Alignment.BottomStart){
                Text(text = text, style = TextStyle(color = Color.Black, fontSize = 16.sp))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        Greeting()
    //PersonalDetailsScreen()
}