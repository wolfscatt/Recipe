package com.tufar.recipe.Service.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tufar.recipe.Model.Category
import com.tufar.recipe.Model.CategoryItems
import com.tufar.recipe.Model.MealsItems
import com.tufar.recipe.Model.Recipes

@Dao
interface RecipeDao {

    @Query("Select * from categoryitems Order by id Desc")
    suspend fun getAllCategory() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: MealsItems?)

    @Query("Delete from categoryitems")
    suspend fun clearDb()

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String) : List<MealsItems>
}