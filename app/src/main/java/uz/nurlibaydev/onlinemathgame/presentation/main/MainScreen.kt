package uz.nurlibaydev.onlinemathgame.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenMainBinding
import uz.nurlibaydev.onlinemathgame.presentation.MainViewModel
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick
import uz.nurlibaydev.onlinemathgame.utils.extenions.showConfirmDialog
import uz.nurlibaydev.onlinemathgame.utils.extenions.showError
import uz.nurlibaydev.onlinemathgame.utils.extenions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 18:32
 */

class MainScreen : Fragment(R.layout.screen_main) {

    private val binding: ScreenMainBinding by viewBinding()
    private val navController: NavController by lazy { findNavController() }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.invitationLiveData.observe(this) { data ->

            showConfirmDialog("Are you really play game ${data.userName}", {

                viewModel.confirmInvitationStatus(1, data.gameId, {
                    showMessage("Invitation accepted", Toast.LENGTH_SHORT)
                }) {
                    showMessage("Game cancelled", Toast.LENGTH_SHORT)
                }

                viewModel.confirmGameStatus(1, data.gameId, { data ->
                    findNavController().navigate(
                        MainScreenDirections.actionGlobalGameScreen(
                            data,
                            2
                        )
                    )
                }) {
                    showError(it)
                }
            }) {
                viewModel.confirmGameStatus(-1, data.gameId, { _ ->
                    showMessage("Game cancelled", Toast.LENGTH_SHORT)
                }) {
                    showError(it)
                }

                viewModel.confirmInvitationStatus(-1, data.gameId, {
                    showMessage("Game cancelled", Toast.LENGTH_SHORT)
                }) {
                    showMessage("Game cancelled", Toast.LENGTH_SHORT)
                }


            }
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

        binding.profileContainer.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToProfileScreen())
        }

        viewModel.nameLiveData.observe(viewLifecycleOwner, nameObserver)
        viewModel.imageLiveData.observe(viewLifecycleOwner, imageObserver)


    }

    override fun onResume() {
        super.onResume()
        viewModel.getNameImage()
    }

    private val nameObserver = Observer<String> {
        binding.tvName.text = it
    }

    private val imageObserver = Observer<String> {
        Glide.with(requireContext())
            .load(it)
            .placeholder(R.drawable.musk)
            .into(binding.imageProfile)

    }
}