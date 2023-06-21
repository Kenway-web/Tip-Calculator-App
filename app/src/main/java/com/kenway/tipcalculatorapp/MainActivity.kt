package com.kenway.tipcalculatorapp

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import java.text.DecimalFormat
import java.time.temporal.TemporalAmount

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorAppTheme {
                MyApp()

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
            TipCalculator()
    }
}


@Preview(showBackground = true)
@Composable
fun TipCalculator()
{

    // for funtionalty , creating some state

    val amount= remember {
        mutableStateOf("")
    }

    val personCounter= remember {
        mutableStateOf(1)
    }
    val tipPercentage= remember {
        mutableStateOf(0f)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TotalHeader(amount = formatToDecimalPoint(
            getTotalHeaderAmount(amount.value,
                personCounter.value,
                tipPercentage.value))
        )
        UserInputArea(amount = amount.value, amountChange = {
                                                            amount.value=it
        },personCounter=personCounter.value, onAddOrReducePerson = {
                                                                   if(it<0)
                                                                   {
                                                                       if(personCounter.value!=1)
                                                                       {
                                                                           personCounter.value--
                                                                       }
                                                                   }
                                                                   else{
                                                                       personCounter.value++
                                                                   }
        },  tipPercentage.value,{
            tipPercentage.value=it
        })

    }
}




@Composable
fun TotalHeader(amount: String)
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




@Composable
fun userInputPreview()
{
    UserInputArea(amount = "12", amountChange = {},1,{},12f,{})
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInputArea(amount:String,
                  amountChange:(String)->Unit,
                  personCounter:Int,
                  onAddOrReducePerson:(Int)->Unit,
                  tipPercentage:Float,
                  tipPercentageChange:(Float)->Unit
)
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

            if(amount.isNotBlank())
            {
                Spacer(modifier = Modifier.height(4.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically
                ){

                    Text(text = "Split", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.fillMaxWidth(.50f))

                    CustomButton(imageVector = Icons.Default.KeyboardArrowUp) {
                        onAddOrReducePerson.invoke(1)
                    }
                    Text(text = "${personCounter}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.
                        padding(horizontal = 8.dp))

                    CustomButton(imageVector = Icons.Default.KeyboardArrowDown) {
                        onAddOrReducePerson.invoke(-1)
                    }

                }


                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {

                    Text(text = "Tip1",style=MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.fillMaxWidth(.70f))
                    Text(text = "$ ${formatToDecimalPoint( getTipAmount(amount,tipPercentage))}",style=MaterialTheme.typography.body1)

                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "${formatToDecimalPoint(tipPercentage.toString())} %", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(8.dp))

                Slider(value = tipPercentage, onValueChange = {
                    tipPercentageChange.invoke(it)
                }, valueRange = 0f..100f,
                    steps = 5,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                )


            }


        }
            



    }
}



@Composable
fun CustomButton(imageVector: ImageVector,onClick:()->Unit)
{
    Card(modifier = Modifier
        .wrapContentSize()
        .padding(12.dp)
        .clickable { onClick.invoke() },
        shape = CircleShape)
    {

        Icon(imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(4.dp))


    }

}

fun getTipAmount(userAmount:String,tipPercentage: Float):String{
    return  when{
        userAmount.isEmpty()->{
            "0"
        }
        else->{
            val amount=userAmount.toFloat()
            // wrapping
            (amount*tipPercentage.div(100)).toString()
        }
    }
}

fun getTotalHeaderAmount(amount:String,personCounter:Int,tipPercentage: Float):String{
        return when{
            amount.isEmpty()->{
                "0"
            }
            else->{
                val userAmount = amount.toFloat()
                val tipAmount=userAmount*tipPercentage.div(100)
                val perheadAmount=(userAmount+tipAmount).div(personCounter)
                perheadAmount.toString()
            }
        }
}

fun formatToDecimalPoint(str:String):String
{
       return if(str.isEmpty()) ""
        else {
            val format=DecimalFormat("################.##")
            format.format(str.toFloat())
        }
}