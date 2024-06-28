package com.hoshi.pwd.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoshi.pwd.R
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.showToast
import com.hoshi.pwd.utils.PwdUtils
import com.hoshi.pwd.viewmodel.PasswordViewModel
import com.hoshi.pwd.widget.EditText
import com.hoshi.pwd.widget.EmptyPage
import com.hoshi.pwd.widget.PasswordItem
import com.lxj.xpopup.XPopup

class MainActivity : BaseActivity() {

    private val pwdViewModel by viewModels<PasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopBar {
                        XPopup.Builder(this)
                            .asBottomList(
                                "",
                                arrayOf("删除所有记录", "导出记录", "导入记录"),
                                intArrayOf()
                            ) { position, _ ->
                                when (position) {
                                    0 -> XPopup.Builder(this)
                                        .asConfirm(
                                            "删除所有记录",
                                            "将会删除所有的密码数据，确认删除吗？"
                                        ) {
                                            pwdViewModel.deleteAll()
                                        }
                                        .show()

                                    1 -> PwdUtils.export()
                                    2 -> PwdUtils.import()
                                }
                            }.show()
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            pwdViewModel.showingDialog.value = true
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
                Text(text = getString(R.string.app_name), color = Color.White)
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
        EditDialog()
    }

    @Composable
    fun EditDialog() {
        val category = remember { mutableStateOf("") }
        val platform = remember { mutableStateOf("") }
        val account = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        /**
         * 隐藏弹窗并且清空数据
         */
        fun dismissAndClear() {
            pwdViewModel.showingDialog.value = false
            category.value = ""
            platform.value = ""
            account.value = ""
            password.value = ""
        }

        if (pwdViewModel.showingDialog.value) {
            AlertDialog(
                onDismissRequest = { dismissAndClear() },
                title = { Text(text = "新建密码") },
                text = {
                    Column {
                        Text(text = "输入对应的分类、平台、账号、密码")
                        Spacer(modifier = Modifier.height(10.dp))
                        EditText(category.value, { category.value = it }, "分类")
                        Spacer(modifier = Modifier.height(6.dp))
                        EditText(platform.value, { platform.value = it }, "平台")
                        Spacer(modifier = Modifier.height(6.dp))
                        EditText(account.value, { account.value = it }, "账号")
                        Spacer(modifier = Modifier.height(6.dp))
                        EditText(password.value, { password.value = it }, "密码")
                    }
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            val categoryValue = category.value
                            val platformValue = platform.value
                            val accountValue = account.value
                            val passwordValue = password.value
                            if (categoryValue.isEmpty()) {
                                showToast("分类不能为空")
                                return@TextButton
                            }
                            if (platformValue.isEmpty()) {
                                showToast("平台不能为空")
                                return@TextButton
                            }
                            if (accountValue.isEmpty()) {
                                showToast("账号不能为空")
                                return@TextButton
                            }
                            if (passwordValue.isEmpty()) {
                                showToast("密码不能为空")
                                return@TextButton
                            }
                            pwdViewModel.insert(
                                Password(
                                    category = categoryValue,
                                    platform = platformValue,
                                    account = accountValue,
                                    password = passwordValue
                                )
                            )
                            dismissAndClear()
                        },
                    ) {
                        Text("保存")
                    }
                },
            )
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

}