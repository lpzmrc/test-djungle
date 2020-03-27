package lpzmrc.test.djungle.io.ui.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(
    context: Context,
    @DimenRes
    spacingRes: Int,
    @RecyclerView.Orientation
    private val orientation: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    private val spacing = context.resources.getDimensionPixelSize(spacingRes)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        when (orientation) {
            RecyclerView.VERTICAL -> getItemOffsetsVertical(outRect, position, spacing)
            RecyclerView.HORIZONTAL -> getItemOffsetsHorizontal(outRect, position, spacing)
        }
    }

    private fun getItemOffsetsVertical(outRect: Rect, position: Int, spacing: Int) {
        if (includeEdge) {
            outRect.left = spacing
            outRect.right = spacing
        }
        if (position == 0) { // top edge
            outRect.top = spacing
        }
        outRect.bottom = spacing // item bottom
    }

    private fun getItemOffsetsHorizontal(outRect: Rect, position: Int, spacing: Int) {
        if (includeEdge) {
            outRect.top = spacing
            outRect.bottom = spacing
        }
        if (position == 0) { // left edge
            outRect.left = spacing
        }
        outRect.right = spacing // item right
    }
}