package com.example.fitflex

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitflex.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items : ArrayList<ExerciseModel>) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    inner class ViewHolder(binding : ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected() ->{
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context , R.drawable.item_circular_bg_selected)
            }
            model.getIsCompleted() ->{
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context , R.drawable.item_circular_bg_completed)
                holder.tvItem.setTextColor(Color.parseColor("#C5CAE9"))
            }
            else ->{
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context , R.drawable.item_circular_background)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}