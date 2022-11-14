package uz.nurlibaydev.onlinemathgame.presentation.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val sharedPreference: SharedPref,
    private val repository: MainRepository,
) : ViewModel(), ProfileViewModel {

    override val nameLiveData: MutableLiveData<String> =
        MutableLiveData(sharedPreference.fullName)

    override val imageLiveData: MutableLiveData<String> =
        MutableLiveData(sharedPreference.fullName)

    override val backLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val changeNameLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val changeImageLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val aboutUsLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val contactLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val supportLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun changeName() {
        changeNameLiveData.postValue(Unit)
    }

    override fun changeImage() {
        changeImageLiveData.postValue(Unit)
    }

    override fun aboutClicked() {
        aboutUsLiveData.postValue(Unit)
    }

    override fun helpClicked() {
        contactLiveData.postValue(Unit)
    }

    override fun supportClicked() {
        supportLiveData.postValue(Unit)
    }

    override fun setName(name: String) {
        viewModelScope.launch {
            sharedPreference.fullName = name
            nameLiveData.value = (name)
        }
    }

    override fun setImage() {

    }

    override fun setImage(str: String) {
        viewModelScope.launch {
            if (hasConnection()) {
                val def = repository.uploadImage(str)
                sharedPreference.image = def
                imageLiveData.postValue(def)
                repository.updateUser()
            } else {
                //TODO no internet connection
            }
        }
    }

    override fun backClick() {
        backLiveData.postValue(Unit)
    }
}