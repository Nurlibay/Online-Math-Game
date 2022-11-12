package uz.nurlibaydev.onlinemathgame.domain

import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.data.source.helper.AuthHelper
import uz.nurlibaydev.onlinemathgame.data.source.helper.PlayerHelper

class MainRepositoryImpl(
    private val authHelper: AuthHelper,
    private val playerHelper: PlayerHelper
) : MainRepository {
    override fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        authHelper.signIn(email, password, onSuccess, onFailure)
    }

    override fun signUp(
        fullName: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        authHelper.signUp(fullName, email, password, onSuccess, onFailure)
    }

    override fun addPlayerToDb(
        fullName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        playerHelper.addPlayerToDb(fullName, onSuccess, onFailure)
    }

    override fun getAllPlayers(
        onSuccess: (players: List<PlayerData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        playerHelper.getAllPlayers(onSuccess, onFailure)
    }
}