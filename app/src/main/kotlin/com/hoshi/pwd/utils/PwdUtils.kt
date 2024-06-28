package com.hoshi.pwd.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hoshi.core.utils.FileUtils
import com.hoshi.pwd.data.PasswordRepository
import com.hoshi.pwd.extentions.showToast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


/**
 * Created by lv.qx on 2024/6/28
 */
object PwdUtils {

    /**
     * 导出
     */
    fun export() {
        MainScope().launch {
            val dataList = PasswordRepository.queryAll()
            val dataGroup = dataList.groupBy { it.category }
            val txtPath = FileUtils.getTempDir() + "password.json"
            val txtFile = File(txtPath)

            // 需要过滤字段时，要用这种方式构建 Gson 对象
            val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()

            writeToTxt(txtFile, gson.toJson(dataGroup))
            showToast("导出成功，路径为 $txtPath")
        }
    }

    fun import() {

    }

    /**
     * 写入到 txt 文件中
     * 后续可以抽出到工具类中
     * @param content String 写入内容
     */
    private fun writeToTxt(txtFile: File, content: String) {
        val outputStream = FileOutputStream(txtFile)
        outputStream.use { it.write(content.toByteArray()) }
    }

}