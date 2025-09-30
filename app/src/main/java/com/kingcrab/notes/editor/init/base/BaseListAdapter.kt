package com.kingcrab.notes.editor.init.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<O : ItemBaseAdapter, V : ViewDataBinding?>() :
    ListAdapter<O, BaseListAdapter.ViewHolder<V>>(getDiffCallBack()) {

    companion object {
        fun <O : ItemBaseAdapter?> getDiffCallBack(): DiffUtil.ItemCallback<O> {
            return object : DiffUtil.ItemCallback<O>() {

                override fun areItemsTheSame(oldItem: O & Any, newItem: O & Any): Boolean {
                    return oldItem.areItemsTheSame(newItem)
                }

                override fun areContentsTheSame(oldItem: O & Any, newItem: O & Any): Boolean {
                    return oldItem.areContentsTheSame(newItem)
                }
            }
        }
    }

    protected var originData: List<O> = mutableListOf()
    private var recyclerView: RecyclerView? = null

    protected abstract val layoutViewHolder: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        val binding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            layoutViewHolder, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItem(position: Int): O? {
        return if (position in 0..<itemCount) {
            super.getItem(position)
        } else {
            null
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun setData(list: List<O>) {
        originData = list
        this.submitList(ArrayList(list)) {
            recyclerView?.scrollToPosition(0)
        }
    }

    class ViewHolder<V : ViewDataBinding?>(val binding: V) : RecyclerView.ViewHolder(binding!!.root)
}