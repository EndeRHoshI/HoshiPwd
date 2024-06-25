package com.hoshi.pwd

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoshi.pwd.extentions.showToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopBar {
                        showToast("hahahah")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {

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
        val hasPwd = false
        if (hasPwd) {
            PwdListPage(paddingValue)
        } else {
            EmptyPage()
        }

    }

    @Composable
    fun PwdListPage(paddingValue: PaddingValues) {
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            Text(
                text = "哈哈哈哈"
            )
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

}