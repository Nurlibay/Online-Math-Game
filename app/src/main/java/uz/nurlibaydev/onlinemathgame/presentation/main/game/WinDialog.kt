package uz.nurlibaydev.onlinemathgame.presentation.main.game

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.databinding.DialogResultBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick

class WinDialog(private val user1Score: String, private val user2Score: String) : DialogFragment(R.layout.dialog_result) {

    private lateinit var binding: DialogResultBinding

    private var closeButtonClick: () -> Unit = {}
    fun closeButtonClickListener(block: () -> Unit) {
        closeButtonClick = block
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogResultBinding.bind(view).apply {
            requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireDialog().setCancelable(false)
            tvMyScore.text = "Result :${user1Score}/10"
            tvOpponentScore.text = "You're score : $user2Score"
            requireDialog().show()

            btnClose.onClick {
                closeButtonClick.invoke()
                requireDialog().dismiss()
            }
        }
    }
}