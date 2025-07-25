package com.hoshi.pwd.view

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    Dialog(
        onDismissRequest = { visible.value = false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        content = {
            Box(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(size = 6.dp))) {
                Column {
                    Button(onClick = {
                        visible.value = false
                        deleteAllDialogVisible.value = true
                    }) {
                        Text(text = "删除所有记录")
                    }
                    Button(onClick = {
                        visible.value = false
                        PwdUtils.export()
                    }) {
                        Text(text = "导出记录")
                    }
                    Button(onClick = {
                        visible.value = false
                        filePickerLauncher.launch("*/*")
                    }) {
                        Text(text = "导入记录")
                    }
                }
            }
        }
    )
}