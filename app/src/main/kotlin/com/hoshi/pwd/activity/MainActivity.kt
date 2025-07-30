package com.hoshi.pwd.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hoshi.core.utils.FileUtils
import com.hoshi.pwd.utils.PwdUtils
import com.hoshi.pwd.view.AppView
import com.hoshi.pwd.viewmodel.PasswordViewModel

class MainActivity : BaseActivity() {

    private val pwdViewModel by viewModels<PasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FileUtils.deleteFile(FileUtils.getTempDir()) // 每次打开应用都清除临时文件夹，后续把这个清除直接整合到工具类里面，写成 deleteTemp 之类的
        val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            PwdUtils.import(this, uri) { pwdViewModel.queryAll() }
        }
        setContent { AppView(pwdViewModel, filePickerLauncher) }
    }

    override fun onResume() {
        super.onResume()
        pwdViewModel.queryAll()
    }

}