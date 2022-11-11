package uz.nurlibaydev.onlinemathgame.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.databinding.ScreenMainContainerBinding

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 02:01
 */

class MainContainer : Fragment(R.layout.screen_main_container){

    //private val sharedPref: SharedPref by inject()
    private val binding: ScreenMainContainerBinding by viewBinding()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //sharedPref.isSigned = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container_view)
        binding.bottomNavMenu.setupWithNavController(navController)
    }

}