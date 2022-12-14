package uz.nurlibaydev.onlinemathgame.presentation.main.profile

import androidx.lifecycle.LiveData

// Created by Jamshid Isoqov an 8/9/2022
interface ProfileViewModel {

    val nameLiveData: LiveData<String>

    val imageLiveData: LiveData<String>

    val backLiveData: LiveData<Unit>

    val changeNameLiveData: LiveData<Unit>

    val changeImageLiveData: LiveData<Unit>

    val aboutUsLiveData: LiveData<Unit>

    val contactLiveData: LiveData<Unit>

    val supportLiveData: LiveData<Unit>

    val logoutLiveData: LiveData<Unit>
    fun logout()

    fun changeName()

    fun changeImage()

    fun aboutClicked()

    fun helpClicked()

    fun supportClicked()

    fun setName(name: String)

    fun setImage()

    fun backClick()

    fun setImage(str: String)

}