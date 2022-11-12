package uz.nurlibaydev.onlinemathgame.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 19:04
 */

@SuppressLint("CustomSplashScreen")
class SplashScreen: Fragment(R.layout.screen_splash) {

    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = SharedPref(requireContext())
        lifecycleScope.launch {
            delay(1500)
            if (sharedPref.isSigned) {
                navController.navigate(SplashScreenDirections.actionSplashScreenToMainContainer())
            } else {
                navController.navigate(SplashScreenDirections.actionSplashScreenToSignInScreen())
            }
        }
    }
}