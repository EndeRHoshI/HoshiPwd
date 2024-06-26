package com.hoshi.pwd.extentions

/**
 * Created by lv.qx on 2024/6/26
 */
fun String.getPwdDisplay(): String {
    val sb = StringBuilder()
    repeat(length) {
        sb.append("•")
    }
    return sb.toString()
}