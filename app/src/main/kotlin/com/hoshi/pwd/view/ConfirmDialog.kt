package com.hoshi.pwd.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * 包含确认取消的弹窗
 */
@Composable
fun ConfirmDialog(
    visible: MutableState<Boolean>,
    title: String,
    content: String,
    cancelVisible: Boolean = true,
    cancelAble: Boolean = true,
    cancelAction: () -> Unit = {},
    confirmAction: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { visible.value = false },
        properties = DialogProperties(dismissOnBackPress = cancelAble, dismissOnClickOutside = cancelAble),
        content = {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(size = 6.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = title, Modifier.padding(0.dp, 18.dp), fontSize = 20.sp)
                    Text(text = content, Modifier.padding(22.dp, 0.dp), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(18.dp))
                    Row {
                        if (cancelVisible) {
                            Button(onClick = {
                                visible.value = false
                                cancelAction.invoke()
                            }) {
                                Text(text = "取消")
                            }
                        }
                        Spacer(modifier = Modifier.width(22.dp))
                        Button(onClick = {
                            visible.value = false
                            confirmAction.invoke()
                        }) {
                            Text(text = "确认")
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    )
}