package uz.nurlibaydev.onlinemathgame.data.source.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.utils.Constants

class AuthHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun signUp(
        fullName: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

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
}