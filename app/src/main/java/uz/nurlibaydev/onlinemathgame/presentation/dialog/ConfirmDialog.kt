package uz.nurlibaydev.onlinemathgame.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import uz.nurlibaydev.onlinemathgame.databinding.DialogConfirmBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.config

// Created by Jamshid Isoqov an 10/12/2022
class ConfirmDialog(ctx: Context, private val message: String = "") : Dialog(ctx) {

    private var confirmClickListener: (() -> Unit)? = null
    private var cancelClickListener: (() -> Unit)? = null

    private lateinit var binding: DialogConfirmBinding

    fun setConfirmClickListener(block: () -> Unit) {
        confirmClickListener = block
    }

    fun setCancelClickListener(block: () -> Unit) {
        cancelClickListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogConfirmBinding.inflate(layoutInflater)
        config(viewBinding = binding)
        binding.btnCancel.setOnClickListener {
            cancelClickListener?.invoke()
            dismiss()
        }
        if (message.isNotEmpty()) {
            binding.tvMessage.text = message
        }
        binding.btnConfirm.setOnClickListener {
            confirmClickListener?.invoke()
            dismiss()
        }
    }

}