package com.hoshi.pwd.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hoshi.core.extentions.matchTrue

/**
 * 分类控件，这里写死几种类型，是因为自己用的不会频繁新增、修改类型，如果有需要再写死就好了
 * @param current 当前选项，为空时即为没有选中
 * @param showAll 是否展示 "全部" 选项，在筛选时会用到
 * @param selectedAction 选中回调
 */
@Composable
fun CategoryView(
    current: String = "",
    showAll: Boolean = false,
    selectedAction: (String) -> Unit
) {
    var currentState by remember { mutableStateOf(current) }
    val categoryList = mutableListOf("生活", "游戏", "工作")
    if (showAll) {
        categoryList.add("全部")
    }
    Row {
        categoryList.forEach {
            val isSelected = it == currentState || (it == "全部" && current == "")
            Text(
                it,
                modifier = Modifier
                    .padding(6.dp, 0.dp)
                    .background(isSelected.matchTrue(Color.Black, Color.Gray))
                    .padding(12.dp, 6.dp)
                    .clickable {
                        currentState = it
                        selectedAction.invoke(it)
                    },
                color = Color.White
            )
        }
    }
}