package uz.nurlibaydev.onlinemathgame.domain

import uz.nurlibaydev.onlinemathgame.data.models.PlayerData

interface MainRepository {
    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    fun signUp(
        fullName: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    fun addPlayerToDb(
        fullName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    fun getAllPlayers(
        onSuccess: (players: List<PlayerData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )
}