package uz.nurlibaydev.onlinemathgame.data.source.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.nurlibaydev.onlinemathgame.data.mapper.toGameEntity
import uz.nurlibaydev.onlinemathgame.data.models.GameData
import uz.nurlibaydev.onlinemathgame.data.models.GameEntity
import uz.nurlibaydev.onlinemathgame.data.models.InvitationData
import uz.nurlibaydev.onlinemathgame.data.models.MathQuizData
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.utils.Constants
import uz.nurlibaydev.onlinemathgame.utils.GenerateMathQuiz
import uz.nurlibaydev.onlinemathgame.utils.ResultData

// Created by Jamshid Isoqov an 11/13/2022
class InvitationHelper(
    private val fireStore: FirebaseFirestore,
    private val sharedPref: SharedPref,
    private val auth: FirebaseAuth
) {

    fun uploadGameData(onSuccess: (String) -> Unit, onMessage: (String) -> Unit) {

        val quizData = ArrayList<MathQuizData>(10)

        repeat(10) {
            val quiz = GenerateMathQuiz.generate(10)
            quizData.add(quiz)
        }

        val gameEntity = GameData(list = quizData).toGameEntity()

        fireStore.collection(Constants.GAMES)
            .document(gameEntity.id)
            .set(gameEntity)
            .addOnSuccessListener {
                onSuccess.invoke(gameEntity.id)
            }.addOnFailureListener {
                onMessage.invoke(it.localizedMessage ?: "Unknown error")
            }
    }

    fun setInvitation(
        userId: String,
        gameId: String,
        onSuccess: () -> Unit,
        onMessage: (String) -> Unit
    ) {
        fireStore.collection(Constants.PLAYERS)
            .document(userId)
            .collection(Constants.INVITATION)
            .document(gameId)
            .set(InvitationData(gameId, sharedPref.fullName))
            .addOnSuccessListener {
                onSuccess.invoke()
            }.addOnFailureListener {
                onMessage.invoke(it.localizedMessage ?: "Unknown error")
            }
    }

    fun playGameListener(gameId: String): Flow<ResultData<GameData>> = callbackFlow {

        val listener = fireStore.collection(Constants.GAMES)
            .document(gameId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(ResultData.error(error))
                }
                val gameEntity = value?.toGameEntity()
                trySend(ResultData.success(gameEntity?.toGameData()!!))
            }

        awaitClose {
            listener.remove()
        }

    }

    fun invitationListener(): Flow<ResultData<InvitationData>> = callbackFlow {
        fireStore.collection(Constants.PLAYERS).document(auth.uid!!)
            .collection(Constants.INVITATION)
            .whereEqualTo("status",0).addSnapshotListener { value, error ->
                val list = value?.documents?.map {
                    it.toObject(InvitationData::class.java)
                }
                if (list!=null&&list.isNotEmpty()){
                    trySend(ResultData.success(list[0]!!))
                }
            }
        awaitClose {

        }
    }

    fun confirmInvitationStatus(
        status: Int,
        gameId: String,
        onSuccess: () -> Unit,
        onMessage: (String) -> Unit
    ) {
        fireStore.collection(Constants.PLAYERS).document(auth.uid!!)
            .collection(Constants.INVITATION)
            .document(gameId)
            .update(mapOf("status" to status))
            .addOnSuccessListener {
                onSuccess.invoke()
            }.addOnFailureListener {
                onMessage.invoke(it.localizedMessage ?: "Unknown error")
            }
    }

    fun confirmGameStatus(
        status: Int,
        gameId: String,
        onSuccess: (GameData) -> Unit,
        onMessage: (String) -> Unit
    ) {
        fireStore.collection(Constants.GAMES).document(gameId)
            .update(mapOf("status" to status))
            .addOnSuccessListener {
                if (status == 1) {
                    fireStore.collection(Constants.GAMES).document(gameId).get()
                        .addOnSuccessListener {
                            val data = it.toObject(GameEntity::class.java)
                            if (data != null) {
                                onSuccess.invoke(data.toGameData())
                            }
                        }
                }
            }.addOnFailureListener {
                onMessage.invoke(it.localizedMessage ?: "Unknown error")
            }
    }

}