package com.tufar.recipe.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tufar.recipe.Model.Converter.CategoryListConverter

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "categoryItems")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter::class)
    var categoryItems: List<CategoryItems>? = null
)