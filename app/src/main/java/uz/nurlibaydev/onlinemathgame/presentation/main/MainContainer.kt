package uz.nurlibaydev.onlinemathgame.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 02:01
 */

class MainContainer : Fragment(R.layout.screen_main_container) {

    private val sharedPref: SharedPref by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref.isSigned = true
    }
}