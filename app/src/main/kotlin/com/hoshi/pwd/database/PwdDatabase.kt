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
    exportSchema = false // 当 exportSchema 设置为 true 时，Room 会在数据库初始化或升级时，将数据库的结构（表、字段、索引等）以 JSON 格式导出，并保存在指定的目录。
)
abstract class PwdDatabase : RoomDatabase() {

    /**
     * 密码实体的 Dao
     */
    abstract fun passwordDao(): PasswordDao

}