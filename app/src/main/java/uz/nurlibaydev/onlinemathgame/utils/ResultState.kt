package uz.nurlibaydev.onlinemathgame.utils
import androidx.lifecycle.LiveData

interface ResultState<T> {
    val loadingState: LiveData<Boolean>
    val errorState: LiveData<String>
    val networkState: LiveData<String>
    val successState: LiveData<T>
}