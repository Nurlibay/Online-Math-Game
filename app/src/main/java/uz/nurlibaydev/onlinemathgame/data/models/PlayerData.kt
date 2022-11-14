package uz.nurlibaydev.onlinemathgame.data.models

import uz.nurlibaydev.onlinemathgame.utils.extenions.getCurrentDate

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 22:04
 */

data class PlayerData(
    var id: String = "",
    var name: String = "",
    var img: String = "",
    var email: String = "",
    var score: Int = 0,
    var date:String = getCurrentDate()
)