package lpzmrc.test.djungle.io.ui.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    context: Context,
    @IntegerRes
    spanCountRes: Int,
    @DimenRes
    spacingRes: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    val spanCount = context.resources.getInteger(spanCountRes)
    val spacing = context.resources.getDimensionPixelSize(spacingRes)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val spanSize = (parent.layoutManager as? GridLayoutManager)?.spanSizeLookup?.getSpanSize(position) ?: 1
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount * spanSize // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount * spanSize// (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount * spanSize// column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount * spanSize // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}