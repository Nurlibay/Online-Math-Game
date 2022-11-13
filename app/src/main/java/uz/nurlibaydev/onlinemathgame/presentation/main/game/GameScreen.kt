package uz.nurlibaydev.onlinemathgame.presentation.main.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.ScreenGameBinding

/**
 *  Created by Nurlibay Koshkinbaev on 13/11/2022 14:04
 */

class GameScreen: Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}