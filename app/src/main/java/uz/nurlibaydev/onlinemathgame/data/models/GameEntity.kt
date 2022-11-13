package uz.nurlibaydev.onlinemathgame.data.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Created by Jamshid Isoqov an 11/13/2022
data class GameEntity(
    val id: String="",
    val status: Int = 0,
    val user1Corrected: Int = 0,
    val user1InCorrected: Int = 0,
    val user2Corrected: Int = 0,
    val user2InCorrected: Int = 0,
    val questions: String=""
) {
    fun toGameData(): GameData {
        val type = object : TypeToken<List<MathQuizData>>() {}.type
        val list = Gson().fromJson<List<MathQuizData>>(questions, type)
        return GameData(
            id,
            status,
            user1Corrected,
            user1InCorrected,
            user2Corrected,
            user2InCorrected,
            list
        )
    }
}
