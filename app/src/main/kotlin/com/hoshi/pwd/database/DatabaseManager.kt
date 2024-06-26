package com.hoshi.pwd.database

import android.app.Application
import androidx.room.Room

/**
 * 数据库的管理类
 */
object DatabaseManager {

    private const val PWD_DB_NAME = "pwd.db"
    private lateinit var application: Application

    val db: PwdDatabase by lazy {
        Room.databaseBuilder(application.applicationContext, PwdDatabase::class.java, PWD_DB_NAME)
            //.allowMainThreadQueries() // 允许在主线程调用
            /*.setQueryCallback({ sqlQuery, bindArgs -> // 数据库调试
                VLog.d("Database", "SQL Query: $sqlQuery SQL Args: $bindArgs")
            }, Executors.newSingleThreadExecutor())*/
            .build()
    }

    fun init(context: Application) {
        application = context
    }

}