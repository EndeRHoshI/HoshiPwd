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
    val categoryFilter = mutableStateOf("")

    /**
     * 查询所有数据
     * @param categoryFilter 分类过滤
     */
    fun queryAll(categoryFilter: String = "") {
        this.categoryFilter.value = categoryFilter
        launchIO { queryAllInner(categoryFilter) }
    }

    private suspend fun queryAllInner(categoryFilter: String = "") {
        var result = PasswordRepository.queryAll()
        if (categoryFilter.isNotEmpty() && categoryFilter != "全部") {
            result = result.filter { it.category == categoryFilter }
        }
        val listSize = result.size
        val sb = StringBuilder()
        if (categoryFilter.isNotEmpty()) {
            sb.append("过滤对应分类：$categoryFilter，")
        }
        sb.append("查询到密码的数量为 $listSize 条")
        HLog.d(sb.toString())
        list.value = result
    }

    fun insert(password: Password) {
        launchIO {
            PasswordRepository.insert(password)
            queryAllInner()
        }
    }

    fun update(password: Password) {
        launchIO {
            HLog.d("更新了密码，更新后为：$password")
            PasswordRepository.update(password)
            queryAllInner()
        }
    }

    fun deleteAll() {
        launchIO {
            PasswordRepository.deleteAll()
            queryAllInner()
        }
    }

    fun delete(password: Password) {
        launchIO {
            PasswordRepository.delete(password)
            queryAllInner()
        }
    }

}