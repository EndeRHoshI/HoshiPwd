package com.hoshi.pwd.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.hoshi.pwd.database.entities.Password

/**
 * Created by lv.qx on 2024/6/25
 */
@Dao
interface PasswordDao : BaseDao<Password> {

    @Query("SELECT * FROM ${Password.TABLE_NAME}")
    suspend fun queryAll(): List<Password>

    @Query("DELETE FROM ${Password.TABLE_NAME}")
    suspend fun deleteAll()

}