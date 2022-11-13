package uz.nurlibaydev.onlinemathgame.domain

import kotlinx.coroutines.flow.Flow
import uz.nurlibaydev.onlinemathgame.data.models.GameData
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.utils.ResultData

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

    fun uploadGameData(onSuccess: (String) -> Unit, onMessage: (String) -> Unit)

    fun setInvitation(
        userId: String,
        gameId: String,
        onSuccess: () -> Unit,
        onMessage: (String) -> Unit
    )

    fun playGameListener(gameId: String): Flow<ResultData<GameData>>
}