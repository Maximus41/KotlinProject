package com.demo.kotlintestproj

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){

    private var dialog : AlertDialog? = null

    protected fun handleNoInternet(context: Context) {
        if(dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
        showAlertDialog(
            context,
            getString(R.string.str_no_internet),
            getString(R.string.str_no_internet_message),
            getString(R.string.str_ok),
            R.drawable.ic_error
        )
    }

    protected fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        buttonText: String,
        icon: Int
    ) {
        dialog = AlertDialog.Builder(context).setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText) { dialog, which ->
                dialog.dismiss()
            }
            .setIcon(icon)
            .show()
    }
}