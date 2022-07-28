package com.garibyan.armen.tictactoe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.garibyan.armen.tictactoe.databinding.RvCrossItemBinding
import com.garibyan.armen.tictactoe.databinding.RvEmptyItemBinding
import com.garibyan.armen.tictactoe.databinding.RvZeroItemBinding


class FieldAdapter : ListAdapter<Field, RecyclerView.ViewHolder>(FieldCallBack()) {
    var onItemClickListener: ((Field) -> Unit)? = null


    inner class EmptyFieldViewHolder(private val binding: RvEmptyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(field: Field) {
            binding.btn.setOnClickListener {
                onItemClickListener?.invoke(field)
            }
        }
    }

    inner class CrossFieldViewHolder(private val binding: RvCrossItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(field: Field) {
            binding.btn.setOnClickListener {
                onItemClickListener?.invoke(field)
            }
        }
    }

    inner class ZeroFieldViewHolder(private val binding: RvZeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(field: Field) {
            binding.btn.setOnClickListener {
                onItemClickListener?.invoke(field)
            }
        }
    }


    class FieldCallBack : DiffUtil.ItemCallback<Field>() {
        override fun areItemsTheSame(oldItem: Field, newItem: Field): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Field, newItem: Field): Boolean {
            return oldItem.viewType == newItem.viewType
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ViewType.CROSS -> CrossFieldViewHolder(
                RvCrossItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ViewType.ZERO -> ZeroFieldViewHolder(
                RvZeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> EmptyFieldViewHolder(
                RvEmptyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is CrossFieldViewHolder -> holder.bind(getItem(position))
            is ZeroFieldViewHolder -> holder.bind(getItem(position))
            is EmptyFieldViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
}