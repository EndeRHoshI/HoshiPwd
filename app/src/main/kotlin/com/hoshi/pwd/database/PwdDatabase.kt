package com.hoshi.pwd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoshi.pwd.database.dao.PasswordDao
import com.hoshi.pwd.database.entities.Password

/**
 * Created by lv.qx on 2024/6/25
 */
@Database(
    entities = [Password::class],
    version = 1,
)
abstract class PwdDatabase : RoomDatabase() {

    /**
     * 密码实体的 Dao
     */
    abstract fun passwordDao(): PasswordDao

}