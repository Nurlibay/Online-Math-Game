package uz.nurlibaydev.onlinemathgame.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.onlinemathgame.data.models.GameData
import uz.nurlibaydev.onlinemathgame.data.models.InvitationData
import uz.nurlibaydev.onlinemathgame.domain.MainRepository

// Created by Jamshid Isoqov an 11/13/2022
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _invitationLiveData: MutableLiveData<InvitationData> = MutableLiveData()
    val invitationLiveData: LiveData<InvitationData> get() = _invitationLiveData

    private val _gameData: MutableLiveData<GameData> = MutableLiveData()
    val gameData: LiveData<GameData> get() = _gameData

    init {
        viewModelScope.launch {
            repository.invitationListener().collectLatest {
                it.onSuccess { data ->
                    _invitationLiveData.value = data
                }
            }
        }
    }

    fun confirmInvitationStatus(
        status: Int, gameId: String, onSuccess: () -> Unit, onMessage: (String) -> Unit
    ) {
        repository.confirmInvitationStatus(status, gameId, {
            onSuccess.invoke()
        }) {
            onMessage.invoke(it)
        }
    }

    fun confirmGameStatus(
        status: Int, gameId: String, onSuccess: (GameData) -> Unit, onMessage: (String) -> Unit
    ) {
        repository.confirmGameStatus(status, gameId, {
            onSuccess.invoke(it)
        }) {
            onMessage.invoke(it)
        }
    }

    fun gameListen(gameId: String) {
        viewModelScope.launch {
            repository.playGameListener(gameId).collectLatest {
                it.onSuccess { gameData ->
                    _gameData.value = gameData
                }
            }
        }
    }
}