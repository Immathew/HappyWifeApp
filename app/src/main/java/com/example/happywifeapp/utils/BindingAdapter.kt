package com.example.happywifeapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI

class BindingAdapter {

    companion object {

        /**Scaling down bitmap for small Icon*/

        @BindingAdapter("setImageFromStringUri")
        @JvmStatic
        fun setImageFromStringUri(imageView: ImageView, stringUri: String) {
            val bitmapFactory = BitmapFactory.decodeFile(stringUri)
            val scaleBitmap = Bitmap.createScaledBitmap(
                bitmapFactory,
                (bitmapFactory.width * 0.9).toInt(),
                (bitmapFactory.height * 0.9).toInt(),
                true
            )
            imageView.setImageBitmap(scaleBitmap)

        }
    }
}