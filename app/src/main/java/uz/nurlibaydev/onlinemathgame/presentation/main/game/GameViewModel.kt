package uz.nurlibaydev.onlinemathgame.presentation.main.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 13/11/2022 20:34
 */

class GameViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var _currentQuestion: MutableLiveData<Resource<Int>> = MutableLiveData()
    val currentQuestionStatus: LiveData<Resource<Int>> = _currentQuestion

    var currentQuestion = 0

    init {
        _currentQuestion.value = Resource.success(currentQuestion)
    }

    private var _openResultDialog: MutableLiveData<Boolean> = MutableLiveData()
    val openResultDialog: LiveData<Boolean> = _openResultDialog

    fun setAnswer(
        gameId: String,
        userType: Int,
        correctAnswers: Int,
        inCorrectAnswers: Int,
    ) {
        mainRepository.setAnswers(gameId, userType, correctAnswers, inCorrectAnswers,
            {
                _currentQuestion.value = Resource.success(++currentQuestion)
                if(currentQuestion == 10) _openResultDialog.value = true
            },
            {
                _currentQuestion.value = Resource.error("Unknown error occured!")
            })
    }
}