package com.emirk.emirkarabeyodev6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.emirk.emirkarabeyodev6.databinding.ListItemProductBinding
import com.emirk.emirkarabeyodev6.model.Products

class ProductAdapter(private val products: Products) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ListItemProductBinding = convertView?.let {
            ListItemProductBinding.bind(it)
        } ?: ListItemProductBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        val product = products.products[position]
        Glide.with(binding.imageView)
            .load(product.image)
            .into(binding.imageView)
        binding.titleTextView.text = product.title
        binding.descriptionTextView.text = product.price.toString()

        // Burada öğeye resim ekleyebilirsiniz, örneğin:
        // binding.imageView.setImageResource(product.imageResId)

        return binding.root
    }

    override fun getItem(position: Int): Any {
        return products
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return 10
    }
}