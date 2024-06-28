package com.hoshi.pwd.data

import com.hoshi.pwd.database.entities.Password

/**
 * Created by lv.qx on 2024/6/25
 */
object PasswordRepository {

    suspend fun queryAll() = PasswordLocalSource.queryAll()

    suspend fun insert(password: Password) = PasswordLocalSource.insert(password)

    suspend fun delete(password: Password) = PasswordLocalSource.delete(password)

    suspend fun deleteAll() = PasswordLocalSource.deleteAll()

    suspend fun isEmpty() = PasswordLocalSource.queryAll().isEmpty()

}