package uz.nurlibaydev.onlinemathgame.presentation.main.players

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenPlayersBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.addVertDivider

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 02:05
 */

class PlayersScreen: Fragment(R.layout.screen_players) {

    private val binding: ScreenPlayersBinding by viewBinding()
    private val adapter by lazy { PlayersAdapter() }
    private val viewModel: PlayerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvPlayers.adapter = adapter
            rvPlayers.addVertDivider(requireContext())

        }
    }
}