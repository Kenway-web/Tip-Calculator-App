package com.kenway.tipcalculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenway.tipcalculatorapp.ui.theme.TipCalculatorAppTheme
import java.time.temporal.TemporalAmount

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorAppTheme {
                // A surface container using the 'background' color from the theme

            }
        }
    }
}

@Composable
fun MyApp()
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

    }
}


@Composable
fun TipCalculator()
{
    Column(modifier = Modifier.fillMaxWidth()) {
        
    }
}



@Preview(showBackground = true)
@Composable
fun TotalHeader(amount: Float=0f)
{

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), color = colorResource(id = R.color.cyan),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Total Per Person",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$ $amount",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInputArea(amount:String,amountChange:(String)->Unit)
{
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = 12.dp
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalAlignment = CenterHorizontally) {

            OutlinedTextField(value =amount ,
                onValueChange = {amountChange.invoke(it)},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Enter Your Amount ")},
                keyboardOptions = KeyboardOptions(autoCorrect = true, keyboardType = KeyboardType.Number,
                imeAction =  ImeAction.Done
                ),

                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })

            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically
            ){

                Text(text = "Split", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.fillMaxWidth(.50f))

            }

        }



    }
}


@Composable
fun CustomButton(onClick:()->Unit)
{



}