package com.example.ermolaevio.mviexample.mvi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ermolaevio.mviexample.R
import kotlinx.android.synthetic.main.item_row.view.*

class MainAdapter(private val inflater: LayoutInflater) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {


    private lateinit var items: List<String>

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): MainHolder {
        val view = inflater.inflate(R.layout.item_row, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

    }

    class MainHolder(view: View) : RecyclerView.ViewHolder(view) {

        private fun bind(message: String) {
            itemView.message.text = message
        }
    }
}
