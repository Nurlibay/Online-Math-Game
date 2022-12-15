package uz.nurlibaydev.onlinemathgame.presentation.main.statistika

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenStatisticsBinding
import uz.nurlibaydev.onlinemathgame.presentation.dialog.ProgressDialog
import uz.nurlibaydev.onlinemathgame.presentation.main.players.PlayerViewModel
import uz.nurlibaydev.onlinemathgame.utils.ResourceState
import uz.nurlibaydev.onlinemathgame.utils.extenions.addVertDivider
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick
import uz.nurlibaydev.onlinemathgame.utils.extenions.showError

/**
 *  Created by Nurlibay Koshkinbaev on 14/11/2022 21:57
 */

class StatisticsScreen: Fragment(R.layout.screen_statistics) {

    private val binding: ScreenStatisticsBinding by viewBinding()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { StatisticsAdapter() }
    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var dialog: ProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = ProgressDialog(requireContext(), "Progress")
        viewModel.getAllPlayers()
        binding.apply {
            rvPlayersStatistics.adapter = adapter
            rvPlayersStatistics.addVertDivider(requireContext())
            iconBack.onClick {
                findNavController().popBackStack()
            }
        }
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
            if (isLoading) dialog.show()
            else dialog.cancel()
        }
    }
}