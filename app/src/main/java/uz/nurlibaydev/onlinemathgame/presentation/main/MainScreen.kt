package uz.nurlibaydev.onlinemathgame.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenMainBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 18:32
 */

class MainScreen: Fragment(R.layout.screen_main) {

    private val binding: ScreenMainBinding by viewBinding()
    private val navController: NavController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            settings.onClick {
                navController.navigate(MainScreenDirections.actionMainScreenToSettingsScreen())
            }
            playNow.onClick {

            }
        }
    }
}