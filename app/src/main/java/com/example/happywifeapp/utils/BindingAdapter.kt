package com.example.happywifeapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapter {

    companion object {

        /**Scaling down bitmap for small Icon*/

        @BindingAdapter("setImageFromStringUri")
        @JvmStatic
        fun setImageFromStringUri(imageView: ImageView, stringUri: String) {
            Glide.with(imageView.context)
                .load(stringUri)
                .into(imageView)

        }
    }
}