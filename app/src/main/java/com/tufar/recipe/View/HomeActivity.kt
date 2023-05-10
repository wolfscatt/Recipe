package com.tufar.recipe.View

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tufar.recipe.Adapter.MainCategoryAdapter
import com.tufar.recipe.Adapter.SubCategoryAdapter
import com.tufar.recipe.Model.CategoryItems
import com.tufar.recipe.Model.MealsItems
import com.tufar.recipe.Service.Database.RecipeDatabase
import com.tufar.recipe.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch
import java.net.IDN

class HomeActivity : BaseActivity() {
    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    private val mainCategoryAdapter = MainCategoryAdapter()
    private val subCategoryAdapter = SubCategoryAdapter()

    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)


    }

    private val onClicked = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }

    }
    private val onClickedSubItem = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id : String) {
            var intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

    }

    private fun getDataFromDb(){
       launch {
           this.let {
               var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
               arrMainCategory = cat as ArrayList<CategoryItems>
               arrMainCategory.reverse()
               getMealDataFromDb(arrMainCategory[0].strCategory)
               mainCategoryAdapter.setData(arrMainCategory)
               binding.rvMainCategory.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
               binding.rvMainCategory.adapter = mainCategoryAdapter
           }
       }
    }
    private fun getMealDataFromDb(categoryName : String){
        binding.tvCategory.text = "$categoryName Category"
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                binding.rvSubCategory.adapter = subCategoryAdapter
            }
        }
    }
}