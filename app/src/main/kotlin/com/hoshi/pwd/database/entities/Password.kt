package com.hoshi.pwd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 密码实体类
 * Created by lv.qx on 2024/6/25
 */
@Entity(tableName = Password.TABLE_NAME)
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // 类别
    val platform: String, // 平台
    val account: String, // 账号
    val password: String, // 密码
) {

    companion object {
        const val TABLE_NAME = "Password"
    }

}