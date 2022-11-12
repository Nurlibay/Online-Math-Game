package uz.nurlibaydev.onlinemathgame.data.models

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 22:04
 */

data class PlayerData(
    var id: String = "",
    var name: String = "",
    var img: String = "",
    var score: Int = 0,
    var numberWin: Int = 0,
    var numberLost: Int = 0
)