package uz.nurlibaydev.onlinemathgame.presentation.main.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 22:24
 */

class PlayerViewModel(
    private val mainRepository: MainRepository
): ViewModel() {
    private var _players: MutableLiveData<Resource<List<PlayerData>>> = MutableLiveData()
    val playerStatus: LiveData<Resource<List<PlayerData>>> get() = _players

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
}