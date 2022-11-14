package uz.nurlibaydev.onlinemathgame.domain

import kotlinx.coroutines.flow.Flow
import uz.nurlibaydev.onlinemathgame.data.models.GameData
import uz.nurlibaydev.onlinemathgame.data.models.InvitationData
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.data.source.helper.AuthHelper
import uz.nurlibaydev.onlinemathgame.data.source.helper.InvitationHelper
import uz.nurlibaydev.onlinemathgame.data.source.helper.PlayerHelper
import uz.nurlibaydev.onlinemathgame.utils.ResultData

class MainRepositoryImpl(
    private val authHelper: AuthHelper,
    private val playerHelper: PlayerHelper,
    private val invitationHelper: InvitationHelper
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

    override fun uploadGameData(opponentName: String, onSuccess: (String) -> Unit, onMessage: (String) -> Unit) {
        invitationHelper.uploadGameData(opponentName, onSuccess, onMessage)
    }

    override fun setInvitation(
        userId: String,
        gameId: String,
        onSuccess: () -> Unit,
        onMessage: (String) -> Unit
    ) {
        invitationHelper.setInvitation(userId, gameId, onSuccess, onMessage)
    }

    override fun playGameListener(gameId: String): Flow<ResultData<GameData>> =
        invitationHelper.playGameListener(gameId)

    override fun invitationListener(): Flow<ResultData<InvitationData>> = invitationHelper.invitationListener()

    override fun confirmInvitationStatus(
        status: Int,
        gameId: String,
        onSuccess: () -> Unit,
        onMessage: (String) -> Unit
    ) = invitationHelper.confirmInvitationStatus(status, gameId, onSuccess, onMessage)

    override fun confirmGameStatus(
        status: Int,
        gameId: String,
        onSuccess: (GameData) -> Unit,
        onMessage: (String) -> Unit
    ) = invitationHelper.confirmGameStatus(status, gameId, onSuccess, onMessage)

    override fun setAnswers(
        gameId: String,
        userType: Int,
        correctAnswerCount: Int,
        inCorrectAnswerCount: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        invitationHelper.setAnswers(gameId, userType, correctAnswerCount, inCorrectAnswerCount, onSuccess, onFailure)
    }

    override fun updateScore(score: Int, onSuccess: (Int) -> Unit, onFailure: (String) -> Unit)  = playerHelper.updateScore(score, onSuccess, onFailure)
}