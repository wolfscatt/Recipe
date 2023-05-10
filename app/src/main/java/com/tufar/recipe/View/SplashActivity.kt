package com.tufar.recipe.View

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tufar.recipe.Model.Category
import com.tufar.recipe.Model.Meal
import com.tufar.recipe.Model.MealsItems
import com.tufar.recipe.Service.Database.RecipeDatabase
import com.tufar.recipe.Service.RetrofitClient.Abstract.GetDataService
import com.tufar.recipe.Service.RetrofitClient.Concrete.RetrofitClientInstance
import com.tufar.recipe.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() , EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks{

    private lateinit var binding : ActivitySplashBinding
    private var READ_STORAGE_PERM = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        readExternalStorageTask()

        binding.loader.visibility = View.GONE
        binding.btnGetStarted.setOnClickListener{
            var intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }

    fun getCategories() {

        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category> {
            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                for (arr in response.body()!!.categoryItems!!){
                    getMeals(arr.strCategory)
                }
                insertDataIntoRoomDb(response.body())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(this@SplashActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getMeals(categoryName : String) {

        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal> {
            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                insertMealDataIntoRoomDb(categoryName,response.body())
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                binding.loader.visibility = View.VISIBLE
                Toast.makeText(this@SplashActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun clearDatabase(){
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
            }
        }
    }
    private fun insertMealDataIntoRoomDb(categoryName : String , meal: Meal?) {
        launch {
            this.let {
                for (arr in meal!!.mealsItem!!){
                    var mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().insertMeal(mealItemModel)
                }
            }
        }
    }

    private fun insertDataIntoRoomDb(category: Category?) {
        launch {
            this.let {
                for (arr in category!!.categoryItems!!){
                    RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().insertCategory(arr)
                }

                binding.btnGetStarted.visibility = View.VISIBLE
            }
        }
    }

    private fun hasReadStoragePermission() : Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    private fun readExternalStorageTask(){
        try {
            if (hasReadStoragePermission()){
                clearDatabase()
                getCategories()

            }else{
                EasyPermissions.requestPermissions(this,
                    "Devam etmek için izin vermelisiniz",
                    READ_STORAGE_PERM,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }catch (e : Exception){
            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
    }


}