package com.exercice.technicaltest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exercice.technicaltest.R
import com.exercice.technicaltest.databinding.ProductItemBinding
import com.exercice.technicaltest.models.Product
import com.pixelcarrot.base64image.Base64Image

private typealias ProductClickListener = (Product) -> Unit
class ProductAdapter(
    private val clickListener: ProductClickListener
) : ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductViewHolder(
    binding: ProductItemBinding,
    private val clickListener: ProductClickListener
) : RecyclerView.ViewHolder(binding.root) {


    /**
     * variables
     */
    private lateinit var item: Product
    private val imageView = binding.imageView
    private val titleView = binding.title
    private val starsView = binding.rating
    private val descView = binding.description
    private val priceView = binding.price
    private val view = binding.root

    /**
     * bind view
     */
    fun bind(item: Product) {
        this.item = item
        Base64Image.decode(item.thumbnail) { bitmap ->
            bitmap?.let {
                imageView.setImageBitmap(it)
            }
        }
        titleView.text = item.title
        starsView.rating = item.rating
        descView.text = item.description
        priceView.text = view.context.getString(R.string.price_label, item.price.toString())
        view.setOnClickListener {
            // click on item
            clickListener.invoke(item)
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }
}