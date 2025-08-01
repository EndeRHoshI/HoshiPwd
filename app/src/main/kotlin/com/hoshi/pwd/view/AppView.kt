package com.hoshi.pwd.view

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.hoshi.core.utils.HLog
import com.hoshi.pwd.R
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.viewmodel.PasswordViewModel
import com.hoshi.pwd.widget.CustomTextField
import com.hoshi.pwd.widget.EmptyPage
import com.hoshi.pwd.widget.PasswordItem

/**
 * App 主布局
 */
@Composable
fun AppView(
    pwdViewModel: PasswordViewModel,
    filePickerLauncher: ActivityResultLauncher<String>
) {
    val context = LocalContext.current
    val editDialogVisible = remember { mutableStateOf(false) }
    val deleteAllDialogVisible = remember { mutableStateOf(false) }
    val moreDialogVisible = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = context.getString(R.string.app_name), color = Color.White)
                },
                actions = {
                    CustomTextField(
                        pwdViewModel.contentFilter,
                        modifier = Modifier
                            .background(Color.White)
                            .width(188.dp)
                            .height(30.dp),
                        onTextChange = {
                            pwdViewModel.queryAll(pwdViewModel.categoryFilter.value, it)
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.padding(start = 6.dp),
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        },
                        trailingIcon = {
                            if (pwdViewModel.contentFilter.value.isNotEmpty()) { // 不为空时，显示清除按钮
                                Icon(
                                    modifier = Modifier
                                        .padding(end = 6.dp)
                                        .clickable {
                                            pwdViewModel.contentFilter.value = ""
                                            pwdViewModel.queryAll(pwdViewModel.categoryFilter.value, "")
                                        },
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.Black
                                )
                            }
                        },
                        textFieldStyle = TextStyle.Default.copy(color = Color.Black)
                    )
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        modifier = Modifier
                            .clickable(onClick = { moreDialogVisible.value = true })
                            .padding(4.dp, 0.dp),
                        tint = Color.White
                    )
                },
                backgroundColor = Color.Black,
                elevation = 12.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editDialogVisible.value = true
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
        PageContent(editDialogVisible, pwdViewModel, paddingValue = innerPadding)
    }

    ConfirmDialog(
        deleteAllDialogVisible,
        "删除全部",
        "将会删除全部的密码数据，确认删除吗？",
        confirmAction = {
            pwdViewModel.deleteAll()
        }
    )

    MoreDialog(moreDialogVisible, deleteAllDialogVisible, pwdViewModel, filePickerLauncher)
}

@Composable
private fun PageContent(
    editDialogVisible: MutableState<Boolean>,
    pwdViewModel: PasswordViewModel,
    paddingValue: PaddingValues
) {
    if (pwdViewModel.list.value.isEmpty()) {
        EmptyPage()
    } else {
        PwdListPage(pwdViewModel, paddingValue)
    }
    EditDialog(editDialogVisible) { password -> pwdViewModel.insert(password) }
}

@Composable
fun PwdListPage(pwdViewModel: PasswordViewModel, paddingValue: PaddingValues) {
    val deletePasswordDialogVisible = remember { mutableStateOf(false) } // 删除密码弹窗是否可见
    val deletePassword = remember { mutableStateOf<Password?>(null) } // 删除密码时，对应的密码条目
    val editDialogVisible = remember { mutableStateOf(false) } // 编辑密码弹窗是否可见
    val editPassword = remember { mutableStateOf<Password?>(null) } // 编辑密码时，对应的密码条目
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue),
        contentPadding = PaddingValues(12.dp), // 列表内容的外边距，注意这个不是分割线，而是整个列表控件的外边距
        verticalArrangement = spacedBy(12.dp, Alignment.CenterVertically), // 第一个参数是分割线高度，第二个参数是父级内部间隔子级的对齐方式，暂时不明用途
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        pwdViewModel.list.value.forEach { password ->
            item {
                PasswordItem(
                    modifier = Modifier,
                    password,
                    pwdViewModel.liteMode.value, {
                        editPassword.value = password
                        editDialogVisible.value = true
                        HLog.d("打开了编辑页面，编辑密码：$password")
                    }, {
                        deletePassword.value = password
                        deletePasswordDialogVisible.value = true
                    })
            }
        }
    }
    ConfirmDialog(
        deletePasswordDialogVisible,
        "删除密码",
        "是否确认删除该密码？平台为 ${deletePassword.value?.platform}，账号为 ${deletePassword.value?.account}",
        confirmAction = {
            deletePassword.value?.let { pwdViewModel.delete(it) }
        }
    )
    EditDialog(editDialogVisible, editPassword) { password -> pwdViewModel.update(password) }
}