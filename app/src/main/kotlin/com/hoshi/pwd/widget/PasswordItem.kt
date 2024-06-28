package com.hoshi.pwd.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.getPwdDisplay

/**
 * Created by lv.qx on 2024/6/28
 */
@Composable
fun PasswordItem(password: Password) {
    val showPwd = remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            // .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(6.dp)) // 这个是线框
            .background(color = Color.Blue, shape = RoundedCornerShape(6.dp))
    ) {
        val (tPlatform, tAccount, tPassword, iVisible) = createRefs()
        Text(
            modifier = Modifier.constrainAs(tPlatform) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 12.dp)
            },
            text = password.platform, color = Color.Black
        )
        Text(
            modifier = Modifier.constrainAs(tAccount) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(tPlatform.end, margin = 12.dp)
            },
            text = password.account, color = Color.Black
        )
        Text(
            modifier = Modifier.constrainAs(tPassword) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(tAccount.end, margin = 12.dp)
            },
            text = if (showPwd.value) password.password else password.password.getPwdDisplay(),
            color = Color.Black
        )
        Icon(
            Icons.Filled.Lock,
            contentDescription = "",
            Modifier
                .constrainAs(iVisible) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .clickable { showPwd.value = !showPwd.value }
        )
    }
}