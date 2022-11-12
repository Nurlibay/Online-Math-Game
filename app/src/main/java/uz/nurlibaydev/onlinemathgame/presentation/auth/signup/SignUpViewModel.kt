package uz.nurlibaydev.onlinemathgame.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.utils.Resource

class SignUpViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private var _signUp: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val signUpStatus: LiveData<Resource<Any?>> get() = _signUp

    fun signUp(fullName: String, email: String, password: String) {
        _signUp.value = Resource.loading()
        mainRepository.signUp(fullName, email, password,
            {
                _signUp.value = Resource.success(null)
            }, {
                _signUp.value = Resource.error(it)
            }
        )
    }

    private var _player: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val playerStatus: LiveData<Resource<Any?>> get() = _player

    fun addPlayerToDb(fullName: String) {
        _signUp.value = Resource.loading()
        mainRepository.addPlayerToDb(fullName,
            {
                _signUp.value = Resource.success(null)
            }, {
                _signUp.value = Resource.error(it)
            }
        )
    }
}