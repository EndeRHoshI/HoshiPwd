package com.hoshi.pwd.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.hoshi.core.utils.HLog
import com.hoshi.pwd.data.PasswordRepository
import com.hoshi.pwd.database.entities.Password

/**
 * Created by lv.qx on 2024/6/25
 */
class PasswordViewModel : BaseViewModel() {

    val list = mutableStateOf<List<Password>>(listOf())

    fun queryAll() {
        launchIO { queryAllInner() }
    }

    private suspend fun queryAllInner() {
        val result = PasswordRepository.queryAll()
        val listSize = result.size
        HLog.d("查询到密码的数量为 $listSize 条")
        list.value = result
    }

    fun insert() {
        launchIO {
            PasswordRepository.insert(Password(category = "生活", platform = "hahaha", account = "hoshi", password = "123456"))
            queryAllInner()
        }
    }

    fun deleteAll() {
        launchIO {
            PasswordRepository.deleteAll()
            queryAllInner()
        }
    }

}