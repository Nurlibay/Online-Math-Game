package uz.nurlibaydev.onlinemathgame.data.source.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.utils.Constants

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 23:55
 */

class PlayerHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    fun addPlayerToDb(fullName: String, onSuccess: () -> Unit, onFailure: (msg: String?) -> Unit) {
        val playerData = PlayerData(auth.currentUser!!.uid, fullName, auth.currentUser!!.email!!, 0, 0, 0)
        db.collection(Constants.PLAYERS).document(playerData.id).set(playerData)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getAllPlayers(onSuccess: (players: List<PlayerData>) -> Unit, onFailure: (msg: String?) -> Unit){
        db.collection(Constants.PLAYERS).get()
            .addOnSuccessListener {
                val players = it.documents.map { player ->
                    player.toObject(PlayerData::class.java)!!
                }
                onSuccess.invoke(players)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}