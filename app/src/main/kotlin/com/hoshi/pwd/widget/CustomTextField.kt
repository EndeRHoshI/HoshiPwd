package com.hoshi.pwd.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/**
 * 自定义的 TextField，基于 BaseTextField
 * @param text 当前文本
 * @param modifier Modifier
 * @param hint hint
 * @param onTextChange 文字改变回调
 * @param leadingIcon 后方控件
 */
@Composable
fun CustomTextField(
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    hint: String? = null,
    onTextChange: (String) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: String.() -> Unit = {},
    textFieldStyle: TextStyle = LocalTextStyle.current,
    hintFieldStyle: TextStyle = LocalTextStyle.current,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically // 保持内部控件垂直居中
    ) {
        leadingIcon?.invoke() // 显示前方的控件
        BasicTextField(
            value = text.value,
            onValueChange = { str ->
                text.value = str // 改变一下当前文本
                onTextChange.invoke(str) // 回调给外部
            },
            cursorBrush = SolidColor(colorResource(id = android.R.color.holo_blue_bright)), // 设置指针颜色
            singleLine = true, // 单行
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            textStyle = textFieldStyle,
            decorationBox = { innerTextField ->
                if (text.value.isBlank() && !hint.isNullOrBlank()) { // 处理 hint
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                        Text(
                            hint,
                            modifier = Modifier.fillMaxWidth(),
                            style = hintFieldStyle
                        )
                    }
                } else {
                    innerTextField()
                }
            },
            keyboardActions = KeyboardActions {
                keyboardActions(text.value)
            },
            keyboardOptions = keyboardOptions
        )
        trailingIcon?.invoke() // 显示后方的控件
    }
}