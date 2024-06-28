package com.hoshi.pwd.data

import com.hoshi.pwd.database.DatabaseManager
import com.hoshi.pwd.database.entities.Password

/**
 * Created by lv.qx on 2024/6/25
 */
object PasswordLocalSource {

    // Dao
    private val passwordDao by lazy { DatabaseManager.db.passwordDao() }

    // 方法调用
    suspend fun queryAll() = passwordDao.queryAll()

    suspend fun insert(password: Password) = passwordDao.insert(password)

    suspend fun insert(passwordList: List<Password>) = passwordDao.insert(passwordList)

    suspend fun delete(password: Password) = passwordDao.delete(password)

    suspend fun deleteAll() = passwordDao.deleteAll()

}