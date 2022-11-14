package uz.nurlibaydev.onlinemathgame.utils.extenions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.nurlibaydev.onlinemathgame.presentation.dialog.ConfirmDialog
import uz.nurlibaydev.onlinemathgame.presentation.dialog.ErrorDialog
import uz.nurlibaydev.onlinemathgame.presentation.dialog.MessageDialog

fun Fragment.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    if (context != null) {
        Toast.makeText(context, message, duration).show()
    }
}

fun Activity.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showConfirmDialog(message: String, block: () -> Unit,cancelListener:()->Unit = {}) {
    val dialog = ConfirmDialog(requireContext(), message)
    dialog.setConfirmClickListener {
        block.invoke()
    }
    dialog.setCancelClickListener {
        cancelListener.invoke()
    }
    dialog.show()
}

fun Fragment.showError(message: String) {
    val dialog = ErrorDialog(requireContext(), message)
    dialog.show()
}

fun Fragment.showMessage(message: String) {
    val dialog = MessageDialog(requireContext(), message)
    dialog.show()

}