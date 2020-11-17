package com.gan.breakingbad.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("isVisible")
fun isVisible(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("imageResource")
fun imageResource(imageView: ImageView, drawable: Int) {
    imageView.setImageResource(drawable)
}

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, url: String?) {
    if (url == null) {
        return
    }
    Glide.with(imageView.context).load(url).into(imageView)
}