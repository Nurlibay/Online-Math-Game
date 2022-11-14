package uz.nurlibaydev.onlinemathgame.presentation.main.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenGameBinding
import uz.nurlibaydev.onlinemathgame.presentation.MainViewModel
import uz.nurlibaydev.onlinemathgame.utils.ResourceState
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick
import uz.nurlibaydev.onlinemathgame.utils.extenions.showError
import uz.nurlibaydev.onlinemathgame.utils.extenions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 13/11/2022 14:04
 */

class GameScreen : Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding()
    private val args by navArgs<GameScreenArgs>()
    private val viewModel: MainViewModel by viewModel()
    private val gameViewModel: GameViewModel by viewModel()
    private var userType: Int = 0
    private var correctAnswers: Int = 0
    private var inCorrectAnswers: Int = 0
    private var user1WinCount: Int = 0
    private var user1LostCount: Int = 0
    private var user2WinCount: Int = 0
    private var user2LostCount: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userType = args.userType
        viewModel.gameListen(args.gameData.id){
            showMessage(it)
        }
        binding.apply {
            btnFirst.onClick {
                btnClicked(btnFirst.text.toString().toInt())
                disabledButtons(false)
            }
            btnSecond.onClick {
                btnClicked(btnSecond.text.toString().toInt())
                disabledButtons(false)
            }
            btnThird.onClick {
                btnClicked(btnThird.text.toString().toInt())
                disabledButtons(false)
            }
            btnFourth.onClick {
                btnClicked(btnFourth.text.toString().toInt())
                disabledButtons(false)
            }
            tvTime.start()
        }
        setupObserver()
    }

    private fun disabledButtons(state: Boolean) {
        binding.apply {
            btnFirst.isEnabled = state
            btnSecond.isEnabled = state
            btnThird.isEnabled = state
            btnFourth.isEnabled = state
        }
    }

    private fun btnClicked(answer: Int) {
        if (args.gameData.list[gameViewModel.currentQuestion].answer == answer) {
            correctAnswers++
        } else {
            inCorrectAnswers++
        }
        gameViewModel.setAnswer(args.gameData.id, userType, correctAnswers, inCorrectAnswers)
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        viewModel.gameData.observe(viewLifecycleOwner) {
            binding.apply {
                if (userType == 2) {
                    tvCorrectAnswers.text = it.user1Corrected.toString()
                    tvIncorrectAnswers.text = it.user1InCorrected.toString()
                    tvMyCorrectAnswers.text = it.user2Corrected.toString()
                    tvMyIncorrectAnswers.text = it.user2InCorrected.toString()
                    tvOpponentUser.text = it.user1Name
                } else {
                    tvCorrectAnswers.text = it.user2Corrected.toString()
                    tvIncorrectAnswers.text = it.user2InCorrected.toString()
                    tvMyCorrectAnswers.text = it.user1Corrected.toString()
                    tvMyIncorrectAnswers.text = it.user1InCorrected.toString()
                    tvOpponentUser.text = it.user2Name
                }
            }
        }

        gameViewModel.currentQuestionStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    val data = args.gameData.list[it.data!!]
                    binding.apply {
                        tvQuestion.text = data.question
                        btnFirst.text = data.variant1.toString()
                        btnSecond.text = data.variant2.toString()
                        btnThird.text = data.variant3.toString()
                        btnFourth.text = data.variant4.toString()
                        tvQuestionNumber.text = "Question: ${it.data + 1}/10"
                        disabledButtons(true)
                    }
                }
                else -> {
                    showError(it.message?:"Message")
                }
            }
        }

        gameViewModel.openResultDialog.observe(viewLifecycleOwner) {
            if (it) {
                if(binding.tvMyCorrectAnswers.text.toString().toInt() > binding.tvCorrectAnswers.text.toString().toInt()){
                    gameViewModel.updateScore(correctAnswers * 5, ++user2WinCount, 0)
                    gameViewModel.updateScore(correctAnswers * 5, 0, ++user1LostCount)
                }
                if(binding.tvMyCorrectAnswers.text.toString().toInt() < binding.tvCorrectAnswers.text.toString().toInt()){
                    gameViewModel.updateScore(correctAnswers * 5, ++user1WinCount, 0)
                    gameViewModel.updateScore(correctAnswers * 5, 0, ++user2LostCount)
                }
                openResultDialog(binding.tvMyCorrectAnswers.text.toString(), "${correctAnswers * 5}")
            }
        }
    }

    private fun openResultDialog(moves: String, time: String) {
        val dialog = WinDialog(moves, time)
        dialog.show(requireActivity().supportFragmentManager, "DIALOG_FRAGMENT")
        dialog.closeButtonClickListener {
            findNavController().navigate(GameScreenDirections.actionGameScreenToMainScreen())
        }
    }
}