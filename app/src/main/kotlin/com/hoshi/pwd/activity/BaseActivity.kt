package com.hoshi.pwd.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hoshi.core.utils.HLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by lv.qx on 2024/5/15
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    protected val tag: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HLog.d(tag, "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        HLog.d(tag, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        HLog.d(tag, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        HLog.d(tag, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        HLog.d(tag, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() // 取消协程
        HLog.d(tag, "onDestroy()")
    }

}