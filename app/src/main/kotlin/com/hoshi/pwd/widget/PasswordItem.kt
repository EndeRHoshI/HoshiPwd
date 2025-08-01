package com.hoshi.pwd.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.core.extentions.matchTrue
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.getPwdDisplay

/**
 * Created by lv.qx on 2024/6/28
 */
@Composable
fun PasswordItem(
    modifier: Modifier = Modifier,
    password: Password,
    liteMode: Boolean = false,
    updateAction: (Password) -> Unit,
    deleteAction: (Password) -> Unit
) {
    val showPwd = remember { mutableStateOf(false) }
    val textColor = Color.White
    Row(
        modifier = modifier
            .fillMaxWidth()
            // .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(6.dp)) // 这个是线框
            .background(color = Color.Black, shape = RoundedCornerShape(6.dp))
            .padding(12.dp) // background 后面的是内边距
            .clickable { updateAction.invoke(password) }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "平台：${password.platform}",
                color = textColor,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!liteMode) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "账号：${password.account}",
                    color = textColor
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "密码：${showPwd.value.matchTrue(password.password, password.password.getPwdDisplay())}",
                    color = textColor
                )
            }
        }
        if (!liteMode) {
            Column {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "",
                    Modifier.clickable { deleteAction.invoke(password) },
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    showPwd.value.matchTrue(Icons.Sharp.Favorite, Icons.Sharp.FavoriteBorder),
                    contentDescription = "",
                    Modifier.clickable { showPwd.value = !showPwd.value },
                    tint = Color.White
                )
            }
        }
    }
}