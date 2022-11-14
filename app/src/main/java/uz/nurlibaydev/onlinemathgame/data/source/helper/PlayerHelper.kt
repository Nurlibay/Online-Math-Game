package uz.nurlibaydev.onlinemathgame.data.source.helper

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CompletableDeferred
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.utils.Constants
import java.util.*

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 23:55
 */

class PlayerHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val sharedPref: SharedPref
) {
    fun addPlayerToDb(fullName: String, onSuccess: () -> Unit, onFailure: (msg: String?) -> Unit) {
        val playerData =
            PlayerData(auth.currentUser!!.uid, "", fullName, auth.currentUser!!.email!!, 0)
        db.collection(Constants.PLAYERS).document(playerData.id).set(playerData)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getAllPlayers(
        onSuccess: (players: List<PlayerData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
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

    fun updateScore(score: Int, onSuccess: (Int) -> Unit, onFailure: (String) -> Unit) {
        val lastScore = sharedPref.score + score
        db.collection(Constants.PLAYERS).document(auth.uid!!).update(
            mapOf(
                "score" to lastScore
            )
        ).addOnSuccessListener {
            onSuccess.invoke(lastScore)
        }.addOnFailureListener { error ->
            onFailure.invoke(error.localizedMessage ?: "Unknown error")
        }
    }

    fun updateUser() {
        db.collection(Constants.PLAYERS).document(auth.uid!!)
            .update(mapOf("image" to sharedPref.image))
    }

    suspend fun uploadImage(path: String): String {

        val fileName = UUID.randomUUID().toString() + ".jpg"


        val deferred = CompletableDeferred<String>()
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

        refStorage.putFile(Uri.parse(path)).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                deferred.complete(imageUrl)
            }
        }
            .addOnFailureListener { e ->
                deferred.completeExceptionally(e)
            }

        return deferred.await()
    }
}