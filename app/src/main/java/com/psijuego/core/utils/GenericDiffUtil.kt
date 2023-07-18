package com.psijuego.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.ParameterUI

class GenericDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList.all { it is ParameterUI } && newList.all { it is ParameterUI }) {
            return (oldList as List<ParameterUI>)[oldItemPosition] == (newList as List<ParameterUI>)[newItemPosition]
        }
        if (oldList.all { it is CategoryUI } && newList.all { it is CategoryUI }) {
            return (oldList as List<CategoryUI>)[oldItemPosition] == (newList as List<CategoryUI>)[newItemPosition]
        }
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}