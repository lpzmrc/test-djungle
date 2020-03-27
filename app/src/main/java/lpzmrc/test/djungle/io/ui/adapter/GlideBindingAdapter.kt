package lpzmrc.test.djungle.io.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.ui.view.gallery.glide.GlideApp

object GlideBindingAdapter {
    @BindingAdapter(value = ["glideUrl"])
    @JvmStatic
    fun setGlideUrl(view: ImageView, glideUrl: String?) {
        glideUrl?.let { string ->
            GlideApp.with(view)
                .load(string)
                .placeholder(R.drawable.ic_file_download)
                .error(R.drawable.ic_dissatisfied)
                .centerCrop()
                .into(view)
        }
    }
}