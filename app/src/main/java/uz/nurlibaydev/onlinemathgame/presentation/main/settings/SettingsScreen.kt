package uz.nurlibaydev.onlinemathgame.presentation.main.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenSettingsBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 21:13
 */

class SettingsScreen: Fragment(R.layout.screen_settings) {

    private val binding: ScreenSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvLang.text = resources.getString(R.string.lang_text)
            containerSelectLanguage.onClick {
                val dialog = LanguageDialog()
                dialog.show(requireActivity().supportFragmentManager, "LanguageDialog")
            }
            iconBack.onClick {
                findNavController().popBackStack()
            }
        }
    }
}