package com.example.androidskeleton.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.example.androidskeleton.ui.widgets.ProgressDialog

open class BaseActivity: AppCompatActivity(){
    private val progressDialog by lazy {
        ProgressDialog(this)
    }

    fun showProgressDialog(cancelable: Boolean = false) {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        progressDialog.setCancelable(cancelable)
        progressDialog.show()
    }

    fun dismissProgressDialog() = progressDialog.dismiss()
}