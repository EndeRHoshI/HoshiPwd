package com.hoshi.pwd.utils

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.hoshi.core.utils.FileUtils
import com.hoshi.core.utils.HLog
import com.hoshi.core.utils.TimeUtils
import com.hoshi.pwd.data.PasswordRepository
import com.hoshi.pwd.database.entities.Password
import com.hoshi.pwd.extentions.showToast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader


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
            if (dataList.isEmpty()) {
                showToast("暂时还没有密码记录，无法导出")
                return@launch
            }
            val dataGroup = dataList.groupBy { it.category }
            val date = TimeUtils.getSimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
            val txtPath = FileUtils.getTempDir() + "password_$date.json"
            val txtFile = File(txtPath)

            // 需要过滤字段时，要用这种方式构建 Gson 对象
            val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()

            writeToTxt(txtFile, gson.toJson(dataGroup))
            showToast("导出成功，路径为 $txtPath")
        }
    }

    fun import(jsonPath: String?, successAction: () -> Unit = {}) {
        if (jsonPath.isNullOrEmpty()) {
            HLog.d("导入记录时，jsonPath 为空，请检查调用")
        } else {
            HLog.d("准备导入记录，jsonPath = $jsonPath")
            val file = File(jsonPath)
            if (file.exists()) {
                val stringBuilder = StringBuilder()
                FileInputStream(file).use {
                    BufferedReader(InputStreamReader(it)).use { reader ->
                        var line: String? = reader.readLine()
                        while (line != null) {
                            stringBuilder.append(line)
                            line = reader.readLine()
                            if (line != null) {
                                stringBuilder.append("\n")
                            }
                        }
                    }
                }
                val jsonString = stringBuilder.toString()
                readJsonStr(jsonString, successAction)
            } else {
                HLog.d("导入记录时，文件不存在，请检查调用")
            }
        }

    }

    fun import(context: Context, uri: Uri?, successAction: () -> Unit = {}) {
        if (uri == null) {
            HLog.d("导入记录时，uri 为空，请检查调用")
        } else {
            val stringBuilder = StringBuilder()
            context.contentResolver.openInputStream(uri).use {
                BufferedReader(InputStreamReader(it)).use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                        if (line != null) {
                            stringBuilder.append("\n")
                        }
                    }
                }
            }
            val jsonString = stringBuilder.toString()
            readJsonStr(jsonString, successAction)
        }
    }

    private fun readJsonStr(jsonStr: String?, successAction: () -> Unit = {}) {
        runCatching {
            val jsonObject = JsonParser.parseString(jsonStr).asJsonObject
            val pwdList = mutableListOf<Password>()
            val gson = Gson()
            jsonObject.keySet().forEach {
                val categoryKey = it
                val pwdListArray = jsonObject.get(categoryKey).asJsonArray
                pwdListArray.forEach { jsonElement ->
                    val password = gson.fromJson(jsonElement, Password::class.java)
                    password.category = categoryKey // 把分类填进去一下
                    pwdList.add(password)
                }
            }
            MainScope().launch {
                PasswordRepository.deleteAll() // 因为是全量替换，所以先把所有删除了
                PasswordRepository.insert(pwdList)
                val size = pwdList.size
                val toastStr = "导入完毕，共 $size 条数据"
                showToast(toastStr)
                HLog.d(toastStr)
                successAction.invoke()
            }
        }.onFailure {
            val toastStr = "导入失败，message = ${it.message}"
            HLog.e(toastStr)
            showToast(toastStr)
        }
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