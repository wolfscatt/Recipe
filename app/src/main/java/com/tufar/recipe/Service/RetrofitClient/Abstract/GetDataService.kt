package com.tufar.recipe.Service.RetrofitClient.Abstract

import retrofit2.http.Query
import com.tufar.recipe.Model.Category
import com.tufar.recipe.Model.Meal
import com.tufar.recipe.Model.MealResponse
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String): Call<MealResponse>


}