package uz.nurlibaydev.onlinemathgame.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Created by Jamshid Isoqov an 11/13/2022
@Parcelize
data class MathQuizData(
    val question: String,
    val variant1: Int,
    val variant2: Int,
    val variant3: Int,
    val variant4: Int,
    val answer: Int
):Parcelable
