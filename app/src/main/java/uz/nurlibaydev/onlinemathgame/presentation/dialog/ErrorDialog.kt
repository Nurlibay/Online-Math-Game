package uz.nurlibaydev.onlinemathgame.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import uz.nurlibaydev.onlinemathgame.databinding.DialogErrorBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.config

// Created by Jamshid Isoqov an 10/12/2022
class ErrorDialog(ctx: Context, private val message: String) : Dialog(ctx) {

    private lateinit var binding: DialogErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogErrorBinding.inflate(layoutInflater)
        config(binding)
        binding.tvErrorMessage.text = message
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

}