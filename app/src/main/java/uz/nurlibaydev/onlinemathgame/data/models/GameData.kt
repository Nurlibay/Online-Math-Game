package uz.nurlibaydev.onlinemathgame.data.models

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.util.*

// Created by Jamshid Isoqov an 11/13/2022
@Parcelize
data class GameData(
    val id: String = UUID.randomUUID().toString(),
    val status: Int = 0,
    val user1Corrected: Int = 0,
    val user1InCorrected: Int = 0,
    val user2Corrected: Int = 0,
    val user2InCorrected: Int = 0,
    val user1Name: String = "",
    val user2Name: String = "",
    val list: List<MathQuizData>
):Parcelable {
    fun toGameEntity(): GameEntity {
        val type = object : TypeToken<List<MathQuizData>>() {}.type
        val jsonString = Gson().toJson(list, type)
        return GameEntity(
            id,
            status,
            user1Corrected,
            user1InCorrected,
            user2Corrected,
            user2InCorrected,
            user1Name,
            user2Name,
            jsonString
        )
    }
}
