package com.exercice.technicaltest.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercice.technicaltest.R
import com.exercice.technicaltest.adapters.ProductAdapter
import com.exercice.technicaltest.constants.Constants
import com.exercice.technicaltest.databinding.FragmentMainBinding
import com.exercice.technicaltest.models.Product
import com.google.android.material.snackbar.Snackbar
import com.pixelcarrot.base64image.Base64Image
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Fragment
 * contain list of products
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    /**
     * variable
     */
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var startResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var recyclerViewAdapter: ProductAdapter
    private val mainViewModel: MainViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        startResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // result.data.data
                    val intent = result.data
                    intent?.let {
                        val bitmap: Bitmap? = intent.extras?.get("data") as Bitmap?
                        bitmap?.let {
                            photoTakenCallback(bitmap)
                        }
                    }
                }
            }
    }

    /**
     * called when click
     * on item of recyclview
     */
    private fun onProductClicked(product: Product) {
        navigateToProductDetails(productId = product.id)
    }

    private fun navigateToProductDetails(productId: Int) {
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_PRODUCT_ID, productId)

        findNavController()
            .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    /**
     * retriver bitmap image
     */
    private fun photoTakenCallback(bitmap: Bitmap?) {
        // convert bitmap to base64
        Base64Image.encode(bitmap) { base64 ->
            base64?.let { image ->
                // success
                mainViewModel.addProduct(image)
            }
        }
    }

    /**
     * called when on Create view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()

    }


    /**
     * called when
     * the view created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        handleObserve()
        mainViewModel.getProducts()
        handleClick()
    }

    private fun handleClick() {
        binding.fab.setOnClickListener { view ->
            handleTakeProductPhoto()
        }
    }

    private fun handleObserve() {
        mainViewModel.products.observe(viewLifecycleOwner) { products ->
            recyclerViewAdapter.submitList(products)
        }

        mainViewModel.productdetails.observe(viewLifecycleOwner) { product ->
            navigateToProductDetails(product.id)
        }

        mainViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.progress.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        mainViewModel.anError.observe(viewLifecycleOwner) { anError ->
            Snackbar.make(
                binding.root,
                if (anError == Constants.EMPTY_PRODUCTS_ERROR_MSG) R.string.no_product_message else R.string.error_message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * init product adapter
     */
    private fun initAdapter() {
        recyclerViewAdapter = ProductAdapter { product ->
            onProductClicked(product)
        }
        binding.productRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * handle take a picture
     */
    private fun handleTakeProductPhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startResultLauncher.launch(takePictureIntent)
    }

    /**
     * called when the app destroyed
     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}