package com.hoshi.pwd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * 密码实体类
 * Created by lv.qx on 2024/6/25
 *
 * id 和分类这两个字段不需要参与序列化，因为平时直接修改文档时，不需要手动写 id，而分类会写在第二级里面，不需要重复写了
 */
@Entity(tableName = Password.TABLE_NAME)
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 数据库自增 id
    var category: String, // 分类
    @Expose var platform: String, // 平台
    @Expose var account: String, // 账号
    @Expose var password: String, // 密码
) {

    companion object {
        const val TABLE_NAME = "Password"
    }

}