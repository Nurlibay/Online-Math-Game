package uz.nurlibaydev.onlinemathgame.utils

import uz.nurlibaydev.onlinemathgame.data.models.MathQuizData
import java.lang.Math.*
import kotlin.math.pow

// Created by Jamshid Isoqov an 8/22/2022
object GenerateMathQuiz {
    fun generate(level: Int): MathQuizData {
        val quizData = generateMath(level)

        val answer = eval(quizData).toInt()

        val list = ArrayList<Int>(4)

        var i = 0

        if (answer >= -1 && answer <= 1) {
            list.addAll(listOf(-1, 0, 1, 2))
        } else {
            while (i <= 1) {
                val num =
                    answer + (-1.0).pow(i.toDouble())
                        .toInt() * (random() * answer * 2).toInt()
                if (!list.contains(num) && num != answer) {
                    list.add(num)
                    i++
                }
            }
            i = 0
            while (i <= 1) {
                val num =
                    answer + (-1.0).pow(i.toDouble())
                        .toInt() * (random() * answer * 4).toInt()
                if (!list.contains(num) && num != answer) {
                    list.add(num)
                    i++
                }
            }
        }
        list.shuffled()

        val random = (random() * 4).toInt()
        list[random] = answer

        return MathQuizData(quizData, list[0], list[1], list[2], list[3], answer)
    }

    private fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.code)) x += parseTerm()
                    else if (eat('-'.code)) x -= parseTerm()
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor()
                    else if (eat('/'.code)) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return +parseFactor()
                if (eat('-'.code)) return -parseFactor()
                var x: Double
                val startPos = pos
                if (eat('('.code)) { // parentheses
                    x = parseExpression()
                    if (!eat(')'.code)) throw RuntimeException("Missing ')'")
                } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) { // numbers
                    while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.code && ch <= 'z'.code) { // functions
                    while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                    val func = str.substring(startPos, pos)
                    if (eat('('.code)) {
                        x = parseExpression()
                        if (!eat(')'.code)) throw RuntimeException("Missing ')' after argument to $func")
                    } else {
                        x = parseFactor()
                    }
                    x =
                        when (func) {
                            "sqrt" -> sqrt(x)
                            "sin" -> sin(toRadians(x))
                            "cos" -> cos(
                                toRadians(x)
                            )
                            "tan" -> tan(toRadians(x))
                            else -> throw RuntimeException(
                                "Unknown function: $func"
                            )
                        }
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.code)) x = x.pow(parseFactor()) // exponentiation
                return x
            }
        }.parse()
    }

    private fun generateMath(level: Int): String {

        val operation = (random() * 4 + 2).toInt().coerceAtMost(ceil((level / 5.0)).toInt())

        val numbers = ArrayList<Int>(operation + 1)

        for (i in 0..operation) {
            val num = (random() * 50 + 1).toInt()
            numbers.add(num)
        }
        val operations = ArrayList<Int>(operation)

        for (i in 0 until operation) {
            val num = (random() * 4).toInt()
            operations.add(num)
        }
        val builder = StringBuilder()

        for (i in operations.indices) {
            builder.append(numbers[i])
            builder.append(getOperation(operations[i]))
        }
        builder.append(numbers[numbers.lastIndex])
        return builder.toString()
    }

    private fun getOperation(num: Int): Char = when (num) {
        0 -> '+'
        1 -> '-'
        2 -> '*'
        else -> '/'
    }
}