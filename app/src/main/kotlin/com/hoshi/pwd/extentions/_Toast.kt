package com.hoshi.pwd.extentions

import android.view.Gravity
import com.hjq.toast.ToastUtils

@JvmOverloads
fun showToast(message: String, gravity: Int = Gravity.CENTER) {
    ToastUtils.setGravity(gravity)
    ToastUtils.show(message)
}

@JvmOverloads
fun showToast(resId: Int, gravity: Int = Gravity.CENTER) {
    ToastUtils.setGravity(gravity)
    ToastUtils.show(resId)
}