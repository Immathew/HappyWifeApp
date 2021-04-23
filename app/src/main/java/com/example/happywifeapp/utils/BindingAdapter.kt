package com.example.happywifeapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class BindingAdapter {

    companion object {

        /**Scaling down bitmap for small Icon*/

        @BindingAdapter("setImageFromStringUri")
        @JvmStatic
        fun setImageFromStringUri(imageView: ImageView, stringUri: String) {
            val bitmapFactory = BitmapFactory.decodeFile(stringUri)
            val scaleBitmap = Bitmap.createScaledBitmap(
                bitmapFactory,
                (bitmapFactory.width * 0.95).toInt(),
                (bitmapFactory.height * 0.95).toInt(),
                true
            )
            imageView.setImageBitmap(scaleBitmap)

        }
    }
}