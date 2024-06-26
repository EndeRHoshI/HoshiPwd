package com.hoshi.pwd.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.getPwdDisplay
import com.hoshi.pwd.viewmodel.PasswordViewModel

class MainActivity : BaseActivity() {

    private val pwdViewModel by viewModels<PasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopBar {
                        pwdViewModel.deleteAll()
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            pwdViewModel.insert()
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.Add, "")
                    }
                }
            ) { innerPadding ->
                PageContent(paddingValue = innerPadding)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        pwdViewModel.queryAll()
    }

    @Composable
    fun TopBar(onMenuClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(text = "Hoshi 密码本", color = Color.White)
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier.clickable(onClick = onMenuClicked),
                    tint = Color.White
                )
            },
            backgroundColor = Color.Blue,
            elevation = 12.dp
        )
    }

    @Composable
    fun PageContent(paddingValue: PaddingValues) {
        if (pwdViewModel.list.value.isEmpty()) {
            EmptyPage()
        } else {
            PwdListPage(paddingValue)
        }
    }

    @Composable
    fun PwdListPage(paddingValue: PaddingValues) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            pwdViewModel.list.value.forEach {
                item {
                    PasswordItem(it)
                }
            }
        }
    }

    @Composable
    fun EmptyPage() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "暂时还没有密码记录", color = Color.Black
            )
        }
    }

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

}