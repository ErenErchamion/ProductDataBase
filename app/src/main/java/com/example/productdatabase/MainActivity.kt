package com.example.productdatabase

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun saveCat(view: View){
        val db = FirebaseFirestore.getInstance()

        val CatNameEditText = findViewById(R.id.editTextCatName) as EditText
        val CatImageEditText = findViewById(R.id.editTextCatImage) as EditText

        var catName=CatNameEditText.text.toString()
        var catImage=CatImageEditText.text.toString()

        var Category=CategoryDTO()

        with(Category){
            categoryName=catName
            categoryImagePath=catImage
        }

        db.collection("Categorys").document()
            .set(Category)

    }





    fun saveProduct(view: View){
        val db = FirebaseFirestore.getInstance()

        val ProductNameEditText = findViewById(R.id.editTextProductName) as EditText
        val ProductCatEditText = findViewById(R.id.editTextProductCat) as EditText
        val ProductImageEditText = findViewById(R.id.editTextProductImage) as EditText
        val ProductDescriptionEditText = findViewById(R.id.editTextProductDescription) as EditText

        var productNametext=ProductNameEditText.text.toString()
        var productCatText=ProductCatEditText.text.toString()
        var productImageText=ProductImageEditText.text.toString()
        var productDescriptionText=ProductDescriptionEditText.text.toString()

        var Product=ProductDTO()
        var categoryid=""


        db.collection("Categorys")
            .whereEqualTo("categoryName","Adidas" )
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    categoryid=document.id
                    Product.catID=document.id
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        with(Product){
            productName=productNametext
            productImagePath=productImageText
            productDescription=productDescriptionText


        }
        db.collection("Products").document()
            .set(Product)







    }





















}







data class CategoryDTO(var categoryName:String,var categoryImagePath:String){
    constructor():this("", "")
}
data class ProductDTO(var productName:String,var catID:String,var productImagePath:String,var productDescription:String){
    constructor():this("","","","")
}