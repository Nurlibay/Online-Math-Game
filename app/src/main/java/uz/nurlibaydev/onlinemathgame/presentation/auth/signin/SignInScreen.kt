package uz.nurlibaydev.onlinemathgame.presentation.auth.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenSignInBinding
import uz.nurlibaydev.onlinemathgame.utils.ResourceState
import uz.nurlibaydev.onlinemathgame.utils.extenions.showError
import uz.nurlibaydev.onlinemathgame.utils.extenions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 01:51
 */

class SignInScreen: Fragment(R.layout.screen_sign_in) {

    private val binding: ScreenSignInBinding by viewBinding()
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvSignUp.setOnClickListener {
                navController.navigate(SignInScreenDirections.actionSignInScreenToSignUpScreen())
            }
            btnSignIn.setOnClickListener {
                if(validate()){
                    viewModel.signIn(etEmail.text.toString(), etPassword.text.toString())
                }
            }
        }
        setupObserverSignInStatus()
    }

    private fun setupObserverSignInStatus() {
        viewModel.signInStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                    setLoading(true)
                }
                ResourceState.SUCCESS -> {
                    setLoading(false)
                    navController.navigate(SignInScreenDirections.actionSignInScreenToMainContainer())
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    showMessage(it.message.toString())
                }
                ResourceState.NETWORK_ERROR -> {
                    setLoading(false)
                    showError(getString(R.string.no_internet))
                }
            }
        }
    }

    private fun validate(): Boolean {
        binding.apply {
            return if (etEmail.text!!.isNotEmpty() && etPassword.text!!.isNotEmpty()
                && etPassword.length() >= 6
            ) {
                true
            } else if (etPassword.length() < 6) {
                tilPassword.error = getString(R.string.password_length_condition)
                false
            } else {
                false
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
            etEmail.isEnabled = !isLoading
            etPassword.isEnabled = !isLoading
        }
    }
}