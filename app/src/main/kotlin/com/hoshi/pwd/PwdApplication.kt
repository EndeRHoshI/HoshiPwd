package com.hoshi.pwd

import android.app.Application
import com.hoshi.pwd.database.DatabaseManager

class PwdApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this) // 初始化数据库
    }

}