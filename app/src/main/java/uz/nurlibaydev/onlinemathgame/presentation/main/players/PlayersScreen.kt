package uz.nurlibaydev.onlinemathgame.presentation.main.players

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenPlayersBinding
import uz.nurlibaydev.onlinemathgame.utils.ResourceState
import uz.nurlibaydev.onlinemathgame.utils.extenions.addVertDivider
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick
import uz.nurlibaydev.onlinemathgame.utils.extenions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 02:05
 */

class PlayersScreen : Fragment(R.layout.screen_players) {

    private val binding: ScreenPlayersBinding by viewBinding()
    private val adapter by lazy { PlayersAdapter() }
    private val viewModel: PlayerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvPlayers.adapter = adapter
            rvPlayers.addVertDivider(requireContext())
            iconBack.onClick {
                findNavController().popBackStack()
            }
        }
        adapter.setOnItemClickListener {
            viewModel.playerItemClick(it)
        }



        viewModel.openConfirmDialog.observe(viewLifecycleOwner) { player ->

            AlertDialog.Builder(requireContext()).setTitle("Confirm")
                .setMessage("How to really confirmed?").setPositiveButton(
                    "Confirm"
                ) { p0, _ ->
                    p0.dismiss()
                    viewModel.sendInvitation(player)
                }.create().show()

        }

        viewModel.invitationStatusListener.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                    setLoading(true)
                }
                ResourceState.SUCCESS -> {
                    //invitation ketti
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    showMessage(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    setLoading(false)
                    showMessage(getString(R.string.no_internet))
                }
            }

        }

        viewModel.openGame.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                    setLoading(true)
                }
                ResourceState.SUCCESS -> {
                    setLoading(false)
                    findNavController().navigate(PlayersScreenDirections.actionPlayersScreenToGameScreen(it.data!!, 1))
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    showMessage(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    setLoading(false)
                    showMessage(getString(R.string.no_internet))
                }
            }
        }

        viewModel.getAllPlayers()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.playerStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                    setLoading(true)
                }
                ResourceState.SUCCESS -> {
                    setLoading(false)
                    adapter.submitList(it.data)
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    showMessage(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    setLoading(false)
                    showMessage(getString(R.string.no_internet))
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
        }
    }

}