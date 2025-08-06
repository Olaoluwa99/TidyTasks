package com.develop.tidytasks.presentation.home.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecorator(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        outRect.left = space
        outRect.right = space
        outRect.top = space

        // Optional: Add extra space at the bottom only for the last item
        if (position == itemCount - 1) {
            outRect.bottom = space
        }
    }
}
