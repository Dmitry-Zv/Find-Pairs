package com.vc.findpairs.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class CenterGridItemDecoration(private val spacing: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val itemCount = parent.adapter?.itemCount ?: 0

        val totalSpacing = spacing * (spanCount - 1)
        val totalWidth = parent.width - parent.paddingLeft - parent.paddingRight

        val itemWidth = (totalWidth - totalSpacing) / spanCount
        val row = position / spanCount
        val column = position % spanCount

        val extraSpace = totalWidth - itemWidth * spanCount - totalSpacing

        outRect.left = (extraSpace / 2 + column * spacing / spanCount).toInt()
        outRect.right = (extraSpace / 2 - (column + 1) * spacing / spanCount).toInt()

        if (row == 0) {
            outRect.top = spacing
        }
        outRect.bottom = spacing

        // Additional logic for the last row
        if (row == (itemCount - 1) / spanCount) {
            outRect.bottom = spacing + (spanCount - 1 - column) * spacing / spanCount
        }
    }
}