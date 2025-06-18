package com.milla.antonella.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.milla.antonella.laboratoriocalificado03.adapter.TeacherAdapter
import com.milla.antonella.laboratoriocalificado03.databinding.ActivityEjercicio01Binding
import com.milla.antonella.laboratoriocalificado03.model.TeacherResponse
import com.milla.antonella.laboratoriocalificado03.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ejercicio01Activity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicio01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTeachers.layoutManager = LinearLayoutManager(this)

        // Mostrar "Cargando profesores"
        binding.textViewLoading.visibility = View.VISIBLE
        binding.rvTeachers.visibility = View.GONE

        RetrofitClient.instance.getTeachers().enqueue(object : Callback<TeacherResponse> {
            override fun onResponse(call: Call<TeacherResponse>, response: Response<TeacherResponse>) {
                if (response.isSuccessful) {
                    val teacherList = response.body()?.teachers
                    Log.d("API_SUCCESS", "Datos recibidos: $teacherList")

                    if (!teacherList.isNullOrEmpty()) {
                        val adapter = TeacherAdapter(this@Ejercicio01Activity, teacherList)
                        binding.rvTeachers.adapter = adapter
                        binding.textViewLoading.visibility = View.GONE
                        binding.rvTeachers.visibility = View.VISIBLE
                    } else {
                        binding.textViewLoading.text = "No se encontraron profesores"
                    }
                } else {
                    Log.e("API_ERROR", "Error en respuesta: ${response.errorBody()?.string()}")
                    binding.textViewLoading.text = "Error al cargar profesores"
                }
            }

            override fun onFailure(call: Call<TeacherResponse>, t: Throwable) {
                Log.e("API_ERROR", "Fallo de conexión: ${t.message}", t)
                binding.textViewLoading.text = "Fallo: ${t.message}"
            }
        })
    }
}
