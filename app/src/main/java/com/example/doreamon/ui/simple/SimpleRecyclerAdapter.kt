package com.example.doreamon.ui.simple

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doreamon.R

/**
 * RecyclerView的adapter示例
 * @author wzh
 * @date 2023/2/1
 */
const val TAG="SimpleRecyclerAdapter"
class SimpleRecyclerAdapter : RecyclerView.Adapter<SimpleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        Log.v(TAG,"onCreateViewHolder")
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_simple, parent, false)
        return SimpleViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        Log.v(TAG,"onBindViewHolder:${TextSimple.POKER[position]}")
        holder.bind(TextSimple.POKER[position])

    }

    override fun onViewRecycled(holder: SimpleViewHolder) {

        Log.v(TAG,"onViewRecycled:${TextSimple.POKER[holder.layoutPosition]}")
    }


    override fun getItemCount() = TextSimple.POKER.size
}

class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tv = itemView.findViewById<TextView>(R.id.tv_text)

    fun bind(word: String) {
        tv.text = word
    }

}