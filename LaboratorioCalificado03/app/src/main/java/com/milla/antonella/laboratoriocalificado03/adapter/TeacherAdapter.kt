package com.milla.antonella.laboratoriocalificado03.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.milla.antonella.laboratoriocalificado03.databinding.ItemTeacherBinding
import com.milla.antonella.laboratoriocalificado03.model.Teacher
import com.bumptech.glide.Glide

class TeacherAdapter(
    private val context: Context,
    private val teachers: List<Teacher>
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(val binding: ItemTeacherBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teachers[position]

        // Mostrar nombre completo
        holder.binding.tvName.text = "${teacher.name} ${teacher.last_name}"

        // Mostrar número de teléfono
        holder.binding.tvPhone.text = "📞 ${teacher.phone_number}"

        // Mostrar correo electrónico
        holder.binding.tvEmail.text = "✉️ ${teacher.email}"

        // Cargar imagen del profesor
        Glide.with(context)
            .load(teacher.image_url)
            .placeholder(android.R.drawable.sym_def_app_icon)
            .into(holder.binding.ivPhoto)

        // Click simple: llamada
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${teacher.phone_number}")
            }
            context.startActivity(intent)
        }

        // Click largo: enviar email
        holder.itemView.setOnLongClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${teacher.email}")
            }
            context.startActivity(emailIntent)
            true
        }
    }

    override fun getItemCount(): Int = teachers.size
}
