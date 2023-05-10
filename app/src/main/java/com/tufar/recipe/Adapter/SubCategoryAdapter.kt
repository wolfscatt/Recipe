package com.tufar.recipe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tufar.recipe.Model.MealsItems
import com.tufar.recipe.Model.Recipes
import com.tufar.recipe.databinding.RecyclerViewSubCategoryBinding

class SubCategoryAdapter() : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>(){

    var listener : OnItemClickListener? = null
    var arrSubCategory = ArrayList<MealsItems>()
    var ctx : Context? = null

    fun setData(arrData : List<MealsItems>){
        arrSubCategory = arrData as ArrayList<MealsItems>
    }
    fun setClickListener(listener1 : OnItemClickListener){
        listener = listener1
    }
    class RecipeViewHolder(val binding : RecyclerViewSubCategoryBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var binding = RecyclerViewSubCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        ctx = parent.context
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.textViewDishName.text = arrSubCategory[position].strMeal
        Glide.with(ctx!!).load(arrSubCategory[position].strMealThumb).into(holder.binding.imgDish)
        holder.binding.root.setOnClickListener {
            listener!!.onClicked(arrSubCategory[position].idMeal)
        }

    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }
    interface OnItemClickListener{
        fun onClicked(id : String)
    }
}