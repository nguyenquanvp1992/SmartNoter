package com.kingcrab.notes.editor.init.base

interface ItemBaseAdapter {
    fun areItemsTheSame(oldItem: ItemBaseAdapter): Boolean

    fun areContentsTheSame(oldItem: ItemBaseAdapter): Boolean
}