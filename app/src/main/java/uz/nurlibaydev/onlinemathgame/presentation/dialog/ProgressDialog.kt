package uz.nurlibaydev.onlinemathgame.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import uz.nurlibaydev.onlinemathgame.databinding.DialogLoadingBinding
import uz.nurlibaydev.onlinemathgame.databinding.DialogMessageBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.config

// Created by Jamshid Isoqov an 10/12/2022
class ProgressDialog(ctx: Context,var message: String) : Dialog(ctx) {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogLoadingBinding.inflate(layoutInflater)
        config(binding)
        setCancelable(false)
        binding.tvTitle.text = message
    }
}