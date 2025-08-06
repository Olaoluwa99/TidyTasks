package com.develop.tidytasks.presentation.home.utils

import androidx.recyclerview.widget.DiffUtil
import com.develop.tidytasks.data.model.Todo

class TodoDiffCallback(
    private val oldList: List<Todo>,
    private val newList: List<Todo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}