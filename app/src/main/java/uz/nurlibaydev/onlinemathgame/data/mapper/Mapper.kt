package uz.nurlibaydev.onlinemathgame.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import uz.nurlibaydev.onlinemathgame.data.models.GameEntity

// Created by Jamshid Isoqov an 11/13/2022


fun DocumentSnapshot.toGameEntity(): GameEntity {
    val id: String = this.id
    val status: Int = this["status"].toString().toInt()
    val user1Corrected: Int = this["user1Corrected"].toString().toInt()
    val user1InCorrected: Int = this["user1InCorrected"].toString().toInt()
    val user2Corrected: Int = this["user2Corrected"].toString().toInt()
    val user2InCorrected: Int = this["user2InCorrected"].toString().toInt()
    val questions: String = this["questions"].toString()
    return GameEntity(
        id,
        status,
        user1Corrected,
        user1InCorrected,
        user2Corrected,
        user2InCorrected,
        questions
    )
}