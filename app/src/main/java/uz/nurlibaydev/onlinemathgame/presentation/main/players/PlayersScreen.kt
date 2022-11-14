package uz.nurlibaydev.onlinemathgame.presentation.main.players

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenPlayersBinding
import uz.nurlibaydev.onlinemathgame.presentation.dialog.ProgressDialog
import uz.nurlibaydev.onlinemathgame.utils.ResourceState
import uz.nurlibaydev.onlinemathgame.utils.extenions.*

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 02:05
 */

class PlayersScreen : Fragment(R.layout.screen_players) {

    private val binding: ScreenPlayersBinding by viewBinding()
    private val adapter by lazy { PlayersAdapter() }
    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = ProgressDialog(requireContext(), "Progress")
        viewModel.openConfirmDialog.observe(this) { player ->
            showConfirmDialog("How to really confirmed?", {
                viewModel.sendInvitation(player)
            }) {

            }
        }

        viewModel.openGame.observe(this) {
            when (it.status) {
                ResourceState.LOADING -> {
                    setLoading(true)
                }
                ResourceState.SUCCESS -> {
                    setLoading(false)
                    findNavController().navigate(
                        PlayersScreenDirections.actionPlayersScreenToGameScreen(
                            it.data!!,
                            1
                        )
                    )
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    showError(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    setLoading(false)
                    showError(getString(R.string.no_internet))
                }
            }
        }
    }

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





        viewModel.invitationStatusListener.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                     dialog.show()
                }
                ResourceState.SUCCESS -> {
                }
                ResourceState.ERROR -> {
                    dialog.cancel()
                    showMessage(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    dialog.cancel()
                    showError(getString(R.string.no_internet))
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
                    showError(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    setLoading(false)
                    showError(getString(R.string.no_internet))
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading){
                dialog.show()
            }else{
                dialog.cancel()
            }
        }
    }

}