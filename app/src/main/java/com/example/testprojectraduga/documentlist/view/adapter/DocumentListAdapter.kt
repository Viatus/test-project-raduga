package com.example.testprojectraduga.documentlist.view.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testprojectraduga.R
import com.example.testprojectraduga.api.DocumentListDataItem
import com.example.testprojectraduga.databinding.ListItemBinding
import javax.inject.Inject

class DocumentListAdapter :
    RecyclerView.Adapter<DocumentListAdapter.ViewHolder>() {
    private var items: List<DocumentListDataItem> = emptyList()

    var selectedItemPos = RecyclerView.NO_POSITION
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == selectedItemPos)
    }

    fun updateItems(items: List<DocumentListDataItem>) {
        if (this.items == items) {
            return
        }
        this.items = items
        selectedItemPos = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: DocumentListDataItem, isSelected: Boolean) {
            binding.apply {
                textViewIdRecord.text = listItem.id_record.toString()
                if (listItem.id_hd_route != null) {
                    textViewIdHdRoute.visibility = View.VISIBLE
                    textViewIdHdRoute.text = "id_hd_route: " + listItem.id_hd_route.toString()
                } else {
                    textViewIdHdRoute.visibility = View.GONE
                }
                textViewNomRoute.text = "nom_route: " + listItem.nom_route
                textViewNomZak.text = "nom_zak: " + listItem.nom_zak
                if (listItem.nom_nakl != null) {
                    textViewNomNakl.visibility = View.VISIBLE
                    textViewNomNakl.text = "nom_nakl: " + listItem.nom_nakl
                } else {
                    textViewNomNakl.visibility = View.GONE
                }
                if (isSelected) {
                    val typedValue = TypedValue()
                    val theme = root.context.theme
                    theme.resolveAttribute(
                        com.google.android.material.R.attr.colorSecondary,
                        typedValue,
                        true
                    )
                    root.setBackgroundColor(
                        typedValue.data
                    )
                } else {
                    root.setBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.white
                        )
                    )
                }
            }
            binding.root.setOnClickListener { onClick() }
        }

        private fun onClick() {
            notifyItemChanged(selectedItemPos)
            selectedItemPos = layoutPosition
            notifyItemChanged(selectedItemPos)
        }
    }
}