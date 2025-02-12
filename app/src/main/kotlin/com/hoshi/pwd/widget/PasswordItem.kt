package com.hoshi.pwd.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.getPwdDisplay

/**
 * Created by lv.qx on 2024/6/28
 */
@Composable
fun PasswordItem(password: Password, updateAction: (Password) -> Unit, deleteAction: (Password) -> Unit) {
    val showPwd = remember { mutableStateOf(false) }
    val textColor = Color.White
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            // .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(6.dp)) // 这个是线框
            .background(color = colorResource(id = android.R.color.holo_blue_bright), shape = RoundedCornerShape(6.dp))
            .clickable { updateAction.invoke(password) }
    ) {
        val (tPlatform, tAccount, tPassword, iVisible, iDelete) = createRefs()
        Text(
            modifier = Modifier.constrainAs(tPlatform) {
                top.linkTo(parent.top, margin = 12.dp)
                bottom.linkTo(tAccount.top)
                start.linkTo(parent.start, margin = 12.dp)
            },
            text = password.platform,
            color = textColor,
            fontSize = 16.sp
        )
        Text(
            modifier = Modifier.constrainAs(tAccount) {
                top.linkTo(tPlatform.bottom, margin = 6.dp)
                bottom.linkTo(tPassword.top)
                start.linkTo(tPlatform.start)
            },
            text = password.account, color = textColor
        )
        Text(
            modifier = Modifier.constrainAs(tPassword) {
                top.linkTo(tAccount.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 12.dp)
                start.linkTo(tPlatform.start)
            },
            text = if (showPwd.value) password.password else password.password.getPwdDisplay(),
            color = textColor
        )
        Icon(
            Icons.Filled.Lock,
            contentDescription = "",
            Modifier
                .constrainAs(iVisible) {
                    top.linkTo(tPassword.top)
                    bottom.linkTo(tPassword.bottom)
                    end.linkTo(parent.end, margin = 12.dp)
                }
                .clickable { showPwd.value = !showPwd.value }
        )
        Icon(
            Icons.Filled.Delete,
            contentDescription = "",
            Modifier
                .constrainAs(iDelete) {
                    top.linkTo(parent.top, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                }
                .clickable { deleteAction.invoke(password) }
        )
    }
}