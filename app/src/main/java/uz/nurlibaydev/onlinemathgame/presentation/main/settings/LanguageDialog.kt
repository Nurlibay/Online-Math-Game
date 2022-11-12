package uz.nurlibaydev.onlinemathgame.presentation.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.databinding.DialogChooseLanguageBinding
import uz.nurlibaydev.onlinemathgame.presentation.MainActivity

class LanguageDialog : DialogFragment() {

    private val binding: DialogChooseLanguageBinding by viewBinding()
    private val pref: SharedPref by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireDialog().window?.setBackgroundDrawableResource(R.drawable.shape_dialog)
        return inflater.inflate(R.layout.dialog_choose_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvEng.setOnClickListener {
                pref.language = "en"
                dismiss()
                (requireActivity() as MainActivity).setNewLocale()
                dismiss()
            }
            tvRu.setOnClickListener {
                pref.language = "ru"
                dismiss()
                (requireActivity() as MainActivity).setNewLocale()
                dismiss()
            }
        }
    }
}