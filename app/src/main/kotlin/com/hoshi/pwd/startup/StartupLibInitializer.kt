package com.hoshi.pwd.startup

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.hjq.toast.ToastUtils

/**
 * 统一处理初始化
 * Created by lv.qx on 2024/6/25
 */
class StartupLibInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // MMKV.initialize(context)
        // HttpHelper.instance.init(OkHttpProcessor())
        // HookUtils.hookInstrumentation()
        if (context is Application) {
            ToastUtils.init(context)
        }
    }

    override fun dependencies() = mutableListOf<Class<out Initializer<*>>>()

}