package com.hoshi.pwd.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoshi.core.extentions.matchTrue
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.showToast
import com.hoshi.pwd.widget.EditText

/**
 * 编辑密码弹窗
 * @param visible 控制弹窗可见
 * @param currentPassword 当前密码，如果是 null 的话，说明是新建密码
 * @param savePasswordAction 保存密码的回调
 */
@Composable
fun EditDialog(
    visible: MutableState<Boolean>,
    currentPassword: MutableState<Password?> = mutableStateOf(null),
    savePasswordAction: (Password) -> Unit
) {
    val isCreate by remember { mutableStateOf(currentPassword.value == null) } // 去一下是否新建密码

    /**
     * 隐藏弹窗并且清空数据
     */
    fun dismissAndClear() {
        visible.value = false
        currentPassword.value = null
    }

    if (visible.value) {
        AlertDialog(
            onDismissRequest = { dismissAndClear() },
            title = { Text(text = "${isCreate.matchTrue("新建", "编辑")}密码") },
            text = {
                Column {
                    Text(text = "输入对应的分类、平台、账号、密码")
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryView(currentPassword.value?.category.orEmpty()) { createAndUpdatePwd(currentPassword, newCategory = it) }
                    Spacer(modifier = Modifier.height(6.dp))
                    EditText(currentPassword.value?.platform.orEmpty(), { createAndUpdatePwd(currentPassword, newPlatform = it) }, "平台")
                    Spacer(modifier = Modifier.height(6.dp))
                    EditText(currentPassword.value?.account.orEmpty(), { createAndUpdatePwd(currentPassword, newAccount = it) }, "账号")
                    Spacer(modifier = Modifier.height(6.dp))
                    EditText(currentPassword.value?.password.orEmpty(), { createAndUpdatePwd(currentPassword, newPassword = it) }, "密码")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val idValue = currentPassword.value?.id ?: 0
                        val categoryValue = currentPassword.value?.category
                        val platformValue = currentPassword.value?.platform
                        val accountValue = currentPassword.value?.account
                        val passwordValue = currentPassword.value?.password
                        if (categoryValue.isNullOrEmpty()) {
                            showToast("请选择分类")
                            return@TextButton
                        }
                        if (platformValue.isNullOrEmpty()) {
                            showToast("平台不能为空")
                            return@TextButton
                        }
                        if (accountValue.isNullOrEmpty()) {
                            showToast("账号不能为空")
                            return@TextButton
                        }
                        if (passwordValue.isNullOrEmpty()) {
                            showToast("密码不能为空")
                            return@TextButton
                        }
                        savePasswordAction.invoke(
                            Password(
                                id = idValue,
                                category = categoryValue,
                                platform = platformValue,
                                account = accountValue,
                                password = passwordValue
                            )
                        )
                        dismissAndClear()
                    },
                ) {
                    Text("保存")
                }
            },
        )
    }
}

private fun createAndUpdatePwd(
    currentPassword: MutableState<Password?>,
    newCategory: String? = null, // 分类
    newPlatform: String? = null, // 平台
    newAccount: String? = null, // 账号
    newPassword: String? = null, // 密码

) {
    // 如果取不到当前密码的话，创建一个全部都是默认值的
    val password = (currentPassword.value?.copy() ?: Password(category = "", platform = "", account = "", password = "")).apply {
        newCategory?.let { category = it }
        newPlatform?.let { platform = it }
        newAccount?.let { account = it }
        newPassword?.let { password = it }
    }
    currentPassword.value = password
}