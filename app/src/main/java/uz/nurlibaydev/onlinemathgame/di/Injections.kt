package uz.nurlibaydev.onlinemathgame.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.nurlibaydev.onlinemathgame.data.source.helper.AuthHelper
import uz.nurlibaydev.onlinemathgame.data.source.helper.InvitationHelper
import uz.nurlibaydev.onlinemathgame.data.source.helper.PlayerHelper
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.domain.MainRepositoryImpl
import uz.nurlibaydev.onlinemathgame.presentation.MainViewModel
import uz.nurlibaydev.onlinemathgame.presentation.auth.signin.SignInViewModel
import uz.nurlibaydev.onlinemathgame.presentation.auth.signup.SignUpViewModel
import uz.nurlibaydev.onlinemathgame.presentation.main.players.PlayerViewModel

val dataModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    single { AuthHelper(get()) }
    single { PlayerHelper(get(), get()) }
    single { InvitationHelper(get(),get(),get()) }
}

val sharedPrefModule = module {
    single { SharedPref(androidApplication().applicationContext) }
}

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get(), get(),get()) }
}

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { PlayerViewModel(get()) }
    viewModel { MainViewModel(get()) }
}