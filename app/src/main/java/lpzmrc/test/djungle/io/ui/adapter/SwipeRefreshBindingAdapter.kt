package lpzmrc.test.djungle.io.ui.adapter

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object SwipeRefreshBindingAdapter {
    @BindingAdapter("progress")
    @JvmStatic
    fun setRefreshing(view: SwipeRefreshLayout, progress: Boolean) {
        view.isRefreshing = progress == true
    }
}