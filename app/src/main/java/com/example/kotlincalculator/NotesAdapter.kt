package com.example.kotlincalculator

import DataClasses.Notes
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincalculator.databinding.NoteItemBinding

class NotesAdapter(private var notes : List<Notes>, context : Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var onItemLongClicked: ((Int) -> Unit)? = null

    inner class NotesViewHolder(private val binding : NoteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(note: Notes) {
            binding.noteTitle.text = note.noteName
            binding.noteDescription.text = note.noteDescription
        }

        init {
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemLongClicked?.invoke(position)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : NotesViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount() : Int = notes.size

    override fun onBindViewHolder(holder : NotesViewHolder, position : Int) {
        val note = notes[position]
        holder.bind(note)
    }

    fun refreshData(newNotes : List<Notes>){
        notes = newNotes
        notifyDataSetChanged()
    }
}