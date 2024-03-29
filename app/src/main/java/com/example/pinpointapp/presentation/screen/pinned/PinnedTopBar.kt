package com.example.pinpointapp.presentation.screen.pinned

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pinpointapp.ui.theme.topAppBarBackgroundColor
import com.example.pinpointapp.ui.theme.topAppBarContentColor

@Composable
fun PinnedTopBar(onMenuClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Drawer Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            }
        },
        title = {
            Text(
                text = "Pinned",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        }
    )
}

@Preview
@Composable
fun PinnedTopBar() {
    PinnedTopBar({})
}