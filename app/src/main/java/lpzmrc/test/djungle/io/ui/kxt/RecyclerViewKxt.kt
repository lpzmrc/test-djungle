package lpzmrc.test.djungle.io.ui.kxt

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.ui.widget.GridSpacingItemDecoration
import lpzmrc.test.djungle.io.ui.widget.LinearSpacingItemDecoration

val RecyclerView.todoLayoutManager: LinearLayoutManager
    get() = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

val RecyclerView.todoDecoration: RecyclerView.ItemDecoration
    get() = LinearSpacingItemDecoration(
        context,
        R.dimen.gallery_spacing,
        (layoutManager as? LinearLayoutManager)?.orientation ?: LinearLayoutManager.VERTICAL,
        true
    )

val RecyclerView.galleryLayoutManager: GridLayoutManager
    get() = GridLayoutManager(context, resources.getInteger(R.integer.gallery_columns))

val RecyclerView.galleryDecoration: RecyclerView.ItemDecoration
    get() = GridSpacingItemDecoration(
        context,
        R.integer.gallery_columns,
        R.dimen.gallery_spacing,
        true
    )