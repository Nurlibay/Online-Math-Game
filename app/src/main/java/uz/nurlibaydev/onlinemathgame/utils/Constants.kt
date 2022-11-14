package uz.nurlibaydev.onlinemathgame.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import uz.nurlibaydev.onlinemathgame.presentation.MainActivity

object Constants {

    fun goToPlayMarket(mainActivity: MainActivity) {
        try {
            mainActivity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=uz.gita.task_app")
                )
            )
        } catch (e: ActivityNotFoundException) {
            mainActivity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=uz.gita.task_app")
                )
            )
        }
    }

    const val GAMES = "game"
    const val SHARED_PREFS = "shared_prefs"
    const val PLAYERS = "players"
    const val INVITATION = "invitation"
}