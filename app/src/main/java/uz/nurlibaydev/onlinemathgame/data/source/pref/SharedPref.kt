package uz.nurlibaydev.onlinemathgame.data.source.pref

import android.content.Context
import android.content.SharedPreferences
import uz.nurlibaydev.onlinemathgame.utils.BooleanPreference
import uz.nurlibaydev.onlinemathgame.utils.Constants
import uz.nurlibaydev.onlinemathgame.utils.IntPreference
import uz.nurlibaydev.onlinemathgame.utils.StringPreference

class SharedPref(context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)

    var isSigned: Boolean by BooleanPreference(pref, false)

    var language: String by StringPreference(pref, "ru")

    var fullName:String by StringPreference(pref,"You're name")

    var score:Int by IntPreference(pref,0)
}