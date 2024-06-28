package com.hoshi.pwd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * 密码实体类
 * Created by lv.qx on 2024/6/25
 */
@Entity(tableName = Password.TABLE_NAME)
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 数据库自增 id
    @Expose val category: String, // 类别
    @Expose val platform: String, // 平台
    @Expose val account: String, // 账号
    @Expose val password: String, // 密码
) {

    companion object {
        const val TABLE_NAME = "Password"
    }

}