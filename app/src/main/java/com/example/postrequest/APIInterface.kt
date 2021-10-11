package com.example.postrequest

import retrofit2.Call
import retrofit2.http.*

//https://dojo-recipes.herokuapp.com/recipes/

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUser(): Call<List<Users.UserDetails>>


    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: Users.UserDetails): Call<Users.UserDetails>

    @Headers("Content-Type: application/json")
    @PUT("/test/{id}")
    fun updateUser(@Path("id") id : Int, @Body userData: Users.UserDetails) : Call<Users.UserDetails>

    @Headers("Content-Type: application/json")
    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id : Int) : Call<Void>
}