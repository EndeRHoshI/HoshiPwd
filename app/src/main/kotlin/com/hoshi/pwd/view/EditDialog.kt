package com.hoshi.pwd.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.showToast
import com.hoshi.pwd.widget.EditText

/**
 * 编辑密码弹窗
 */
@Composable
fun EditDialog(visible: MutableState<Boolean>, savePasswordAction: (Password) -> Unit) {
    val category = remember { mutableStateOf("") }
    val platform = remember { mutableStateOf("") }
    val account = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    /**
     * 隐藏弹窗并且清空数据
     */
    fun dismissAndClear() {
        visible.value = false
        category.value = ""
        platform.value = ""
        account.value = ""
        password.value = ""
    }

    if (visible.value) {
        AlertDialog(
            onDismissRequest = { dismissAndClear() },
            title = { Text(text = "新建密码") },
            text = {
                Column {
                    Text(text = "输入对应的分类、平台、账号、密码")
                    Spacer(modifier = Modifier.height(10.dp))
                    EditText(category.value, { category.value = it }, "分类")
                    Spacer(modifier = Modifier.height(6.dp))
                    EditText(platform.value, { platform.value = it }, "平台")
                    Spacer(modifier = Modifier.height(6.dp))
                    EditText(account.value, { account.value = it }, "账号")
                    Spacer(modifier = Modifier.height(6.dp))
                    EditText(password.value, { password.value = it }, "密码")
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        val categoryValue = category.value
                        val platformValue = platform.value
                        val accountValue = account.value
                        val passwordValue = password.value
                        if (categoryValue.isEmpty()) {
                            showToast("分类不能为空")
                            return@TextButton
                        }
                        if (platformValue.isEmpty()) {
                            showToast("平台不能为空")
                            return@TextButton
                        }
                        if (accountValue.isEmpty()) {
                            showToast("账号不能为空")
                            return@TextButton
                        }
                        if (passwordValue.isEmpty()) {
                            showToast("密码不能为空")
                            return@TextButton
                        }
                        savePasswordAction.invoke(
                            Password(
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