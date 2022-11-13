package uz.nurlibaydev.onlinemathgame.presentation.main.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.DialogResultBinding

class WinDialog(private val user1Score: String, private val user2Score: String) :
    DialogFragment(R.layout.dialog_result) {

    private lateinit var binding: DialogResultBinding

    private var nextButtonClick: ((Unit) -> Unit?)? = null
    fun nextButtonClickListener(block: ((Unit) -> Unit?)?) {
        nextButtonClick = block
    }

    private var homeButtonClick: ((Unit) -> Unit?)? = null
    fun homeButtonClickListener(block: ((Unit) -> Unit?)?) {
        homeButtonClick = block
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogResultBinding.bind(view).apply {
            requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireDialog().setCancelable(false)
            tvMyScore.text = user1Score
            tvOpponentScore.text = user2Score
            requireDialog().show()

            btnNext.setOnClickListener {
                nextButtonClick?.invoke(Unit)
                dialog?.dismiss()
            }

            btnHome.setOnClickListener {
                homeButtonClick?.invoke(Unit)
            }
        }
    }
}