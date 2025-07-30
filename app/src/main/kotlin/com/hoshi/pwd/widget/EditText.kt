package com.hoshi.pwd.widget

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

/**
 * Created by lv.qx on 2024/6/28
 * @param text 已经填在文本框内的文本
 * @param onValueChange 文本变更监听
 * @param labelContent 小标题，可以说相当于 hint，但它不在输入框内
 * @param keyboardType 软键盘类型
 */
@Composable
fun EditText(
    text: String,
    modifier: Modifier = Modifier,
    labelContent: String = "",
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        singleLine = true, // 用于文本垂直居中
        value = text,
        onValueChange = onValueChange,
        label = {
            if (labelContent.isNotEmpty()) {
                Text(labelContent)
            }
        },
        textStyle = TextStyle.Default.copy(fontSize = 15.sp),
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors( // 背景色、下划线颜色和 label 颜色在这里设置
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType) // 只能控制键盘样式，输入过滤还要另外处理
    )
}