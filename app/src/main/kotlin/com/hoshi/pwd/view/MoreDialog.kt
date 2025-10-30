package com.hoshi.pwd.view

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hoshi.core.utils.FileUtils
import com.hoshi.pwd.utils.PwdUtils
import com.hoshi.pwd.viewmodel.PasswordViewModel

/**
 * 更多弹窗，在这个弹窗可以执行各类操作、配置
 */
@Composable
fun MoreDialog(
    visible: MutableState<Boolean>,
    deleteAllDialogVisible: MutableState<Boolean>,
    pwdViewModel: PasswordViewModel,
    filePickerLauncher: ActivityResultLauncher<String>
) {
    if (visible.value) {
        Dialog(
            onDismissRequest = { visible.value = false },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(size = 6.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Button(
                            onClick = {
                                visible.value = false
                                deleteAllDialogVisible.value = true
                            }
                        ) {
                            Text(text = "删除全部")
                        }
                        Button(
                            onClick = {
                                visible.value = false
                                PwdUtils.export() // 导出到应用内的 cache/temp 目录下
                            }
                        ) {
                            Text(text = "导出记录")
                        }
                        Button(
                            onClick = {
                                // 导入时，将上面导出的文件改下名字放到 temp 里面，再点击导入即可
                                PwdUtils.import(FileUtils.getTempDir() + "password.json") { pwdViewModel.queryAll() }
                                visible.value = false
                                // filePickerLauncher.launch("*/*")
                            }
                        ) {
                            Text(text = "导入记录")
                        }
                        Spacer(Modifier.height(10.dp))
                        CategoryView(pwdViewModel.categoryFilter.value, true) { category -> pwdViewModel.queryAll(category) }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .toggleable(
                                    value = pwdViewModel.liteMode.value,
                                    onValueChange = { pwdViewModel.liteMode.value = it },
                                    role = Role.Checkbox
                                )
                                .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = pwdViewModel.liteMode.value,
                                onCheckedChange = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = "精简模式",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}