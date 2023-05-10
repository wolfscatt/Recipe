package com.tufar.recipe.Model.Converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tufar.recipe.Model.MealsItems

class MealListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<MealsItems>):String?{
        if (category == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<MealsItems>(){

            }.type
            return gson.toJson(category,type)
        }
    }

    @TypeConverter
    fun toCategoryList ( categoryString: String):List<MealsItems>?{
        if (categoryString == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object :TypeToken<MealsItems>(){

            }.type
            return  gson.fromJson(categoryString,type)
        }
    }
}