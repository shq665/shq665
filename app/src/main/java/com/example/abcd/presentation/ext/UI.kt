package com.example.abcd.presentation.ext

import android.content.Context
import android.content.pm.PackageManager
import android.media.Image
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

fun ImageView.loadImageUrl(url: String){
    Glide
        .with(this)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageUrlWithoutScale(url: String, ignoreScale: Boolean = false){
    Glide
        .with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadImageResources(resId: Int){
    Glide
        .with(this)
        .load(resId)
        .centerCrop()
        .into(this)
}

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED