package uz.nurlibaydev.onlinemathgame.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.NavMainDirections
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenMainBinding
import uz.nurlibaydev.onlinemathgame.presentation.MainViewModel
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 18:32
 */

class MainScreen: Fragment(R.layout.screen_main) {

    private val binding: ScreenMainBinding by viewBinding()
    private val navController: NavController by lazy { findNavController() }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.invitationLiveData.observe(this) { data ->

            AlertDialog.Builder(requireContext())
                .setTitle("Confirm")
                .setMessage("Are you really play game ${data.userName}")
                .setPositiveButton("Ok") { p0, _ ->
                    viewModel.confirmInvitationStatus(1, data.gameId, {

                    }) {

                    }
                    viewModel.confirmGameStatus(1, data.gameId, { data ->
                        p0.dismiss()
                        findNavController().navigate(MainScreenDirections.actionGlobalGameScreen(data, 2))
                    }) {

                    }
                }.create().show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            settings.onClick {
                navController.navigate(MainScreenDirections.actionMainScreenToSettingsScreen())
            }
            playNow.onClick {
                navController.navigate(MainScreenDirections.actionMainScreenToPlayersScreen())
            }
        }


    }
}