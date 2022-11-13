package uz.nurlibaydev.onlinemathgame.presentation.main.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.nurlibaydev.onlinemathgame.data.models.GameData
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 22:24
 */

class PlayerViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private var _players: MutableLiveData<Resource<List<PlayerData>>> = MutableLiveData()
    val playerStatus: LiveData<Resource<List<PlayerData>>> get() = _players

    private var _openConfirmDialog: MutableLiveData<PlayerData> = MutableLiveData()
    val openConfirmDialog: LiveData<PlayerData> get() = _openConfirmDialog

    private var _invitationStatusListener: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val invitationStatusListener: LiveData<Resource<Unit>> get() = _invitationStatusListener

    private var _openGame: MutableLiveData<Resource<GameData>> = MutableLiveData()
    val openGame: LiveData<Resource<GameData>> get() = _openGame

    fun getAllPlayers() {
        _players.value = Resource.loading()
        mainRepository.getAllPlayers(
            {
                _players.value = Resource.success(it)
            }, {
                _players.value = Resource.error(it)
            }
        )
    }

    fun playerItemClick(playerData: PlayerData) {
        _openConfirmDialog.value = playerData
    }

    fun sendInvitation(playerData: PlayerData) {
        _invitationStatusListener.value = Resource.loading()
        mainRepository.uploadGameData({ gameId ->
            mainRepository.setInvitation(playerData.id, gameId, {
                _invitationStatusListener.value = Resource.success(Unit)
            }) { error ->
                _invitationStatusListener.value = Resource.error(error)
            }
            mainRepository.playGameListener(gameId)
                .onEach {
                    _openGame.value = Resource.loading()
                    it.onSuccess { data ->
                        if (data.status == 1)
                            _openGame.value = Resource.success(data)
                    }.onError { error ->
                        _openGame.value = Resource.error(error.localizedMessage!!)
                    }
                }.launchIn(viewModelScope)
        }) {
            //TODO error
        }
    }

}