package com.example.moviesappjosephescribano.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

@Composable
fun CargaDrawable(): CircularProgressDrawable {
    val context = LocalContext.current
    val circularProgressDrawable: CircularProgressDrawable = remember {
        val c = CircularProgressDrawable(context)
        c.strokeWidth = 5f
        c.centerRadius = 30f
        c.start()
        c
    }
    return circularProgressDrawable
}