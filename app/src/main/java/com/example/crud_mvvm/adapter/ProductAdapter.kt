package com.example.crud_mvvm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.crud_mvvm.R
import com.example.crud_mvvm.ui.activity.UpdateProductActivity
import com.example.crud_mvvm.model.ProductModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProductAdapter (var context: Context,var data :
                      ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var productName : TextView = view.findViewById(R.id.lblName)
        var productPrice : TextView = view.findViewById(R.id.lblPrice)
        var productDesc : TextView = view.findViewById(R.id.lblDescription)
        var btnEdit : LottieAnimationView = view.findViewById(R.id.btnEdit)
        var imageView : ImageView = view.findViewById(R.id.imageView45)
        var progressBar : ProgressBar = view.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view = LayoutInflater.from(parent.context).
                        inflate(R.layout.sample_product,
                         parent,false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = data[position].productName
        holder.productPrice.text = data[position].productPrice.toString()
        holder.productDesc.text = data[position].productDesc

        var imageUrl = data[position].url
        Picasso.get().load(imageUrl).into(holder.imageView,object: Callback{
            override fun onSuccess() {
                holder.progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
            }

        })

        holder.btnEdit.setOnClickListener {
            var intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("product",data[position])
            context.startActivity(intent)
        }
    }

    fun getProductID(position: Int) : String {
        return data[position].id
    }

    fun getImageName(position: Int): String{
        return data[position].imageName
    }

    fun updateData(products: List<ProductModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }
}