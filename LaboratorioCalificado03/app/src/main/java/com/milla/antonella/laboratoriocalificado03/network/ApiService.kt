package com.milla.antonella.laboratoriocalificado03.network

import com.milla.antonella.laboratoriocalificado03.model.TeacherResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("list/teacher-b")
    fun getTeachers(): Call<TeacherResponse>
}
