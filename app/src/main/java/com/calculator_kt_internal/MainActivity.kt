package com.calculator_kt_internal

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.regex.PatternSyntaxException

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Calculator() {
    val textValue = remember { mutableStateOf("") }
    val scrollState = remember { ScrollState(0) }
    LaunchedEffect(scrollState.maxValue) { scrollState.animateScrollTo(scrollState.maxValue) }
    textValue.value = getInputAsString(input)
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp * 0.71f // 75% of screen width
        val screenHeight = configuration.screenHeightDp * 0.59f // 80% of screen height
        val txtFldOffset = configuration.screenWidthDp * 0.63f // 63% of screen width
        Column(modifier = Modifier.fillMaxHeight(1f / 4f).fillMaxWidth()) {
            TextField(
                value = textValue.value,
                onValueChange = {},
                singleLine = true,
                modifier =
                Modifier.offset(0.dp, y = txtFldOffset.dp)
                    .height(140.dp)
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                enabled = false,
                textStyle =
                TextStyle(
                    fontSize =
                    (if (textValue.value == "Invalid expression") 38.sp
                    else if (textValue.value == "Invalid expression occurrence two") 20.sp
                    else 70.sp),
                    textAlign = TextAlign.Right
                ),
                colors =
                TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    disabledTextColor =
                    (if (
                        textValue.value == "Invalid expression" ||
                        textValue.value == "Error" ||
                        textValue.value == "Invalid expression occurrence two"
                    )
                        Color.Red
                    else Color.Black),
                    disabledLabelColor = Color.Black
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight(1f / 3f).fillMaxWidth(),
        ) {
            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Button(
                    onClick = { equals() },
                    modifier =
                    Modifier.offset(x = screenWidth.dp, y = screenHeight.dp)
                        .fillMaxWidth(1f / 5f)
                        .fillMaxHeight(1f / 2f),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0, 255, 0))
                ) {
                    Text(
                        text = "=",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 40.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(1f)) {
            Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                Button(
                    onClick = { clear() },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "C", color = Color(0, 255, 0), fontSize = 35.sp)
                }
                Button(
                    onClick = { receiveInput("/") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "÷",
                        modifier = Modifier.offset(0.dp, (-3).dp),
                        color = Color(0, 255, 0),
                        fontSize = 43.sp
                    )
                }
                IconButton(
                    onClick = { receiveInput("*") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close_24px),
                        contentDescription = "multiplication icon",
                        modifier = Modifier.size(40.dp),
                        tint = Color(0, 255, 0)
                    )
                }
                IconButton(
                    onClick = { delete() },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.backspace_24px),
                        contentDescription = "backspace icon",
                        modifier = Modifier.size(40.dp),
                        tint = Color(0, 255, 0)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                Button(
                    onClick = { receiveInput("7") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "7", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("8") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "8", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("9") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "9", color = Color.Black, fontSize = 30.sp)
                }
                IconButton(
                    onClick = { receiveInput("-") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "minus icon",
                        modifier = Modifier.size(45.dp),
                        tint = Color(0, 255, 0)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                Button(
                    onClick = { receiveInput("4") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "4", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("5") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "5", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("6") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "6", color = Color.Black, fontSize = 30.sp)
                }
                IconButton(
                    onClick = { receiveInput("+") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add_24px),
                        contentDescription = "plus icon",
                        modifier = Modifier.size(40.dp).offset(0.dp, (-2).dp),
                        tint = Color(0, 255, 0)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(3f / 4f).wrapContentHeight()) {
                Button(
                    onClick = { receiveInput("1") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "1", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("2") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "2", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("3") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "3", color = Color.Black, fontSize = 30.sp)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(3f / 4f).wrapContentHeight()) {
                Button(
                    onClick = { modulus() },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "%", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput("0") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "0", color = Color.Black, fontSize = 30.sp)
                }
                Button(
                    onClick = { receiveInput(".") },
                    modifier = Modifier.weight(1f).height(78.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = ".", color = Color.Black, fontSize = 30.sp)
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent { Calculator() }
    }
}

/* Define a map that holds the calculator operators each assigned an integer indicating its
precedence in a mathematical expression */
val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2, "*-" to 3)

// Check if a string is an operator or a negative sign
fun isOperator(token: String): Boolean {
    val it = precedence.containsKey(token)

    // Check if the token exists in the precedence map or if it's a single "-"
    return it || (token == "-")
}

// Check if a string itself is an operator
fun isSpecialCharacter(c: String): Boolean {
    val operators = "([*\\-+*/()])"

    // Check if the character is in the set of operators
    return operators.contains(c)
}

// Function to parse a number from a string to a double
fun parseNumber(token: String): Double {
    return token.toDoubleOrNull() ?: throw NumberFormatException("Invalid number: $token")
}

// Add whitespace anywhere there is an operator thereby separating numbers and operators
fun addWhitespaceAroundOperators(input: String): String {
    val pattern = """(\*\-|\+|-|\*|/|\(|\))"""

    return try {
        var result = input

        for ((index, char) in input.withIndex()) {
            if (char.isDigit()) {
                result =
                    input.substring(0, index) + // Retain the string before the first number
                            input.substring(index).replace(Regex(pattern)) { " ${it.value} " }
                break
            }
        }
        result
    } catch (e: PatternSyntaxException) {
        println("Error compiling regular expression: ${e.message}")
        input
    }
}

// Read the values from the 'input' list and write them to a single string variable
fun getInputAsString(values: List<String>): String {
    val result = StringBuilder()

    for (value in values) {
        result.append(value)
    }

    return result.toString()
}

// Check if a string contains any operator
fun containsSpecialCharacters(str: String): Boolean {
    val pattern = "([*\\-+/()])" // Regular expression pattern to match operators

    val regex = Regex(pattern)
    return regex.containsMatchIn(str)
}

// Check if the elements of a vector contain an operator
fun foundOperatorFunc(vec: List<String>): Boolean {
    for (str in vec) {
        if (containsSpecialCharacters(str)) {
            return true // Found a string with an operator
        }
    }
    return false // No string with an operator found in the list
}

// Check if any of the elements of a vector is a period
fun periodInInputFunc(vec: List<String>): Boolean {
    for (str in vec) {
        if ('.' in str) {
            return true // Found a string with a period
        }
    }
    return false // No string with a period found in the list
}

// Check whether there is a period in the last number occurring after an operator
fun periodInLastOperand(input: List<String>): Boolean {
    var lastOperatorIndex = -1

    // Find the index of the last operator in the list
    for (i in input.indices) {
        if (containsSpecialCharacters(input[i])) {
            lastOperatorIndex = i
        }
    }

    // if an operator is found, check for a period in the substring from that index to the end
    if (lastOperatorIndex != -1) {
        for (i in lastOperatorIndex until input.size) {
            if (input[i] == ".") {
                return true
            }
        }
    }

    return false
}

// Remove all zeros occurring after a decimal point as they have no relevance
fun removeTrailingZeros(number: Double): String {
    return String.format("%.15f", number).trimEnd('0').removeSuffix(".")
}

// Evaluate an expression based on the provided operators
fun performOperation(
    lclOperatorStack: MutableList<String>,
    lclValueStack: MutableList<Double>
): Pair<MutableList<String>, MutableList<Double>> {
    val op = lclOperatorStack.removeAt(lclOperatorStack.size - 1)
    val operand2 = lclValueStack.removeAt(lclValueStack.size - 1)
    val operand1 = lclValueStack.removeAt(lclValueStack.size - 1)
    val result =
        when (op[0]) {
            '*' -> {
                if (op.length > 1 && op[1] == '-') {
                    operand1 * (operand2 * -1)
                } else {
                    operand1 * operand2
                }
            }
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '/' -> operand1 / operand2
            else -> 0.0
        }
    lclValueStack.add(result)
    return Pair(lclOperatorStack, lclValueStack)
}

// A function to receive and prepare a string for evaluation as a mathematical expression
fun calculateInput(expression: String): Pair<Double, String> {
    val tokens = expression.split("\\s+".toRegex()) // Split the expression into tokens
    val operatorStack = mutableListOf<String>()
    val valueStack = mutableListOf<Double>()
    val limitOne = 4
    var counterOne = 0
    val limitTwo = 10
    var counterTwo = 0
    val limitThree = 10
    var counterThree = 0

    for (token in tokens) {
        when {
            token == "(" -> operatorStack.add(token)
            token == ")" -> {
                if (counterTwo >= 10) {
                    operatorStack.clear()
                    valueStack.clear()
                }
                while (
                    operatorStack.isNotEmpty() &&
                    operatorStack.last() != "(" &&
                    counterTwo <= limitTwo
                ) {
                    val (updatedOperatorStack, updatedValueStack) =
                        performOperation(operatorStack, valueStack)
                    operatorStack.addAll(updatedOperatorStack)
                    valueStack.addAll(updatedValueStack)
                    counterTwo++
                }
                operatorStack.removeLast() // Pop "("
            }
            isOperator(token) -> {
                if (
                    token == "-" &&
                    (tokens.indexOf(token) == 0 ||
                            isOperator(tokens[tokens.indexOf(token) - 1]) ||
                            tokens[tokens.indexOf(token) - 1] == "(")
                ) {
                    valueStack.add(-1.0)
                    operatorStack.add("*")
                } else {
                    if (counterThree >= 10) {
                        operatorStack.clear()
                        valueStack.clear()
                    }
                    while (
                        operatorStack.isNotEmpty() &&
                        precedence[operatorStack.last()]!! >= precedence[token]!! &&
                        counterThree <= limitThree
                    ) {
                        val (updatedOperatorStack, updatedValueStack) =
                            performOperation(operatorStack, valueStack)
                        operatorStack.addAll(updatedOperatorStack)
                        valueStack.addAll(updatedValueStack)
                        counterThree++
                    }
                    operatorStack.add(token)
                }
            }
            else -> {
                try {
                    val value = parseNumber(token)
                    valueStack.add(value)
                } catch (e: NumberFormatException) {
                    return Pair(0.0, "Invalid expression")
                }
            }
        }
    }

    if (counterOne >= 4) {
        operatorStack.clear()
        valueStack.clear()
    }
    while (operatorStack.isNotEmpty() && counterOne <= limitOne) {
        val operatorStacks = performOperation(operatorStack, valueStack)
        operatorStack.addAll(operatorStacks.first)
        valueStack.addAll(operatorStacks.second)
        counterOne++
    }

    if (valueStack.size >= 1) {
        return Pair(valueStack.last(), "")
    }

    return Pair(0.0, "Invalid expression occurrence two")
}

var input: MutableList<String> =
    mutableStateListOf() // Declare a mutable list that will hold values and error messages
var lastElementOperator: Boolean? = null
var multiAndMinus: Boolean? = null
var isOperator = false
val operatorPattern = "([*\\-|+/()])".toRegex() // Define the regular expression pattern
var operatorPosition = -1
var resultDisplayed: Boolean? = null

// Receive input and do some validation before inserting it into a mutable list
fun receiveInput(value: String) {
    try {
        var key: String? = null
        when (value) {
            "/" -> key = "divide"
            "*" -> key = "multiply"
            "-" -> key = "minus"
            "+" -> key = "add"
            "." -> key = "period"
        }
        // Check whether the button pressed is an operator and the input box was displaying a result
        if (
            (key == "divide" || key == "multiply" || key == "minus" || key == "add") &&
            resultDisplayed == true
        ) {
            isOperator = true
            resultDisplayed = false
        }
        var lastElement = ""
        var secondLastElement = -1
        if (input.isNotEmpty()) {
            lastElement = input.last() // get the last element in the input list
        }
        if (input.size > 2) { // get the index of the second last element
            secondLastElement = input.size - 2
        }
        var secondLastMultiply = false
        // check if the second last element is a multiplication symbol
        if (secondLastElement != -1 && input.getOrNull(secondLastElement) == "*") {
            secondLastMultiply = true
        }
        // If the period button has been clicked and there is no number preceding it, append a zero
        // before the period
        if (
            key == "period" && (input.size < 1 || (input.isNotEmpty() && isOperator(input.last())))
        ) {
            input.add("0")
        }
        if (
            lastElement == "Invalid expression" || lastElement == "Error"
        ) { // if "Invalid expression" or "Error" is being shown and another button is clicked,
            // first clear the input box
            input.clear()
        }
        // In the case that the program was expecting a negative number as the first operand but
        // other operators are provided instead, clear the input box
        if (
            lastElement == "-" &&
            input.size == 1 &&
            (key == "divide" || key == "multiply" || key == "add")
        ) {
            input.clear()
        }
        if ((key == "divide" || key == "multiply" || key == "add") && input.size < 1) {
            /*Prohibit an operator from being provided as the first value in the input
            box*/
            input.clear()
        } else if (
            lastElement == "*" && value == "-"
        ) { // Allow the minus operator to follow the multiplication operator
            multiAndMinus = true
            input.add(value)
        } else if (
            secondLastMultiply &&
            lastElement == "-" &&
            (key == "divide" || key == "multiply" || key == "add")
        ) {
            /*If any operator comes immediately after a multiplication sign and a minus sign, remove
            the latter two and insert that operator into the input box */
            if (input.size >= 2) {
                input.removeLast()
                input.removeLast()
                input.add(value)
            }
        } else if (
            (key == "divide" || key == "multiply" || key == "add" || key == "minus") &&
            input.isNotEmpty() &&
            isOperator(input.last())
        ) { // Prevent two operators from following each other
            try {
                input.removeAt(input.size - 1)
            } catch (e: NoSuchElementException) {
                System.err.println("NoSuchElementException at: receiveInput()")
                return
            }
            input.add(value)
        } else if (resultDisplayed == true) {
            /* If any number is clicked while the input box is displaying an
            answer, erase and start inserting values afresh */
            input.clear()
            input.add(value)
            resultDisplayed = false
        } else if (
            ((periodInInputFunc(input) && !foundOperatorFunc(input)) ||
                    periodInLastOperand(input)) && key == "period"
        ) { // Prevent any operand from having more than one period
            val newInput = input
            input = newInput
        } else { // Add the value of the clicked button to the input list
            input.add(value)
        }
        return
    } catch (e: IndexOutOfBoundsException) {
        System.err.println(e.message)
        input.clear()
        return
    } catch (e: IllegalArgumentException) {
        System.err.println(e.message)
        input.clear()
        return
    } catch (e: Exception) {
        System.err.println(e.message)
        input.clear()
        return
    }
}

// If the percentage button has been clicked
fun modulus() {
    try {
        input.forEachIndexed { index, item ->
            // Check if there is an operator in the input item and if present, get its position
            if (operatorPattern.containsMatchIn(item)) {
                operatorPosition = index
            }
        }
        val indexToSlice = operatorPosition + 1
        // If the input vector is empty redirect to the html page and show an empty input box
        if (input.isEmpty()) {
            input.clear()
            return
        }
        if (input.joinToString("") == "0") {
            input.clear()
            input.add("0")
            resultDisplayed = true
            return
        }
        val slicedInput = input.subList(indexToSlice, input.size)
        val implodeToPercentage = slicedInput.joinToString("")
        var toPercentage: Double
        try {
            toPercentage = implodeToPercentage.toDouble()
        } catch (e: NumberFormatException) {
            println("Error: Cannot convert implodeToPercentage to double - ${e.message}")
            input.clear()
            input.add("Error")
            return
        }
        toPercentage /= 100
        val operandWithoutModulus: MutableList<String> = mutableListOf()
        if (indexToSlice > 0) {
            operandWithoutModulus.addAll(input.subList(0, indexToSlice))
        }
        val firstPattern = "([*\\-+/()])" // Pattern for a plus or minus
        val firstOperatorPattern = Regex(firstPattern)
        val secondPattern = "([*/()])" // Pattern for division or multiplication
        val secondOperatorPattern = Regex(secondPattern)
        var plusInInput = false
        var divisionInInput = false
        var negativeInInput = false
        for (element in input) {
            if (multiAndMinus == true) {
                negativeInInput = true
            } else if (secondOperatorPattern.containsMatchIn(element)) {
                divisionInInput = true
            } else if (firstOperatorPattern.containsMatchIn(element)) {
                plusInInput = true
            }
        }
        if (plusInInput) { // if the modulus expression contains a plus or minus
            val operandWithoutOperator = input.subList(0, operatorPosition)
            val firstOperandResultPair =
                calculateInput(
                    addWhitespaceAroundOperators(operandWithoutOperator.joinToString(""))
                )
            var currentValueOne: Double = -1.0
            if (firstOperandResultPair.second.isEmpty()) {
                currentValueOne = firstOperandResultPair.first
                println("firstOperandResult: ${firstOperandResultPair.first}")
            } else {
                System.err.println(
                    "firstOperandResultPair.second: ${firstOperandResultPair.second}"
                )
                input.clear()
                input.add("Error")
                return
            }
            val implodedOperand = currentValueOne
            val operandWithModulus = implodedOperand * toPercentage
            operandWithoutModulus.add(operandWithModulus.toString())
            val operandWithoutModulusStr = operandWithoutModulus.joinToString("")
            val currentValueSpc = addWhitespaceAroundOperators(operandWithoutModulusStr)
            val currentValuePair = calculateInput(currentValueSpc)
            var currentValue: Double = -1.0
            if (currentValuePair.second.isEmpty()) {
                currentValue = currentValuePair.first
                println("Result: ${currentValuePair.first}")
            } else {
                System.err.println(currentValuePair.second)
            }
            input.clear()
            input.add(removeTrailingZeros(currentValue))
            resultDisplayed = true
        } else if (
            divisionInInput
        ) { // if the modulus expression contains a multiplication or division symbol
            operandWithoutModulus.add(toPercentage.toString())
            val operandWithoutModulusStr = operandWithoutModulus.joinToString("")
            val currentValueSpc = addWhitespaceAroundOperators(operandWithoutModulusStr)
            val currentValuePair = calculateInput(currentValueSpc)
            var currentValue: Double = -1.0
            if (currentValuePair.second.isEmpty()) {
                currentValue = currentValuePair.first
                println("Result: ${currentValuePair.first}")
            } else {
                System.err.println(currentValuePair.second)
            }
            input.clear()
            input.add(removeTrailingZeros(currentValue))
            resultDisplayed = true
        } else if (
            negativeInInput
        ) { // if the modulus expression contains a multiplication sign followed by a minus sign
            operandWithoutModulus.add(toPercentage.toString())
            val operandWithoutModulusStr = operandWithoutModulus.joinToString("")
            val currentValueSpc = addWhitespaceAroundOperators(operandWithoutModulusStr)
            val currentValuePair = calculateInput(currentValueSpc)
            var currentValue: Double = -1.0
            if (currentValuePair.second.isEmpty()) {
                currentValue = currentValuePair.first
                println("Result: ${currentValuePair.first}")
            } else {
                System.err.println(currentValuePair.second)
            }
            input.clear()
            input.add(removeTrailingZeros(currentValue))
            resultDisplayed = true
            multiAndMinus = false
        } else {
            var currentValueFlt: Double = -1.0
            try {
                currentValueFlt = getInputAsString(input).toDouble()
            } catch (e: NumberFormatException) {
                println("Error: Cannot convert 'getInputAsString(input)' to double - ${e.message}")
            }
            currentValueFlt /= 100
            input.clear()
            input.add(removeTrailingZeros(currentValueFlt))
            resultDisplayed = true
        }
        return
    } catch (e: IndexOutOfBoundsException) {
        System.err.println(e.message)
        input.clear()
        return
    } catch (e: IllegalArgumentException) {
        System.err.println(e.message)
        input.clear()
        return
    } catch (e: Exception) {
        System.err.println(e.message)
        input.clear()
        return
    }
}

// if the equals button has been clicked
fun equals() {
    try {
        if (input.isEmpty()) {
            return
        }
        // If the expression ends with an operator display an 'Error' message in the input box
        if (isSpecialCharacter(input.last())) {
            input.clear()
            input.add("Error")
            return
        }
        var inputConv = ""
        for (str in input) {
            inputConv += str
        }
        println(inputConv)
        val currentValueSpc = addWhitespaceAroundOperators(inputConv)
        val currentValuePair = calculateInput(currentValueSpc)
        var currentValue: Double
        if (currentValuePair.second.isEmpty()) {
            currentValue = currentValuePair.first
            println("Result: ${currentValuePair.first}")
        } else {
            System.err.println(currentValuePair.second)
            input.clear()
            input.add(currentValuePair.second)
            resultDisplayed = true
            isOperator = false
            return
        }
        val currentValueStr = currentValue.toString()
        if (currentValueStr.isNotEmpty() && currentValueStr[0] == '.') {
            val currentValueWithZero = "0$currentValueStr"
            val currentValueFlt = currentValueWithZero.toDoubleOrNull()
            if (currentValueFlt != null) {
                currentValue = currentValueFlt
            } else {
                println("Error converting currentValueWithZero to double")
            }
        }
        input.clear()
        input.add(removeTrailingZeros(currentValue))
        resultDisplayed = true
        isOperator = false
        return
    } catch (e: IndexOutOfBoundsException) {
        System.err.println(e.message)
        input.clear()
        return
    } catch (e: IllegalArgumentException) {
        System.err.println(e.message)
        input.clear()
        return
    } catch (e: Exception) {
        System.err.println(e.message)
        input.clear()
        return
    }
}

// If the C button is clicked
fun clear() {
    input.clear()
    if (lastElementOperator == true) {
        lastElementOperator = false
    }
    resultDisplayed = false
    isOperator = false
    multiAndMinus = false
    return
}

// If the delete button has been clicked
fun delete() {
    if (input.size > 0) {
        if (lastElementOperator == true) {
            lastElementOperator = false
        }
        if (lastElementOperator == true && multiAndMinus == true) {
            multiAndMinus = false
        }
        input.removeAt(input.size - 1)
    }
    resultDisplayed = false
    isOperator = false
    return
}
