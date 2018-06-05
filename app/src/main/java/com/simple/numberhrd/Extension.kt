package com.simple.numberhrd

import android.content.Context
import android.widget.Toast

fun Context.toast(text: CharSequence) {
    Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
}