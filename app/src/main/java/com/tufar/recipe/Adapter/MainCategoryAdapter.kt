package com.tufar.recipe.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tufar.recipe.Model.CategoryItems
import com.tufar.recipe.Model.Recipes
import com.tufar.recipe.databinding.RecyclerViewMainCategoryBinding

class MainCategoryAdapter() : RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>(){

    var listener : OnItemClickListener? = null
    var arrMainCategory = ArrayList<CategoryItems>()
    var ctx : Context? = null

    fun setData(arrData : List<CategoryItems>){
        arrMainCategory = arrData as ArrayList<CategoryItems>
    }

    fun setClickListener(listener1 : OnItemClickListener){
        listener = listener1
    }

    class RecipeViewHolder(val binding : RecyclerViewMainCategoryBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var binding = RecyclerViewMainCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        ctx = parent.context
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.textViewDishName.text = arrMainCategory[position].strCategory
        Glide.with(ctx!!).load(arrMainCategory[position].strCategoryThumb).into(holder.binding.imgDish)
        holder.binding.root.setOnClickListener {
            listener!!.onClicked(arrMainCategory[position].strCategory)
        }
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    interface OnItemClickListener{
        fun onClicked(categoryName: String)
    }
}