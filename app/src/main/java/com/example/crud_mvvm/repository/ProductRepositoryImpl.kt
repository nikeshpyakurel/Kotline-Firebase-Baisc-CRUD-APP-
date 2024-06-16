package com.example.crud_mvvm.repository

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud_mvvm.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class ProductRepositoryImpl : ProductRepository {
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref: DatabaseReference = firebaseDatabase.reference.child("products")

    var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageReference: StorageReference = firebaseStorage.reference.child("products")
    override fun addProducts(productModel: ProductModel, callback: (Boolean, String?) -> Unit) {
        var id = ref.push().key.toString()
       productModel.id = id

        ref.child(id).setValue(productModel).addOnCompleteListener {
            if (it.isSuccessful) {
               callback(true,"Product added successfully")
            } else {
                callback(false,"Unable to add Products")
            }
        }
    }

    override fun uploadImages(imageName:String,imageUri: Uri, callback: (Boolean, String?,String?) -> Unit) {
        var imageReference = storageReference.child("products").
        child(imageName)
        imageUri.let {url->
            imageReference.putFile(url).addOnSuccessListener {
                imageReference.downloadUrl.addOnSuccessListener {url->
                    var imageUrl =  url.toString()
                   callback(true,imageUrl,"Upload success")
                }
            }.addOnFailureListener {
               callback(false,"","Failed to load image")
            }
        }

    }

    override fun getAllProducts(callback: (List<ProductModel>?, Boolean, String?) -> Unit) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               var productList = mutableListOf<ProductModel>()
                for (eachData in snapshot.children){
                    var product = eachData.getValue(ProductModel::class.java)
                    if(product !=null){
                        productList.add(product)
                    }
                }
                callback(productList,true,"Product fetched successfully")
            }

            override fun onCancelled(error: DatabaseError) {
               callback(null,false,"Unable to fetch ${error.message}")
            }
        })
    }

    override fun updateProducts(
        id: String,
        data: MutableMap<String, Any>?,
        callback: (Boolean, String?) -> Unit
    ) {
        data?.let {
            ref.child(id).updateChildren(it).addOnCompleteListener {
                if(it.isSuccessful){
                   callback(true,"Data has been updated")
                }else{
                   callback(false,"Unable to upload data")
                }
            }
        }
    }

    override fun deleteProducts(id: String, callback: (Boolean, String?) -> Unit) {
        ref.child(id).removeValue().addOnCompleteListener {
            if(it.isSuccessful){

                callback(true,"Product Deleted")
            }
            else{
                callback(false,"Unable to delte product")
            }
        }
    }

    override fun deleteImage(imageName: String, callback: (Boolean, String?) -> Unit) {
        storageReference.child("products").
        child(imageName).delete().addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Image Deleted")
            }
            else{
                callback(false,"Unable to delete image")
            }
        }
    }

}