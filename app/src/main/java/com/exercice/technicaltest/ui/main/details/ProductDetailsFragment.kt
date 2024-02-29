package com.exercice.technicaltest.ui.main.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.exercice.technicaltest.R
import com.exercice.technicaltest.constants.Constants
import com.exercice.technicaltest.databinding.FragmentProductDetailsBinding
import com.exercice.technicaltest.models.Product
import com.google.android.material.snackbar.Snackbar
import com.pixelcarrot.base64image.Base64Image
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getInt(Constants.KEY_PRODUCT_ID) ?: 0
        handleObserve()
        productDetailsViewModel.getProductDetails(productId)
    }

    private fun handleObserve() {

        productDetailsViewModel.productdetails.observe(viewLifecycleOwner) { product ->
            displayProductDetails(product)
        }

        productDetailsViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.progress.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        productDetailsViewModel.anError.observe(viewLifecycleOwner) { anError ->
            Snackbar.make(
                binding.root,
                if (anError == Constants.EMPTY_PRODUCTS_ERROR_MSG) R.string.no_product_message else R.string.error_message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun displayProductDetails(product: Product) {
        Base64Image.decode(product.thumbnail) { bitmap ->
            bitmap?.let {
                binding.photoImg.setImageBitmap(it)
            }
        }
        binding.titleTv.text = product.title
        binding.descriptionTvValue.text = product.description
        binding.priceValue.text = requireContext().getString(R.string.price_label,product.price.toString())
        binding.discountPercentageValue.text = requireContext().getString(R.string.discount_label,product.discountPercentage)
        binding.ratingValue.rating = product.rating
        binding.stockValue.text = product.stock.toString()
        binding.brandValue.text = product.brand
        binding.categoryValue.text = product.category
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}