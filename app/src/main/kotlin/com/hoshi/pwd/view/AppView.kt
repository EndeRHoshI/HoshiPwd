package com.hoshi.pwd.view

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hoshi.pwd.R
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.showToast
import com.hoshi.pwd.viewmodel.PasswordViewModel
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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = context.getString(R.string.app_name), color = Color.White)
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        modifier = Modifier.clickable(onClick = { moreDialogVisible.value = true }),
                        tint = Color.White
                    )
                },
                backgroundColor = colorResource(id = android.R.color.holo_blue_bright),
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

    if (deleteAllDialogVisible.value) {
        ConfirmDialog(
            deleteAllDialogVisible,
            "删除所有记录",
            "将会删除所有的密码数据，确认删除吗？",
            confirmAction = {
                pwdViewModel.deleteAll()
            }
        )
    }

    if (moreDialogVisible.value) {
        MoreDialog(moreDialogVisible, deleteAllDialogVisible, pwdViewModel, filePickerLauncher)
    }
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
    val deletePasswordDialogVisible = remember { mutableStateOf(false) }
    val deletePassword = remember { mutableStateOf<Password?>(null) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue)
    ) {
        pwdViewModel.list.value.forEach {
            item {
                PasswordItem(it, {
                    showToast("点击了，准备修改")
                }, {
                    deletePassword.value = it
                    deletePasswordDialogVisible.value = true
                })
            }
        }
    }
    if (deletePasswordDialogVisible.value) {
        ConfirmDialog(
            deletePasswordDialogVisible,
            "删除密码",
            "是否确认删除该密码？平台为 ${deletePassword.value?.platform}，账号为 ${deletePassword.value?.account}",
            confirmAction = {
                deletePassword.value?.let { pwdViewModel.delete(it) }
            }
        )
    }
}