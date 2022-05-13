package com.example.doreamon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2022/5/6
 */
class BannerAdapter(val list:List<String>):RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loop, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.tvTitle.text="这是第${position+1}个pager"
    }

    override fun getItemCount()=list.size
}

class CardViewHolder internal constructor(itemView: View) :RecyclerView.ViewHolder(itemView){
        val tvTitle=itemView.findViewById<TextView>(R.id.tv_title)
}