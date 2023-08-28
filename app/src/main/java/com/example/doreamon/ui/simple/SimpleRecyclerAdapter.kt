package com.example.doreamon.ui.simple

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.doreamon.R
import com.example.doreamon.widget.recycleritemdrag.DraggableOrSwipeAdapter
import com.example.doreamon.widget.recycleritemdrag.MyItemTouchHelper
import java.util.*

/**
 * RecyclerView的adapter示例
 * @author wzh
 * @date 2023/2/1
 */
const val TAG = "SimpleRecyclerAdapter"

class SimpleRecyclerAdapter : RecyclerView.Adapter<SimpleViewHolder>(), DraggableOrSwipeAdapter {

    val list = mutableListOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")

    init {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        Log.v(TAG, "onCreateViewHolder")
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_simple, parent, false)
        return SimpleViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder:${TextSimple.POKER[position]}")
        holder.bind(list[position])

    }

    override fun onViewRecycled(holder: SimpleViewHolder) {

    }


    override fun getItemCount() = list.size
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        //ItemTouchHelper绑定Recyclerview
        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelper(this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}

class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tv = itemView.findViewById<TextView>(R.id.tv_text)

    fun bind(word: String) {
        tv.text = word
    }

}