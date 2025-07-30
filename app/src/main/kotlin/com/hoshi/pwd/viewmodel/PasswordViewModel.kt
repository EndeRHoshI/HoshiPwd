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
    val categoryFilter = mutableStateOf("") // 分类过滤
    val contentFilter = mutableStateOf("") // 内容过滤
    val liteMode = mutableStateOf(false) // 精简模式

    /**
     * 查询所有数据
     * @param categoryFilter 分类过滤
     */
    fun queryAll(categoryFilter: String = "", contentFilter: String = "") {
        this.categoryFilter.value = categoryFilter
        this.contentFilter.value = contentFilter
        launchIO { queryAllInner(categoryFilter, contentFilter) }
    }

    private suspend fun queryAllInner(categoryFilter: String = "", contentFilter: String = "") {
        var result = PasswordRepository.queryAll()
        if (categoryFilter.isNotEmpty() && categoryFilter != "全部") {
            result = result.filter { it.category == categoryFilter }
        }
        if (contentFilter.isNotEmpty()) {
            val platformResult = result.filter { it.platform.contains(contentFilter) }
            val accountResult = result.filter { it.account.contains(contentFilter) }
            result = platformResult + accountResult
        }
        val listSize = result.size
        val sb = StringBuilder()
        if (categoryFilter.isNotEmpty()) {
            sb.append("过滤对应分类：$categoryFilter，")
        }
        if (contentFilter.isNotEmpty()) {
            sb.append("过滤对应内容：$contentFilter，")
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